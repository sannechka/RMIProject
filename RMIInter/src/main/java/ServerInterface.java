
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    void getUsers() throws RemoteException;

    void sendMessage(String userName, String message) throws RemoteException, InterruptedException;

    void disconnect(String userName) throws RemoteException;

    boolean connect(String userName) throws IOException, NotBoundException;

    void sendPM(String user1, String user2, String privateMessage) throws RemoteException;
}


