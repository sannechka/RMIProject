
public class Message  implements Imessage{

    protected String text;
    protected User user;

    public Message(String text, User user) {
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getMessageFromChat() {
        return  user + ": " + text;
    }
}
