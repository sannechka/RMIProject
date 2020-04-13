import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class Sendler implements Runnable {

    public SynchronousQueue<Imessage> allMessages = new SynchronousQueue<>();
    private Server server;

    public Sendler(Server server) {
        this.server = server;
    }

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
                ClientInterface recipient = server.getUserByName(usersInChat.get(1));
                recipient.messageFromChat(message.getMessageFromChat());
                ClientInterface sendler = server.getUserByName(usersInChat.get(0));
                sendler.messageFromChat(message.getMessageFromChat());
            } else {
                ClientInterface sendler = server.getUserByName(usersInChat.get(0));
                sendler.messageFromChat(usersInChat.get(1) + " did not receive a message");
            }
        } else {
            for (ClientInterface a : server.getUsers()) {
                a.messageFromChat(message.getMessageFromChat());
            }
        }

    }
}

