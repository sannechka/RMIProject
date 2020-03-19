import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMath  extends Remote {
        int add(int a, int b) throws RemoteException;
        String  sendMessege (String username, String  message )  throws RemoteException;
}


