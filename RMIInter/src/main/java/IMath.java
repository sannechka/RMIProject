import com.sun.security.ntlm.Client;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IMath  extends Remote {
        public  String[] getusers() throws RemoteException;
        public String  sendMessege (String username, String  message )  throws RemoteException;
        public  void  disconnect (String  username ) throws   RemoteException;
        public boolean connect (String  username) throws IOException;
        public  void  sendPM (User user1, User user2, String  privateMessage )  throws RemoteException;
}


