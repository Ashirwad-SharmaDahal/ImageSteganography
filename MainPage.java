import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainPage {
    private JFrame frame;
    private JTextField messageField;
    private JLabel decryptedMessageLabel;
    private JLabel imageLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainPage window = new MainPage();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainPage() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Image Steganography");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Button Panel (Encrypt & Decrypt)
        JPanel buttonsPanel = new JPanel();
        frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 10));

        JButton encryptButton = new JButton("Encrypt");
        buttonsPanel.add(encryptButton);

        JButton decryptButton = new JButton("Decrypt");
        buttonsPanel.add(decryptButton);

        // Message Input Panel
        JPanel messagePanel = new JPanel();
        frame.getContentPane().add(messagePanel, BorderLayout.CENTER);
        messagePanel.setLayout(null);

        JLabel messageLabel = new JLabel("Message:");
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        messageLabel.setBounds(40, 20, 72, 26);
        messagePanel.add(messageLabel);

        messageField = new JTextField();
        messageField.setBounds(120, 25, 400, 20);
        messagePanel.add(messageField);
        messageField.setColumns(10);

        decryptedMessageLabel = new JLabel("Decrypted Message will appear here");
        decryptedMessageLabel.setBounds(40, 60, 500, 20);
        messagePanel.add(decryptedMessageLabel);

        imageLabel = new JLabel();
        imageLabel.setBounds(40, 100, 500, 200);
        messagePanel.add(imageLabel);

        // Encrypt Button Action
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (message.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a message to encrypt.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                File imageFile = FileChooser.selectImageFile(frame);
                if (imageFile == null) {
                    return; // User canceled file selection
                }

                File outputFile = FileChooser.saveImageFile(frame);
                if (outputFile == null) {
                    return; // User canceled file save
                }

                try {
                    EncryptLSB.encrypt(imageFile, message, outputFile);
                    BufferedImage modifiedImage = ImageIO.read(outputFile);
                    imageLabel.setIcon(new ImageIcon(modifiedImage));
                    JOptionPane.showMessageDialog(frame, "Message encrypted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    // CHANGE: Show error message if encryption fails
                    JOptionPane.showMessageDialog(frame, "Error encrypting message: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Decrypt Button Action
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File imageFile = FileChooser.selectImageFile(frame);
                if (imageFile == null) {
                    return; // User canceled file selection
                }

                try {
                    String decryptedMessage = DecryptLSB.decrypt(imageFile);
                    decryptedMessageLabel.setText("Decrypted Message: " + decryptedMessage);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error decrypting message: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}