import com.amazonaws.services.rekognition.model.Image;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class loginForm {

    private static String APP_NAME = "Securiface";
    private static String PICS_EXTENSION = "jpg";

    private JPanel loginPanel;
    private JButton bt_login;
    private JTextField txt_id;
    private JPanel pnl_webcam;

    private Webcam webcam;

    private static JFrame frame;

    public loginForm() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(false);
        panel.setDisplayDebugInfo(false);
        panel.setImageSizeDisplayed(false);
        panel.setMirrored(true);

        pnl_webcam.add(panel);

        bt_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Aucun identifiant
                if (txt_id.getText().length() == 0) {
                    loginErrorForm.main(null);
                }

                Thread t = new Thread() {
                    public void run() {
                        bt_login.setEnabled(false);
                        bt_login.setText("Authentification...");

                        String picsBddName = txt_id.getText() + "." + PICS_EXTENSION;

                        try {
                            BufferedImage bufImg = webcam.getImage();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(bufImg, "jpg", baos);
                            ByteBuffer byteBuffer = ByteBuffer.wrap(baos.toByteArray());

                            // ----- AJOUTER UN AGENT
                            //ImageIO.write(bufImg, "jpg", new File("./src/main/resources/" + txt_id.getText() + ".png"));

                            if (CompareFaces.compareFace(new Image().withBytes(byteBuffer), picsBddName)) {
                                System.out.println("OK !");
                                webcam.close();
                                frame.dispose();
                                inventoryForm.main(null, "a");
                            } else {
                                loginErrorForm.main(null);
                            }

                        } catch (Exception e) {
                            loginErrorForm.main(null);
                        }

                        bt_login.setEnabled(true);
                        bt_login.setText("S'authentifier");
                    }
                };
                t.start();
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame(APP_NAME);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new loginForm().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
