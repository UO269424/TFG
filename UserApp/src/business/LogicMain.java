package business;

import gui.MainWindow;

public class LogicMain {

    public static void run(MainWindow mainWindow)    {
        // Crear la instancia de AppRunnable
        Application application = new Application();
        application.addObserver(mainWindow);

        // Crear y ejecutar el hilo de la aplicación principal
        Thread appThread = new Thread(application);
        appThread.start();

        // Crear y ejecutar el hilo del WatchService, pasando una referencia de la aplicación principal
        Thread watchThread = new Thread(new FileWatcher(application));
        watchThread.start();
    }
}
