package gui;

import business.Alert;
import business.AlertHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainWindow extends JFrame implements Observer {

    private JPanel alertsPanel;
    private List<AlertPanel> alertPanelsList;

    private static MainWindow instance;

    public static MainWindow getInstance(EnterWindow parent)  {
        if(instance == null)   {
            instance = new MainWindow(parent);
        }
        return instance;
    }

    private MainWindow(EnterWindow parent) {
        setTitle("Image Classification App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Panel de alertas
        alertsPanel = new JPanel();
        alertsPanel.setLayout(new BoxLayout(alertsPanel, BoxLayout.Y_AXIS));
        JScrollPane alertsScrollPane = new JScrollPane(alertsPanel);
        mainPanel.add(alertsScrollPane, BorderLayout.CENTER);

        // Botón de eliminar todas las alertas
        JButton clearAllButton = new JButton("Eliminar todas las alertas");
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllAlerts();
            }
        });
        mainPanel.add(clearAllButton, BorderLayout.SOUTH);

        // Agregar panel principal a la ventana
        add(mainPanel);

        // Inicializar lista de alertas
        alertPanelsList = new ArrayList<>();

        // Mostrar la ventana
        setVisible(true);
    }

    // Método para crear una nueva alerta
    public void createAlert(Alert a) {
        List<ImageIcon> images = new ArrayList<>();
        for(Path path: a.getImages())   {
            images.add(new ImageIcon(String.valueOf(path)));
        }
        AlertPanel alertPanel = new AlertPanel(images);
        alertsPanel.add(alertPanel);
        alertPanelsList.add(alertPanel);
        validate();
        repaint();
        a.setDisplayed(true);
    }

    // Método para eliminar todas las alertas
    public void clearAllAlerts() {
        alertsPanel.removeAll();
        alertPanelsList.clear();
        AlertHandler.getInstance().removeDisplayedAlerts();
        validate();
        repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        for(Alert a : AlertHandler.getInstance().notDisplayedAlerts()) {
            createAlert(a);
        }
    }

    // Clase interna para representar una alerta individual
    private class AlertPanel extends JPanel {
        private List<ImageIcon> images;

        public AlertPanel(List<ImageIcon> images) {
            this.images = images;
            setLayout(new FlowLayout());

            // Agregar imágenes al panel de alerta
            for (ImageIcon image : images) {
                JLabel imageLabel = new JLabel(image);
                add(imageLabel);
            }

            // Botón para eliminar la alerta
            JButton removeButton = new JButton("Eliminar alerta");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeAlert();
                }
            });
            add(removeButton);
        }

        // Método para eliminar la alerta actual
        private void removeAlert() {
            alertsPanel.remove(this);
            alertPanelsList.remove(this);
            validate();
            repaint();
        }
    }
}
