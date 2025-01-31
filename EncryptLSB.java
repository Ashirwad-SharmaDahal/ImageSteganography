import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;

import javax.swing.JFileChooser;

public class EncryptLSB {
    /*
     * Driver method
     */
    public static void Encrypt(File imageFile, String message) {
        String directory = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();  //gets the directory of documents folder
        String newImageFileString = directory = "\\export.png";
        File newImageFile = new File(newImageFileString);

        BufferedImage image; //allows to put the image in buffer so that we can manipulate it
        try {
            image = ImageIO.read(imageFile);
            BufferedImage imageToEncrypt = GetImageToEncrypt(image); // copying the image to diffeent buffer because we dont want to manipulate the original image
        } catch(Exception e){

        }

    }

    private static BufferedImage GetImageToEncrypt(BufferedImage image){
        ColorModel colorModel = image.getColorModel(); //colorModel represents how colors are represented within AWT
        boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        WritableRaster raster = image.copyDate(null); //provided Pixel writing capabilities
        return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null); //now we are making new image out of these
        //raster writes the new data into the BufferedImage and colorModel tells us how to interpret that new data
    }



}