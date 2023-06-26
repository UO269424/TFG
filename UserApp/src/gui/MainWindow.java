package gui;

import business.Alert;
import business.AlertHandler;
import business.observer.MyObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainWindow extends JFrame implements MyObserver {

    private static MainWindow instance;

    public static MainWindow getInstance() {
        if(instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    private MainWindow() {

        setTitle("Alert List");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        List<Alert> alerts = AlertHandler.getInstance().getNotDisplayedAlerts();
        for (Alert alert : alerts) {
            JLabel label = new JLabel(String.format("%s@%s", alert.getUser().getName(), alert.getUser().getIp()));
            JButton viewImagesButton = new JButton("View Images");
            JButton removeAlertButton = new JButton("Remove Alert");

            viewImagesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ImageViewer imageViewerFrame = new ImageViewer(alert.getImages());
                }
            });

            removeAlertButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AlertHandler.getInstance().removeAlert(alert);
                    update();
                }
            });

            JPanel alertPanel = new JPanel();
            alertPanel.setLayout(new FlowLayout());
            alertPanel.add(label, BorderLayout.CENTER);
            alertPanel.add(viewImagesButton, BorderLayout.WEST);
            alertPanel.add(removeAlertButton, BorderLayout.EAST);

            panel.add(alertPanel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);
    }

    @Override
    public void update() {
        getContentPane().removeAll();
        initComponents();
        revalidate();
        repaint();
    }

}
