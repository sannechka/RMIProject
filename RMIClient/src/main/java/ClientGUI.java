import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.Border;


public class ClientGUI extends JFrame {

    private JButton connectBut = new JButton("Connect");
    private JButton messageBut = new JButton("send a message:");
    public Label label = new Label("Enter your name:");
    public Label label2 = new Label("Enter your messege");
    public Label label3 = new Label("Online");
    public JTextField name = new JTextField("", 5);
    public JTextField messagee= new JTextField("", 10);
    private JPanel textPanel, inputPanel, userpanel;
    private String youname, message;
    JFrame registration;
    JFrame chat;
    JTextArea chaat = new JTextArea();
    Client a;
    JList<String> users;

    public static  void main(String[] args) throws NamingException, RemoteException {
      ClientGUI  s= new ClientGUI();
      s.setVisible(true);
    }
    public void getConnected() throws RemoteException {
        try {
            a = new Client(this);
            a.startClient(name.getText());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
                getConnected();
                chat = new JFrame();
                chat.setLayout( new BorderLayout());
                JPanel mainPanel = new JPanel(new BorderLayout());
                textPanel = new JPanel(new BorderLayout());
                JPanel massegePanel= new JPanel();
                userpanel = new JPanel();
                chat.setSize(500, 600);
                chat.add(mainPanel, BorderLayout.CENTER);
                chat.add(userpanel, BorderLayout.EAST);
                mainPanel.add(textPanel,BorderLayout.SOUTH);
                mainPanel.add(massegePanel, BorderLayout.NORTH);
                userpanel.add( new JScrollPane(users));
                userpanel.add(label3, BorderLayout.NORTH);
                updatelist();
                chaat.setBackground(Color.PINK);
                mainPanel.add(chaat);
                textPanel.add(label2, BorderLayout.WEST);
                textPanel.add(messagee,BorderLayout.SOUTH);
                textPanel.add(messageBut,BorderLayout.NORTH);
                updatelist();
                messageBut.setBounds(200, 200, 5,5);
                messagee.setBounds(200, 200, 5,5);
                chat.setVisible(true);
                textPanel.setVisible(true);
                messageBut.addActionListener(new ButtonEventListener2());

            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }
        class ButtonEventListener2 implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                message = messagee.getText();
                messagee.setText("");
                try {
                    String g = a.remote.sendMessege(youname , message);
                    chaat.append(g);
                    chaat.setText(chaat.getText() + "\n");
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        }

    public void updatelist () {
        try {
            String[] online = a.remote.getusers();
            System.out.println(online);;
            users = new JList<String>(online);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}








