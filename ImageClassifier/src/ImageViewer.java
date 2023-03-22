import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;

public class ImageViewer extends JFrame implements ActionListener {
    private JLabel imageLabel;
    private JButton prevButton, nextButton;
    private ImageIcon[] images;
    private int currentIndex;
    private String path;

    public ImageViewer() {
        super("Image Viewer");

        // Establecer la ruta de la carpeta de imágenes
        path = "Resized/";

        // Cargar imágenes
        File folder = new File(path);
        File[] imageFiles = folder.listFiles(new FilenameFilter() {
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
        prevButton.addActionListener(this);
        nextButton.addActionListener(this);

        // Agregar componentes a la ventana
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

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
        }
        imageLabel.setIcon(images[currentIndex]);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ImageClassifier().setVisible(true);
        });
    }
}
