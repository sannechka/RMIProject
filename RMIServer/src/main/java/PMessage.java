import java.util.ArrayList;
import java.util.List;

public class PMessage extends Message  implements Imessage{
    String recipient;

    public PMessage(String text, String user, String recipient) {
        super(text, user);
        this.recipient = recipient;
    }
    @Override
    public String getMessageFromChat() {
        return  super.user + " FROM " + recipient + ": " + super.text;
    }
    @Override
    public List<String> getClients() {
        List<String>  users = new ArrayList<>();
        users.add(user);
        users.add(recipient);
        return  users;
    }
    }

