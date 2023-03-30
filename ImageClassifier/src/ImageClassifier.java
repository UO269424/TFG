import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class ImageClassifier extends JFrame implements ActionListener {
    private JLabel imageLabel;
    private JButton prevButton, nextButton, cheatButton, okButton;
    private ImageIcon[] images;
    private File[] imageFiles;
    private int currentIndex;
    private String path;

    private File trainDirectory = new File("Train/");
    private File validationDirectory = new File("Validation/");
    private File testDirectory = new File("Test/");

    private int resizeResolution = 50;

    private MyButtonKeyListener myButtonKeyListener;

    public ImageClassifier() {
        super("Image Viewer");

        // Establecer la ruta de la carpeta de imágenes
        path = "Screenshots/";

        myButtonKeyListener = new MyButtonKeyListener();

        // Cargar imágenes
        File folder = new File(path);
        imageFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") ||
                        name.toLowerCase().endsWith(".jpeg") ||
                        name.toLowerCase().endsWith(".png");
            }
        });

        images = new ImageIcon[imageFiles.length];
        for (int i = 0; i < imageFiles.length; i++) {
            images[i] = new ImageIcon(imageFiles[i].getPath());
        }
        currentIndex = 0;

        // Configurar componentes
        imageLabel = new JLabel(images[currentIndex]);
        prevButton = new JButton("Anterior");
        nextButton = new JButton("Siguiente");
        cheatButton = new JButton("Cheating");
        okButton = new JButton("Ok");
        prevButton.addActionListener(this);
        prevButton.addKeyListener(myButtonKeyListener);
        nextButton.addActionListener(this);
        nextButton.addKeyListener(myButtonKeyListener);
        cheatButton.addActionListener(this);
        cheatButton.addKeyListener(myButtonKeyListener);
        okButton.addActionListener(this);
        okButton.addKeyListener(myButtonKeyListener);

        // Agregar componentes a la ventana
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(cheatButton);
        buttonPanel.add(okButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prevButton) {
            currentIndex = (currentIndex - 1 + images.length) % images.length;
        } else if (e.getSource() == nextButton) {
            currentIndex = (currentIndex + 1) % images.length;
        } else if (e.getSource() == cheatButton) {
            cheat(currentIndex);
        } else if (e.getSource() == okButton) {
            ok(currentIndex);
        }
        imageLabel.setIcon(images[currentIndex]);
    }

    private void cheat(int currentIndex) {
        File currentImageFile = imageFiles[currentIndex];
        try {
            classify(currentImageFile, "_1.jpg");
        } catch (IOException e)   {
            System.out.println("Error al procesar la imagen " + currentImageFile.getName() + ": " + e.getMessage());
        }
    }

    private void ok(int currentIndex) {
        File currentImageFile = imageFiles[currentIndex];
        try {
            classify(currentImageFile, "_0.jpg");
        } catch (IOException e)   {
            System.out.println("Error al procesar la imagen " + currentImageFile.getName() + ": " + e.getMessage());
        }
    }

    private void classify(File currentImageFile, String ending) throws IOException {
        BufferedImage currentImage= ImageIO.read(currentImageFile);
        String nombreArchivo = currentImageFile.getName().replaceAll("\\.\\w+$", "") + ending;
        File archivoDestino = new File(trainDirectory, nombreArchivo);
                            //new File(validationDirectory, nombreArchivo);
                            //new File(testDirectory, nombreArchivo);
        BufferedImage nuevaImagen = ImageResizer.resize(currentImage, resizeResolution);
        ImageIO.write(nuevaImagen, "jpg", archivoDestino);
        nextButton.doClick();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ImageClassifier().setVisible(true);
        });
    }


    class MyButtonKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                // Simulate a button click
                nextButton.doClick();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT)  {
                prevButton.doClick();
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                okButton.doClick();
            } else if (e.getKeyCode() == KeyEvent.VK_F) {
                cheatButton.doClick();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}