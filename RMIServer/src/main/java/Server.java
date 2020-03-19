import javax.naming.NamingException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    private Registry registry;

    public static void main(String[] args) throws NamingException, RemoteException {
        Registry registry = LocateRegistry.createRegistry(1099);
        IMath start = new IMathImpl();

        try {
            registry.bind("chat", start);
            System.out.println("Server is running...");
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}





