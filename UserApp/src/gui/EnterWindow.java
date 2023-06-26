package gui;

import business.LogicMain;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterWindow extends JFrame {

    private JPanel contentPane;
    private JButton btnStart;

    private MainWindow mainWindow;

    public EnterWindow()    {
        setTitle("Welcome to the Bingo");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 450);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(getBtnStart());
        this.getRootPane().setDefaultButton(btnStart);
    }

    private JButton getBtnStart() {
        if(btnStart == null)    {
            EnterWindow window = this;
            btnStart = new JButton("Start service");
            btnStart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openMainWindow();
                    LogicMain.run(mainWindow);
                    window.setEnabled(false);
                    window.setVisible(false);
                }
            });
            btnStart.setForeground(Color.WHITE);
            btnStart.setBackground(Color.GREEN);
            btnStart.setFont(new Font("Calibri", Font.BOLD, 18));
            btnStart.setBounds(100, 350, 200, 30);
        }
        return btnStart;
    }

    private void openMainWindow()   {
        mainWindow = MainWindow.getInstance();
        mainWindow.setLocationRelativeTo(this);
        mainWindow.setEnabled(true);
        mainWindow.setVisible(true);
    }
}
