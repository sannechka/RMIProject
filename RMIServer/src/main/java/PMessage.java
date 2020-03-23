public class PMessage extends Message {
    User user2;
    public PMessage(String text, User user, User user2) {
        super(text, user);
        this.user2= user2;
    }
}
