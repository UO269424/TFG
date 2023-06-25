package business;

import java.io.IOException;
import java.nio.file.*;

public class FileWatcher implements Runnable{

    // Ruta de la carpeta a monitorear
    String folderPath = "C:\\Users\\Alonso\\Desktop\\Screenshots";
    private Application applicationThread;

    public FileWatcher(Application applicationThread) {
        this.applicationThread = applicationThread;
    }

    @Override
    public void run() {
        try {
            // Crear el WatchService y registrar la carpeta a monitorear
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path directory = Paths.get(folderPath); // Reemplaza con la ruta correcta
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            // Monitorear la carpeta en busca de eventos
            while (true) {
                WatchKey key = watchService.take(); // Esperar hasta que ocurra un evento
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        // Se ha creado un nuevo archivo en la carpeta
                        Path filePath = directory.resolve((Path) event.context());
                        if(isImageFile(filePath))   {
                            // Pasa la ruta del archivo a la aplicación principal
                            applicationThread.setNew_image(filePath);
                        }
                    }
                }
                key.reset(); // Restablecer el WatchKey para futuros eventos
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para verificar si un archivo es una imagen (extensión .jpg, .png, .gif, etc.)
    private static boolean isImageFile(Path filePath) {
        String fileName = filePath.getFileName().toString().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

}
