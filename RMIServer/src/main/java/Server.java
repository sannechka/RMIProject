
import javafx.print.Collation;

import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Server extends UnicastRemoteObject implements ServerInterface {
    Sendler sendler = new Sendler();

    protected Server() throws RemoteException {

    }

    public static void main(String[] args) throws  RemoteException {
        Registry registry = LocateRegistry.createRegistry(1099);
        ServerInterface start = new Server();
        try {
            registry.bind("chat", start);
            System.out.println("Server is running...");
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public String[] getusers() {
        String[] users = sendler.usersOnline.keySet()
                .stream()
                .toArray(String[]::new);
        return users;
    }

    @Override
    public void sendMessage(String userName, String message) {
        Message newMessage = new Message(message, userName);
        sendler.allMessages.offer(newMessage);
        }

    @Override
    public void sendPM(String sender, String recipient, String privateMessage)  {
        PMessage newMessage = new PMessage(privateMessage, sender,recipient);
        sendler.allMessages.offer(newMessage);
    }

    @Override
    public void disconnect(String userName) throws RemoteException {
       sendler.usersOnline.remove(userName);
       System.out.println("Пользователь: " + userName + " покинул чат");
        }

    public synchronized void registration(String userName) throws IOException {
        boolean reg = false;
        File usersLogins = new File("C:\\Users\\User\\IdeaProjects\\RMIProject", "Login.txt");
        if (!usersLogins.exists()) {
            usersLogins.createNewFile();
            PrintWriter log = new PrintWriter(usersLogins.getAbsoluteFile());
            log.println(userName);
            log.close();
        } else {
            Scanner scan = new Scanner(usersLogins.getAbsoluteFile());
            while (scan.hasNextLine()) {
                String l = scan.nextLine();
                if (l.equals(userName)) {
                    System.out.println("Уже зарегестрированный пользователь : " + userName + " вошел в чат");
                    reg = true;
                }
            }
            if (reg == false) {
                PrintStream printStream = new PrintStream(new FileOutputStream(usersLogins, true), true);
                printStream.println(userName);
                printStream.close();
                System.out.println("Новый пользователь : " + userName + " вошел в чат");
            }
        }
    }

    @Override
    public synchronized boolean connect(String username, ClientInterface client) throws IOException {
        if (sendler.usersOnline.containsKey(username)) {
                return false;
            }
        registration(username);
        sendler.usersOnline.put(username,client);
        return true;
    }
}







