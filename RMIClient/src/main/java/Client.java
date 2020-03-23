import javax.management.remote.rmi.RMIServer;
import javax.naming.NamingException;
import java.awt.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Client {


    public IMath remote;
    ClientGUI gui;

    public Client(ClientGUI aChatGUI)  throws RemoteException {
        this.gui = aChatGUI;
    }

    public void startClient(String name) throws RemoteException
    {
        Registry registry = LocateRegistry.getRegistry(1099);

        try {
            remote = (IMath) registry.lookup("chat");
             boolean log = remote.connect(name);

        } catch (NotBoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public  static void Message(ClientGUI gui) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        IMath remote = (IMath) registry.lookup("chat");

    }


}

