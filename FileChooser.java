import java.io.File;
import javax.swing.JFileChooser;

public class FileChooser {
    public static File MakeFileChooser(){
        JFileChooser chooser = new JFileChooser(); //this instantiates chooser opject

        int option = chooser.showOpenDialog(null); //this displays chooser object to the user
        if(option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();  //if the user selects the approve option on a file then we are gonna get that file and return it
            return file;
        }
        return null;  //else we return null and nothing happens
    }
}
