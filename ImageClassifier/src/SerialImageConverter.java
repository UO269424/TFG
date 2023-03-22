import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SerialImageConverter {

    private static String p = "Screenshots/";
    private static String targetFolder = //"Cheat/";
                                            //"Ok/";
                                            "TestingImages/";

    private static String ending = //"_1.jpg";
                                    "_0.jpg";
                                    //".jpg";

    public static void main(String[] args) {

        String[] imageNames = new File(p).list((dir, name) -> name.toLowerCase().endsWith(".png"));
        int count = 0;
        for (String imageName : imageNames) {
            try {
                File inputFile = new File(p + imageName);
                BufferedImage inputImage = ImageIO.read(inputFile);
                String outputName = imageName.substring(0, imageName.length() - 4) + ending;
                File outputFile = new File(targetFolder + outputName);
                BufferedImage newImage = ImageResizer.resize(inputImage, 50);
                ImageIO.write(newImage, "jpg", outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            count++;
            if(count%100==0) {
                System.out.printf("%s images converted", count);
                System.out.println();
            }

        }
        System.out.println("Se han convertido todas las imágenes.");
    }
}
