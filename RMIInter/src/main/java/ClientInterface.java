import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void messageFromChat(String message) throws RemoteException;

    void updateUserList(String[] usersOnline) throws RemoteException;


}
