package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.List;

class ImageViewer extends JFrame {
    private JLabel imageLabel;
    private JButton previousButton;
    private JButton nextButton;
    private List<Path> imagePaths;
    private int currentIndex;

    public ImageViewer(List<Path> imagePaths) {
        this.imagePaths = imagePaths;
        currentIndex = 0;

        setTitle("Image Viewer");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        updateImage();

        setVisible(true);
    }

    private void initComponents() {
        imageLabel = new JLabel();
        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex > 0) {
                    currentIndex--;
                    updateImage();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < imagePaths.size() - 1) {
                    currentIndex++;
                    updateImage();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(previousButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(nextButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);

        // Agregar un ComponentListener para detectar cambios de tamaño en la ventana
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updateImage();
            }
        });
    }

    private void updateImage() {
        Path imagePath = imagePaths.get(currentIndex);
        ImageIcon imageIcon = new ImageIcon(imagePath.toString());
        int width = imageLabel.getWidth();
        int height = imageLabel.getHeight();
        if(width == 0 || height == 0)   {
            width = 300;
            height = 300;
        }
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageIcon.setImage(image);
        imageLabel.setIcon(imageIcon);
    }
}
