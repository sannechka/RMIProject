import com.sun.security.ntlm.Client;

import java.util.ArrayList;
import java.util.List;

public class Message  implements Imessage{

    protected String text;
    protected String user;

    public Message(String text, String user) {
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String getMessageFromChat() {
        return  user + ": " + text;
    }

    @Override
    public List<String> getClients() {
        List<String>  users = new ArrayList<>();
        users.add(user);
        return  users;
        }
    }

