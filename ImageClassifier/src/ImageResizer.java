import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageResizer {

    public static void main(String[] args) {
        // Verifica que se proporcionaron los argumentos necesarios
        if (args.length != 3) {
            System.out.println("Uso: java ImageResizer <carpeta_origen> <carpeta_destino> <resolucion>");
            System.exit(1);
        }

        // Obtiene los argumentos de la línea de comandos
        String carpetaOrigen = args[0];
        String carpetaDestino = args[1];
        int resolucion = Integer.parseInt(args[2]);

        // Crea el directorio de destino si no existe
        File directorioDestino = new File(carpetaDestino);
        if (!directorioDestino.exists()) {
            directorioDestino.mkdir();
        }

        // Procesa las imágenes en la carpeta de origen
        File carpeta = new File(carpetaOrigen);
        File[] archivos = carpeta.listFiles();
        for (File archivo : archivos) {
            if (archivo.isFile()) {
                try {
                    // Lee la imagen y la redimensiona
                    BufferedImage imagen = ImageIO.read(archivo);
                    int anchoOriginal = imagen.getWidth();
                    int altoOriginal = imagen.getHeight();
                    int nuevoAncho = resolucion;
                    int nuevoAlto = (altoOriginal * resolucion) / anchoOriginal;
                    BufferedImage nuevaImagen = new BufferedImage(nuevoAncho, nuevoAlto, BufferedImage.TYPE_INT_RGB);
                    nuevaImagen.createGraphics().drawImage(imagen.getScaledInstance(nuevoAncho, nuevoAlto, java.awt.Image.SCALE_SMOOTH), 0, 0, null);

                    // Guarda la imagen en la carpeta de destino
                    String nombreArchivo = archivo.getName().replaceAll("\\.\\w+$", "") + "_resized.jpg";
                    File archivoDestino = new File(directorioDestino, nombreArchivo);
                    ImageIO.write(nuevaImagen, "jpg", archivoDestino);

                    // Elimina el archivo original
                    archivo.delete();
                } catch (IOException e) {
                    System.out.println("Error al procesar la imagen " + archivo.getName() + ": " + e.getMessage());
                }
            }
        }
    }
}
