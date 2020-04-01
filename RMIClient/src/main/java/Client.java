import javax.management.remote.rmi.RMIServer;
import javax.naming.NamingException;
import java.awt.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Client extends UnicastRemoteObject implements ClientInterface {

    public ServerInterface remote;
    ClientGUI gui;

    public Client(ClientGUI aChatGUI)  throws RemoteException {
        this.gui = aChatGUI;
    }

    public boolean startClient(String name) throws RemoteException
    {
        Registry registry = LocateRegistry.getRegistry(1099);
        try {
            remote = (ServerInterface) registry.lookup("chat");
             boolean log = remote.connect(name, this);
             return log;



        } catch (NotBoundException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void messageFromChat(String message) throws RemoteException {
        gui.chat.append( message );
        gui.chat.setText(gui.chat.getText() + "\n");


    }
}

