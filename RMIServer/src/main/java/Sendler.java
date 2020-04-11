import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class Sendler {

    public Map<String, ClientInterface> usersOnline = new HashMap<>();
    public SynchronousQueue<Imessage> allMessages = new SynchronousQueue<>();

    public void sendMessage() throws RemoteException{
        Imessage message = null;
        try {
            message = allMessages.take();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        if(message==null){
            return;
        }
        List<String> usersInChat = message.getClients();
        if (usersInChat.size() == 2) {
            if (usersInChat.get(1) != null) {
                ClientInterface recipient = usersOnline.get(usersInChat.get(1));
                recipient.messageFromChat(message.getMessageFromChat());
                ClientInterface sendler = usersOnline.get(usersInChat.get(0));
                sendler.messageFromChat(message.getMessageFromChat());
            } else {
                ClientInterface sendler = usersOnline.get(usersInChat.get(0));
                sendler.messageFromChat(usersInChat.get(1) + " did not receive a message");
            }
        } else {
            Set<Map.Entry<String, ClientInterface>> users = usersOnline.entrySet();
            for (Map.Entry<String, ClientInterface> a : users) {
                a.getValue().messageFromChat(message.getMessageFromChat());
            }
        }

    }
}

