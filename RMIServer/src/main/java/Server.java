

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


public class Server extends UnicastRemoteObject implements ServerInterface {
    private Sendler sendler;

    public Server() throws RemoteException {
        sendler = new Sendler();
        sendler.start();
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

    @Override
    public boolean connect(String userName) throws IOException, NotBoundException {
        if (sendler.usersOnline.containsKey(userName)) {
            return false;
        }
        ClientInterface newClient = (ClientInterface) Naming.lookup(userName);
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







