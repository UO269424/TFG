package util;

import java.nio.file.Path;

public class ImageHandler {

    public static String getUserNameFromImage(Path imgPath)  {
        return String.valueOf(imgPath.getFileName()).split("_")[0];
    }

    public static String getUserIPFromImage(Path imgPath)  {
        return String.valueOf(imgPath.getFileName()).split("_")[1];
    }
}
