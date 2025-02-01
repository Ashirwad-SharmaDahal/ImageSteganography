import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Color;
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
            Pixel[] pixels = GetPixelArray(imageToEncrypt);
            String[] messageBinary = ConvertMessageToBinary(message);
            EncodeMessageBinaryInPixels(pixels, messageBinary);
            ReplacePixelsInNewBufferedImage(pixels, imageToEncrypt);
            SaveNewFile(imageToEncrypt, newImageFile);
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

    private static Pixel[] GetPixelArray(BufferedImage imageToEncrypt){
        int height = imageToEncrypt.getHeight();
        int width = imageToEncrypt.getWidth();
        Pixel[] pixels = new Pixel[height * width];
        int count = 0;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                Color colorToAdd = new Color(imageToEncrypt.getRGB(x, y));
                pixels[count] = new Pixel(x, y, colorToAdd);
                count++;
            }
        }
        return pixels;

    }

    private static String[] ConvertMessageToBinary(String message){
        int messageAscii = ConvertMessageToAscii(message);
        String messageBinary = ConvertAsciiToBinary(messageAscii);
        return messageBinary;
    }

    private static int[] ConvertMessageToAscii(String message){
        int[] messageAscii = new int[message.length()];
        for(int i = 0; i < message.length(); i++){
        messageAscii[i] = (int) message.charAt(i);
        }
        return messageAscii;

    }

    private static String[] ConvertAsciiToBinary(int[] asciiValues){
        String[] messageBinary = new String[asciiValues.length];
        for(int i = 0; i < asciiValues.length(); i++){
            String binary = LeftPadZeros(Integer.toBinaryString(asciiValues[i]));
            messageBinary[i] = binary;
        }  
    }

    private static String LeftPadZeros(String Binary){
        StringBuilder sb = new StringBuilder("00000000");
        int offSet = 8 - binary.length();
        for(int i = 0; i < Binary.length(); i++){
            sb.setCharAt(i + offSet, Binary.charAt(i));
        }
        return sb.toString();
    }

    private static void EncodeMessageBinaryInPixels(Pixel[] pixels, String[] messageBinary) {
        int pixelIndex = 0;
        boolean isLastCharacter = false;
        for(int i = 0; i < messageBinary.length(); i++){
            Pixel[] currentPixels = new Pixel[] {pixels[pixelIndex], pixels[pixelIndex + 1], pixels[pixelIndex + 2]};
            if(i + 1 == messageBinary.length){
                isLastCharacter = true;
            }
            ChangePixelsColor(messageBinary[i], currentPixels, isLastCharacter);  //change the current pixels colors in order to hide the message in binary 
        }
    }

    private static void ChangePixelsColor(String messageBinary, Pixel[] pixels, boolean isLastCharacter){
        int messageIndex = 0;
        for(int i = 0; i < pixels.length-1; i++){
            char[] messageBinaryCharacter = new char[] {messageBinary.charAt(messageIndex), messageBinary.charAt(messageIndex+1),messageBinary.charAt(messageIndex+2)};
            String[] pixelRGBBinary = GetPixelsRGBBinary(pixels[1], messageBinaryCharacter);
            pixels[i].setColor(GetNewPixeLColor(pixelRGBBinary));
            messageIndex = messageIndex + 3;
        }
        if(isLastCharacter == false){
            char[] messageBinaryChars = new char[] {messageBinary.charAt(messageIndex), messageBinary.charAt(messageIndex+1), '1'};
            String[] pixelRGBBinary = GetPixelsRGBBinary(pixels[pixels.length-1], messageBinaryChars);
            pixels[pixels.length-1].setColor(GetNewPixeLColor(pixelRGBBinary));
        }
        else{
            char[] messageBinaryChars = new char[] {messageBinary.charAt(messageIndex), messageBinary.charAt(messageIndex+1), '0'};
            String[] pixelRGBBinary = GetPixelsRGBBinary(pixels[pixels.length-1], messageBinaryChars);
            pixels[pixels.length-1].setColor(GetNewPixeLColor(pixelRGBBinary));
        }
    }

    private static String[] GetPixelsRGBBinary(Pixel pixel, char[] messageBinaryChars){  //turn the pixels into integer values and into binary string and change the LSB of binary into messageBinaryCHars
            String[] pixelRGBBinary = new String[3];
            pixelRGBBinary[0] = ChangePixelBinary(Integer.toBinaryString(pixel.getColor().getRed()), messageBinaryChar[0]);
            pixelRGBBinary[1] = ChangePixelBinary(Integer.toBinaryString(pixel.getColor().getGreen()), messageBinaryChar[1]);
            pixelRGBBinary[2] = ChangePixelBinary(Integer.toBinaryString(pixel.getColor().getBlue()), messageBinaryChar[2]);
            return pixelRGBBinary;

    }

    private static String ChangePixelBinary(String pixelBinary, char messageBinaryChar){
        StringBuilder sb = new StringBuilder(pixelBinary);
        sb.setCharAt(pixelBinary.length()-1, messageBinaryChar); //last character in pixelBinary to messageBinaryChar
        return sb.toString();
    }

    private static Color GetNewPixeLColor(String[] colorBinary){
        return new Color(Integer.parseInt(colorBinary[0],2), Integer.parseInt(colorBinary[1],2), Integer.parseInt(colorBinary[2], 2));

    }

    private static void ReplacePixelsInNewBufferedImage(Pixel[] pixels, BufferedImage imageToEncrypt){
          for(int i = 0; i < pixels.length; i++){
            imageToEncrypt.setRGB(pixels[i].getX(), pixels[i].getY(), pixels[i].getColor().getRGB());
          }
    }

    private static void SaveNewFile(BufferedImage newImage, File newImageFile) {
        try{
            ImageIO.write(newImage, "png", newImageFile);
        } catch(IOException e){
            e.printStackTrace();
        }    
    }
}