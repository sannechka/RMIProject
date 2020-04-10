
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface  extends Remote {
        String[] getusers() throws RemoteException;
         void   sendMessage (String username, String  message )  throws RemoteException;
         void  disconnect (String  username ) throws   RemoteException;
         boolean connect (String  username, ClientInterface client) throws IOException;
         void   sendPM (String user1, String user2, String  privateMessage )  throws RemoteException;
}


