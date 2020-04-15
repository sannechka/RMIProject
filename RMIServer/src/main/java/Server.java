

import java.io.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends UnicastRemoteObject implements ServerInterface {
    private Sendler sendler;

    public Server() throws RemoteException {
        sendler = new Sendler();
        ExecutorService threds = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        threds.submit(sendler);
    }

    public static void main(String[] args) throws RemoteException {
        LocateRegistry.createRegistry(1098);
        ServerInterface start = new Server();
        try {
            Naming.rebind("chat", start);
            System.out.println("Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getUsers() throws RemoteException {
        String[] users = sendler.usersOnline.keySet().
                stream().
                toArray(String[]::new);
        for (ClientInterface a : sendler.usersOnline.values()) {
            a.updateUserList(users);

        }
    }

    @Override
    public void sendMessage(String userName, String message) throws InterruptedException {
        Message newMessage = new Message(message, userName);
        sendler.allMessages.put(newMessage);
    }

    @Override
    public void sendPM(String sender, String recipient, String privateMessage) {
        PMessage newMessage = new PMessage(privateMessage, sender, recipient);
        sendler.allMessages.offer(newMessage);
    }

    @Override
    public void disconnect(String userName) throws RemoteException {
        sendler.usersOnline.remove(userName);
        sendAlert("[" + userName + "] left the chat ");
    }

    public synchronized void registration(String userName) throws IOException {
        boolean reg = false;
        File usersLogins = new File("C:\\Users\\User\\IdeaProjects\\RMIProject", "Login.txt");
        if (!usersLogins.exists()) {
           if(usersLogins.createNewFile()) {
               PrintWriter log = new PrintWriter(usersLogins.getAbsoluteFile());
               log.println(userName);
               log.close();
           }
        } else {
            Scanner scan = new Scanner(usersLogins.getAbsoluteFile());
            while (scan.hasNextLine()) {
                String l = scan.nextLine();
                if (l.equals(userName)) {
                    reg = true;
                }
            }
            if (!reg) {
                PrintStream printStream = new PrintStream(new FileOutputStream(usersLogins, true), true);
                printStream.println(userName);
                printStream.close();
                System.out.println(userName + "- was registered");
            }
        }
    }

    @Override
    public boolean connect(String userName) throws IOException, NotBoundException {
        if (sendler.usersOnline.containsKey(userName)) {
            return false;
        }
        ClientInterface newClient = (ClientInterface) Naming.lookup(userName);
        registration(userName);
        sendAlert("[" + userName + "] connecting to chat ");
        sendler.usersOnline.put(userName, newClient);
        getUsers();
        return true;
    }

    public void sendAlert(String text) throws RemoteException {
        System.out.println(text);
        for (ClientInterface a : sendler.usersOnline.values()) {
            a.messageFromChat(text);
        }
    }
}







