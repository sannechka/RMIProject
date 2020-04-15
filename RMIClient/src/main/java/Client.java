
import javax.swing.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


public class Client extends UnicastRemoteObject implements ClientInterface {

    protected ServerInterface remote;
    private ClientGUI gui;
    private String name;

    public Client(ClientGUI chatGUI, String name) throws RemoteException {
        super();
        this.gui = chatGUI;
        this.name = name;
    }

    public boolean startClient(String name) throws RemoteException {
        LocateRegistry.getRegistry(1098);
        try {
            Naming.rebind(name, this);
            remote = (ServerInterface) Naming.lookup("chat");
            return remote.connect(name);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gui.conectionPanel, "Connection proplem, try later");

        }
        return false;
    }

    @Override
    public void updateUserList(String[] onlineUsers) {
        gui.pMessageBut.setEnabled(true);
        if (onlineUsers.length < 2) {
            gui.pMessageBut.setEnabled(false);
        }
        if (onlineUsers.length != 0) {
            gui.users = new JList<>(onlineUsers);
        }
    }

    @Override
    public void messageFromChat(String message) {
        gui.chat.append(message);
        gui.chat.setText(gui.chat.getText() + "\n");


    }
}

