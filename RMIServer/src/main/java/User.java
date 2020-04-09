

public class User {
    public String name;
    public ClientInterface client;

    public User(String name, ClientInterface client) {
        this.name = name;
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public ClientInterface getClient() {
        return client;
    }

}

