
import javax.naming.NamingException;
import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server extends UnicastRemoteObject implements ServerInterface {
    private Registry registry;
    List<User> users = new ArrayList<>();
    List<Message> messages = new ArrayList<>();
    List<PMessage> pMessages = new ArrayList<>();

    protected Server() throws RemoteException {
    }

    public static void main(String[] args) throws NamingException, RemoteException {
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

    public void sendMessege(String username, String message) {
        String newMessage = username + ": " + message;
        for (int i = 0; i < users.size(); i++) {
            try {
                users.get(i).getClient().messageFromChat(newMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (users.get(i).getName().equals(username)) {
                User a = users.get(i);
                Message newMessage2 = new Message(message, a);
                messages.add(newMessage2);
            }
            System.out.println("Пользователь: " + username + " добавил сообщение: " + message + " в общий чат");
        }
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
    public boolean connect(String username, ClientInterface client) throws IOException {
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
        String newMessage = user1 + " FROM " + user2 + ": " + privateMessage;
        int a = 0;
        int b = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).name.equals(user1) || users.get(i).name.equals(user2)) {
                try {
                    users.get(i).getClient().messageFromChat(newMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            if (users.get(i).getName().equals(user1)) {
                a = i;
            }
            if (users.get(i).getName().equals(user2)) {
                b = i;
            }

        }
        Message newMessage2 = new PMessage(privateMessage, users.get(a), users.get(b));
        messages.add(newMessage2);
        System.out.println("Пользователь: " + user1 + " отправил личное сообщение для: " + user2 + ": " + privateMessage);
    }

}







