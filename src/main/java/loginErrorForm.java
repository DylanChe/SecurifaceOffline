import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginErrorForm {
    private JPanel loginErrorPanel;
    private JButton okButton;
    private JLabel lbl_message;

    public loginErrorForm() {

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(loginErrorPanel).dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("loginErrorForm");
        frame.setContentPane(new loginErrorForm().loginErrorPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        frame.toFront();
        frame.requestFocus();
    }
}
