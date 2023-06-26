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

        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.CENTER);
        add(previousButton, BorderLayout.WEST);
        add(nextButton, BorderLayout.EAST);
    }

    private void updateImage() {
        Path imagePath = imagePaths.get(currentIndex);
        ImageIcon imageIcon = new ImageIcon(imagePath.toString());
        Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));
    }
}
