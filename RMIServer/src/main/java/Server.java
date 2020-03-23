import com.sun.javaws.IconUtil;
import com.sun.security.ntlm.Client;

import javax.naming.NamingException;
import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Server extends UnicastRemoteObject implements IMath {
    private Registry registry;
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Message> messages = new ArrayList<>();
    ArrayList<PMessage> pMessages= new ArrayList<>();

    protected Server() throws RemoteException {
    }

    public static void main(String[] args) throws NamingException, RemoteException {
        Registry registry = LocateRegistry.createRegistry(1099);
        IMath start = new Server();

        try {
            registry.bind("chat", start);
            System.out.println("Server is running...");
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
    public  String[] getusers(){
        String[] onlineusers = new String[users.size()];
        for (int i = 0; i <users.size();i++)
        {onlineusers[i] = users.get(i).getName();}
        return onlineusers;
    }

    public String sendMessege(String username, String message){
        for (int i = 0; i <users.size();i++)
        {
            if(users.get(i).getName().equals(username)){
               User a =users.get(i);
                Message newMessage = new Message(message,a);
                messages.add(newMessage);
            }
        }
        return username + message;
    }
    @Override
    public void disconnect(String userName) throws RemoteException {

    }

    @Override
    public boolean connect(String username) throws IOException {
        boolean reg = false;
        File login = new File("C:\\Users\\User\\IdeaProjects\\RMIProject", "Login.txt");
        if (!login.exists()) {
            login.createNewFile();
            PrintWriter log = new PrintWriter(login.getAbsoluteFile());
            log.println(username);
            log.close();
            return reg;
        } else {
            Scanner scan = new Scanner(login.getAbsoluteFile());
            while (scan.hasNextLine()) {
                String l = scan.nextLine();
                if (l.equals(username)) {
                    reg = true;
                }
            }
            if (reg == false) {
                PrintStream printStream = new PrintStream(new FileOutputStream(login, true), true);
                printStream.println(username);
                printStream.close();
            }
            users.add( new User(username));

            return reg;
        }
    }
    @Override
    public void sendPM(User user1,  User user2, String privateMessage) throws RemoteException {

    }
}






