import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileChooser {
    public static File selectImageFile(JFrame parent) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Select Image File");
    int option = fileChooser.showOpenDialog(parent);
    if (option == JFileChooser.APPROVE_OPTION) {
        return fileChooser.getSelectedFile();
    }
    return null;
}

    public static File saveImageFile(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Encrypted Image");
        int option = fileChooser.showSaveDialog(parent);
        if (option == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
