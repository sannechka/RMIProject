import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.Border;


public class ClientGUI extends JFrame {
    private JButton connectBut = new JButton("Connect");
    private JButton messageBut = new JButton("Send a message");
    private JButton pmessageBut = new JButton("Send a private message");
    private JButton leaveChat = new JButton("Leave chat");
    public Label label = new Label("Enter your name:");
    public Label messegelabel = new Label("Enter your messege:");
    public Label usersOnline = new Label("Online");
    public JTextField name = new JTextField("", 5);
    public JTextField textOfMessage= new JTextField("", 10);
    private JPanel textPanel, inputPanel, userpanel, massegePanel;
    private String youname, message;
    JFrame registration;
    JFrame myChat;
    JTextArea chat;
    Client a;
    JList<String> users;

    public static  void main(String[] args) throws NamingException, RemoteException {
      ClientGUI  s= new ClientGUI();
      s.setVisible(true);
    }
    public boolean getConnected() throws RemoteException {
        try {
            a = new Client(this);
          boolean s;
          s = a.startClient(name.getText());
          return s;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ClientGUI() {
        super("Connection");
        inputPanel = new JPanel();
        this.setSize(400, 400);
        add(inputPanel);
        inputPanel.add(label);
        label.setLocation(150,150);
        inputPanel.add(name);
        inputPanel.add(connectBut);
        connectBut.addActionListener(new ButtonEventListener());
}
    class ButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            youname = name.getText();
            try {
                 if(getConnected()){
                myChat = new JFrame();
                myChat.setLayout( new BorderLayout());
                JPanel mainPanel = new JPanel(new BorderLayout());
                myChat.setSize(500, 600);
                myChat.add(mainPanel, BorderLayout.CENTER);
                myChat.add(getUserPanel(), BorderLayout.EAST);
                mainPanel.add(getTextPanel(),BorderLayout.SOUTH);
                mainPanel.add(getmassegePanel(), BorderLayout.NORTH);
                myChat.setVisible(true);}
                 else {
                     System.out.println("Такой пользователь уже есть");
                 }


        } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    public JPanel getUserPanel() {
        updatelist();
        userpanel = new JPanel(new BorderLayout());
        userpanel.add( new JScrollPane(users), BorderLayout.CENTER);
        userpanel.add(pmessageBut, BorderLayout.NORTH);
        pmessageBut.addActionListener(new ButtonEventListener3());
        userpanel.add(usersOnline, BorderLayout.EAST);
        userpanel.add(leaveChat, BorderLayout.AFTER_LAST_LINE);
        leaveChat.addActionListener(new ButtonEventListener4());

        return userpanel;
    }

    public JPanel getTextPanel(){
        String welcome = "Welcome enter your name and press Start to begin\n";
        textPanel = new JPanel(new BorderLayout());
        textPanel.add(messegelabel, BorderLayout.WEST);
        textPanel.add(textOfMessage,BorderLayout.SOUTH);
        textPanel.add(messageBut,BorderLayout.NORTH);
        messageBut.setBounds(200, 200, 5,5);
        textPanel.setFont(new Font("Meiryo", Font.PLAIN, 15));
        messageBut.addActionListener(new ButtonEventListener2());
        return textPanel;
    }
    public JPanel getmassegePanel(){
        massegePanel = new JPanel(new GridLayout(1, 1, 1, 1));
        chat = new JTextArea("username : " + name.getText() + " connecting to chat...\n", 19, 20);
        chat.setBackground(Color.PINK);
        massegePanel.add(chat);
        return massegePanel;
    }
    class ButtonEventListener2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            message = textOfMessage.getText();
            textOfMessage.setText("");
            try {
                a.remote.sendMessege(youname , message);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        }
    class ButtonEventListener3 implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                message = textOfMessage.getText();
                textOfMessage.setText("");
                try {
                    String name2 = users.getSelectedValue();
                    a.remote.sendPM(youname, name2 , message);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        }
        class ButtonEventListener4 implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                try {  a.remote.disconnect(youname);
                    myChat.dispose();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        }

    public void updatelist () {
        try {
            String[] online = a.remote.getusers();
            System.out.println(online);;
            if(online.length != 0){
                usersOnline.setText( "ONLINE");
            users = new JList<String>(online);}
            else { usersOnline.setText( "No other users");}

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    }}








