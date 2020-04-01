import com.sun.security.ntlm.Client;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInterface  extends Remote {
        public  String[] getusers() throws RemoteException;
        public void   sendMessege (String username, String  message )  throws RemoteException;
        public  void  disconnect (String  username ) throws   RemoteException;
        public boolean connect (String  username, ClientInterface client) throws IOException;
        public  void   sendPM (String user1, String user2, String  privateMessage )  throws RemoteException;
}


