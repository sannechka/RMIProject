import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.Border;


public class ClientGUI extends JFrame {

    private JButton connectBut = new JButton("Connect");
    private JButton messageBut = new JButton("send a message");
    public Label label = new Label("Enter your name:");
    public Label label2 = new Label("Enter your messege");
    public JTextField name = new JTextField("", 5);
    public JTextField messagee= new JTextField("", 10);
    private JPanel textPanel, inputPanel;
    private String youname, message;
    JFrame registration, chat;
    JTextArea chaat = new JTextArea();
    Client a;

    public static  void main(String[] args) throws NamingException, RemoteException {
      ClientGUI  s= new ClientGUI();
      s.setVisible(true);


    }
    public void getConnected() throws RemoteException {

        try {
            a = new Client(this);
            a.startClient();
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
                String g = a.remote.sendMessege(youname, "ehf");
                System.out.println(g);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
            chat = new JFrame();
            textPanel = new JPanel();
            chat.setSize(400, 500);
            chat.add(textPanel);
            textPanel.add(label2);
            textPanel.add(messagee);
            textPanel.add(messageBut);
            textPanel.add(chaat);
            messagee.setLocation(200, 200);
            chat.setVisible(true);
            textPanel.setVisible(true);
            messageBut.addActionListener(new ButtonEventListener2());
        }
    }
        class ButtonEventListener2 implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                message = messagee.getText();
                System.out.println("");
                try {
                    String g = a.remote.sendMessege(youname + " ", message);
                    System.out.println(g);
                    chaat.append(g);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        }}








