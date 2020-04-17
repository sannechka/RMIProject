import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

public class Sendler extends Thread  {

    protected SynchronousQueue<Imessage> allMessages = new SynchronousQueue<>();
    protected Map<String, ClientInterface> usersOnline = new ConcurrentHashMap<>();


    @Override
    public void run() {
        while (true) {
            try {
                sendMessage();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage() throws RemoteException {
        Imessage message = null;
        try {
            message = allMessages.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (message == null) {
            return;
        }
        List<String> usersInChat = message.getClients();
        if (usersInChat.size() == 2) {
            if (usersInChat.get(1) != null) {
                ClientInterface recipient = usersOnline.get(usersInChat.get(1));
                recipient.messageFromChat(message.getMessageFromChat());
                ClientInterface sendler = usersOnline.get(usersInChat.get(0));
                sendler.messageFromChat(message.getMessageFromChat());
                System.out.println(message.getMessageFromChat());
            } else {
                ClientInterface sendler = usersOnline.get(usersInChat.get(0));
                sendler.messageFromChat(usersInChat.get(1) + " did not receive a message");
            }
        } else {
            for (ClientInterface a : new HashSet<>(usersOnline.values())) {
                a.messageFromChat(message.getMessageFromChat());
            }
            System.out.println(message.getMessageFromChat());
        }
    }

}


