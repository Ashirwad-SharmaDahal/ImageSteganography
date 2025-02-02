import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;

public class MainPage {
    private JFrame frame;
    private JTextField messageField; // Declare messageField as an instance variable

    /**
     * Launch the application
     */
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

    /**
     * Create the application
     */
    public MainPage() {
        initialize();
    }

    /**
     * Initialize the contents of the frame
     */
    private void initialize() {
        frame = new JFrame("Image Steganography");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Button Panel (Encrypt & Decrypt)
        JPanel buttonsPanel = new JPanel();
        frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 10)); // 1 row, 2 columns, spacing

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
        messageLabel.setBounds(40, 58, 72, 26);
        messagePanel.add(messageLabel);

        messageField = new JTextField();
        messageField.setBounds(115, 63, 301, 20);
        messagePanel.add(messageField);
        messageField.setColumns(10);

        ActionListener encryptListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                File ImageFile = FileChooser.MakeFileChooser();
                if(ImageFile != null) {
                    EncryptLSB.Encrypt(ImageFile, messageField.getText());
                }
            }
        };
        encryptButton.addActionListener(encryptListener);
        
        ActionListener decryptListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                DecryptLSB.Decrypt();
        
            }
        
        };
        decryptButton.addActionListener(decryptListener);
        
        }    
        }
