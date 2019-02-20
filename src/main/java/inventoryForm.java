import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class inventoryForm {
    private JPanel inventoryPanel;
    private JButton bt_back;
    private JLabel lbl_nom;
    private JLabel lbl_prenom;
    private JLabel lbl_role;
    private JScrollBar scrollBar1;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;

    private static JFrame frame;

    public inventoryForm() {


        lbl_nom.setText("test");
        lbl_prenom.setText("test-prenom");


        bt_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                loginForm.main(null);

            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("inventoryForm");
        frame.setContentPane(new inventoryForm().inventoryPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

}
