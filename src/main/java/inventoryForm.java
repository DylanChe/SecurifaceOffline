import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class inventoryForm {
    private JPanel inventoryPanel;
    private JButton bt_back;
    private JLabel lbl_nom;
    private JLabel lbl_prenom;
    private JLabel lbl_role;
    private JLabel image;
    private JPanel materialPanel;

    private static JFrame frame;
    private static String userMatricule;
    private static JPanel invPanel;

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

        // On récupère la liste des Materiel de la BDD
        ArrayList<Materiel> materiels = Materiel.getListMateriel();

        // On ajoute la liste
        materialPanel.setLayout(new BoxLayout(materialPanel, BoxLayout.PAGE_AXIS));
        for (Materiel materiel : materiels) {
            // ========================================
            boolean isEmpty = Materiel.isEmpty(materiel.getNom());
            boolean isReserved = Agent.getReservation(Integer.valueOf(userMatricule), materiel.getNom());

            JPanel matPan = new JPanel();
            matPan.setLayout(new BorderLayout());

            // ----- LABEL
            JLabel lbl = new JLabel(materiel.getNom());
            matPan.add(lbl, BorderLayout.LINE_START);

            // ----- CHECKBOX
            Checkbox cb = new Checkbox();
            cb.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (cb.getState()) {
                        // =======================================
                        System.out.println("SELECTED " + materiel.getNom() + " !");
                        Materiel.setQteMateriel(materiel.getNom(), -1);
                        Agent.putReservation(Integer.valueOf(userMatricule), materiel.getNom());
                    } else {
                        // =======================================
                        System.out.println("UNSELECTED " + materiel.getNom() + " !");
                        Materiel.setQteMateriel(materiel.getNom(), +1);
                        Agent.removeReservation(Integer.valueOf(userMatricule), materiel.getNom());
                    }
                }
            });
            matPan.add(cb, BorderLayout.LINE_END);
            matPan.setMaximumSize(new Dimension(Integer.MAX_VALUE, matPan.getMinimumSize().height + 5));

            if (isReserved) {
                cb.setState(true);
            } else {
                lbl.setEnabled(!isEmpty);
                cb.setEnabled(!isEmpty);
            }

            materialPanel.add(matPan);
        }

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
        invPanel = new inventoryForm().inventoryPanel;
        frame.setContentPane(invPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }

}
