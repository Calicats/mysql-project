import javax.swing.*;
import java.awt.*;

class View extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public View()
    {
        super("Logare platforma");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        JPanel usernamePanel = new JPanel();
        JPanel passwordPanel = new JPanel();
        JPanel loginPanel = new JPanel();

        JLabel usernameLabel = new JLabel("Nume");
        this.usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(this.usernameField);
        usernamePanel.setLayout(new FlowLayout());

        JLabel passwordLabel = new JLabel("Parola");
        this.passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(this.passwordField);
        passwordPanel.setLayout(new FlowLayout());

        this.loginButton = new JButton("Logare");
        loginPanel.add(loginButton);

        JPanel p = new JPanel();
        p.add(usernamePanel);
        p.add(passwordPanel);
        p.add(loginPanel);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        setContentPane(p);
        setVisible(true);
    }
    public JButton getLoginButton() {
        return loginButton;
    }
    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void showLoggedInView(String username)
    {
        setTitle("Platforma");
        setSize(600, 600);

        revalidate();
        repaint();
    }

    public void resetFields()
    {
        usernameField.setText("");
        passwordField.setText("");
    }
}