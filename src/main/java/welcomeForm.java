import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class welcomeForm {

    private static String APP_NAME = "Securiface";

    private JPanel welcomePanel;
    private JButton bt_connect;
    private JLabel lbl_securiface;

    private static JFrame frame;

    public welcomeForm() {
        bt_connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                loginForm.main(null);
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame(APP_NAME);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new welcomeForm().welcomePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
