import javax.naming.NamingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static  void main(String[] args) throws NamingException, RemoteException {

        Registry registry = LocateRegistry.getRegistry(1099);
        try {
            IMath remote = (IMath) registry.lookup("chat");
            int result = remote.add(1, 2);
            System.out.println(result);

        } catch (NotBoundException e) {
            e.printStackTrace();

        }
    }
}

