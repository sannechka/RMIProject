import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class IMathImpl extends UnicastRemoteObject implements IMath {
        protected IMathImpl() throws RemoteException {
        }

        public int add(int a, int b) {
            return a + b;
        }

         public String sendMessege(String username, String message){
            return username + message;
        }
}

