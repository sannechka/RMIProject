import javax.management.remote.rmi.RMIServer;
import javax.naming.NamingException;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {


    public IMath remote;
    ClientGUI gui;

    public Client(ClientGUI aChatGUI)  throws RemoteException {
        this.gui = aChatGUI;
    }

    public void startClient() throws RemoteException
    {
        Registry registry = LocateRegistry.getRegistry(1099);
        gui.setVisible(true);
        try {
            remote = (IMath) registry.lookup("chat");
            int result = remote.add(4, 5);
            String d = "dfear"+"rftr";
            System.out.println("nj hwfberlhgretgrgrw");
            System.out.println(result);
            String fff= remote.sendMessege("efr","fref");
            System.out.println(fff);

        } catch (NotBoundException e) {
            e.printStackTrace();

        }
    }



    public  static void Message(ClientGUI gui) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        IMath remote = (IMath) registry.lookup("chat");
        remote.sendMessege(gui.name.getText(), "рботает");

    }


}

