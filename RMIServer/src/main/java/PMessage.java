public class PMessage extends Message  implements Imessage{
    User user2;

    public PMessage(String text, User user, User user2) {
        super(text, user);
        this.user2 = user2;
    }
    public String getMessageFromChat() {
        return  super.user.getName() + " FROM " + user2.getName() + ": " + super.text;
    }
    }

