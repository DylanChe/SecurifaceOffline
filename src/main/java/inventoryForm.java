import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

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
    private JLabel image;

    private static JFrame frame;
    private static String userMatricule;

    public inventoryForm() {

        // On renseigne les infos de lagent
        lbl_nom.setText(Agent.getAgent(Integer.valueOf(userMatricule)).getNom());
        lbl_prenom.setText(Agent.getAgent(Integer.valueOf(userMatricule)).getPrenom());
        lbl_role.setText(Agent.getAgent(Integer.valueOf(userMatricule)).getPoste());

        // On renseigne la photo de lagent
        ImageIcon tmp = (ImageIcon) image.getIcon();
        ImageIcon originalImage = new ImageIcon(getClass().getClassLoader().getResource(Agent.getAgent(Integer.valueOf(userMatricule)).getChemin_photo()));
        ImageIcon scaledImage = new ImageIcon(originalImage.getImage().getScaledInstance(200,150, Image.SCALE_SMOOTH));
        image.setIcon(scaledImage);


        bt_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                loginForm.main(null);
            }
        });
    }

    public static void main(String[] args, String matricule) throws MalformedURLException {
        userMatricule = matricule;
        frame = new JFrame("inventoryForm");
        frame.setContentPane(new inventoryForm().inventoryPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }

}
