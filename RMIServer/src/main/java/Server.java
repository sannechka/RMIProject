
import javax.naming.NamingException;
import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server extends UnicastRemoteObject implements ServerInterface {
    List<User> users = new ArrayList<>();
    Queue<Imessage> allMessages=  new LinkedList<>();


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
        String[] onlineusers = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            onlineusers[i] = users.get(i).getName();
        }
        return onlineusers;
    }
    public void sendAllMassage(){

    }

    public void sendMessage(String username, String message) {
        User user = users.stream().filter(o -> o.getName().equals(username)).findFirst().get();
        Message newMessage2 = new Message(message, user);
        allMessages.add(newMessage2);
        }



    @Override
    public void disconnect(String userName) throws RemoteException {
        for (User a : users) {
            if (a.name.equals(userName)) {
                users.remove(a);
                System.out.println("Пользователь: " + userName + " покинул чат");
                break;
            }
        }
    }

    @Override
    public synchronized boolean connect(String username, ClientInterface client) throws IOException {
        for (User a : users) {
            if (a.name.equals(username)) {
                return false;
            }
        }
        boolean reg = false;
        File login = new File("C:\\Users\\User\\IdeaProjects\\RMIProject", "Login.txt");
        if (!login.exists()) {
            login.createNewFile();
            PrintWriter log = new PrintWriter(login.getAbsoluteFile());
            log.println(username);
            log.close();
        } else {
            Scanner scan = new Scanner(login.getAbsoluteFile());
            while (scan.hasNextLine()) {
                String l = scan.nextLine();
                if (l.equals(username)) {
                    System.out.println("Уже зарегестрированный пользователь : " + username + " вошел в чат");
                    reg = true;
                }
            }
            if (reg == false) {
                PrintStream printStream = new PrintStream(new FileOutputStream(login, true), true);
                printStream.println(username);
                printStream.close();
                System.out.println("Новый пользователь : " + username + " вошел в чат");
            }
            users.add(new User(username, client));
        }
        return true;
    }

    @Override
    public void sendPM(String user1, String user2, String privateMessage) throws RemoteException {
        User usera = users.stream().filter(o -> o.getName().equals(user1)).findFirst().get();
        User userb = users.stream().filter(o -> o.getName().equals(user2)).findFirst().get();
        PMessage newMessage2 = new PMessage(privateMessage, usera,userb);
        allMessages.add(newMessage2);
    }

}







