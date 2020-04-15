import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.rmi.RemoteException;


public class ClientGUI extends JFrame {
    private JButton messageBut, leaveChatBut;
    protected JButton pMessageBut = new JButton();
    private Border border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray);
    private JTextField textOfMessage;
    protected JPanel textPanel, userPanel, massegePanel, conectionPanel;
    private String youname, message;
    private JFrame myChat, main;
    protected JTextArea chat;
    private Client client;
    protected JList<String> users;

    public static void main(String[] args) {
        ClientGUI userForm = new ClientGUI();
        userForm.setVisible(true);
    }

    public boolean getConnected() {
        try {
            client = new Client(this, youname);
            return client.startClient(youname);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ClientGUI() {
        super("Connection");
        conectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.setSize(250, 200);
        conectionPanel.setBackground(Color.pink);
        add(conectionPanel);
        conectionPanel.add(new Label("Enter your name:"));
        JTextField name = new JTextField("", 14);
        conectionPanel.add(name);
        JButton connectBut = new JButton("Connect");
        conectionPanel.add(connectBut);
        connectBut.addActionListener(e -> {
            youname = name.getText();
            if (!youname.isEmpty()) {
                if (getConnected()) {
                    getMyChat();
                    main = this;
                    this.setVisible(false);
                }
            }
        });
    }

    public JFrame getMyChat() {
        myChat = new JFrame();
        myChat.setTitle("Welcome to CHAT!!!");
        myChat.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    client.remote.disconnect(youname);
                    main.setVisible(true);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        myChat.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        myChat.setSize(600, 600);
        myChat.add(mainPanel, BorderLayout.CENTER);
        myChat.add(getUserPanel(), BorderLayout.EAST);
        mainPanel.add(getTextPanel(), BorderLayout.SOUTH);
        mainPanel.add(getMassegePanel(), BorderLayout.CENTER);
        mainPanel.setBorder(border);
        myChat.setVisible(true);
        return myChat;
    }

    public JPanel getUserPanel() {

        userPanel = new JPanel(new BorderLayout());
        userPanel.add(new JScrollPane(users), BorderLayout.CENTER);
        pMessageBut.setText("Send a private message");
        userPanel.add(pMessageBut, BorderLayout.NORTH);
        userPanel.add(new Label("Users Online"), BorderLayout.WEST);
        pMessageBut.addActionListener(e -> {
            message = textOfMessage.getText();
            textOfMessage.setText("");
            try {
                String name2 = users.getSelectedValue();
                client.remote.sendPM(youname, name2, message);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        leaveChatBut = new JButton("Leave chat");
        leaveChatBut.setBackground(Color.lightGray);
        leaveChatBut.setFont(new Font("Meiryo", Font.PLAIN, 18));
        userPanel.add(leaveChatBut, BorderLayout.AFTER_LAST_LINE);
        leaveChatBut.addActionListener(e -> {
            try {
                client.remote.disconnect(youname);
                myChat.dispose();
                main.setVisible(true);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        return userPanel;
    }

    public JPanel getTextPanel() {
        textPanel = new JPanel(new BorderLayout());
        textPanel.add(new Label("Enter your message:"), BorderLayout.WEST);
        textOfMessage = new JTextField("", 10);
        textPanel.add(textOfMessage, BorderLayout.SOUTH);
        messageBut = new JButton("Send a message");
        textPanel.add(messageBut, BorderLayout.NORTH);
        messageBut.setBounds(200, 200, 5, 5);
        textPanel.setFont(new Font("Meiryo", Font.PLAIN, 15));
        messageBut.addActionListener(e -> {
            message = textOfMessage.getText();
            textOfMessage.setText("");
            try {
                client.remote.sendMessage(youname, message);
            } catch (RemoteException | InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        return textPanel;
    }

    public JPanel getMassegePanel() {
        massegePanel = new JPanel(new BorderLayout());
        chat = new JTextArea("[" + youname + "] connecting to chat...\n");
        chat.setFont(new Font("Meiryo", Font.PLAIN, 15));
        chat.setBackground(Color.pink);
        chat.setBorder(border);
        chat.setEditable(false);
        massegePanel.add(chat, BorderLayout.CENTER);
        return massegePanel;
    }
}










