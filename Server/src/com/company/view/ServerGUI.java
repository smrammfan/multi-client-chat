package com.company.view;

import com.company.ServerAndViewConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class ServerGUI implements ServerView {

    private final ServerAndViewConnector serverAndViewConnector;
    private final JFrame mainFrame;
    private JTextArea logsArea;

    public ServerGUI (ServerAndViewConnector serverAndViewConnector){
        this.serverAndViewConnector = serverAndViewConnector;
        serverAndViewConnector.setGui(this);
        mainFrame = createMainFrame();
    }

    public void showFrame(){
        mainFrame.setVisible(true);
    }

    private JFrame createMainFrame(){
        JFrame mainFrame = getBaseFrame("Char Server", new Dimension(400, 550));
        mainFrame.add(createMainPanel());
        return mainFrame;
    }

    private JFrame getBaseFrame(String title, Dimension size){
        JFrame frame = new JFrame(title);
        frame.setSize(size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private JPanel createMainPanel(){
        JPanel panel = createBasePanel(new FlowLayout(), Color.GRAY);
        panel.add(createAreaForLogs());
        panel.add(createButton(
                new Dimension(200, 40),
                "Delete user",
                l -> serverAndViewConnector.removeUser()));
        panel.add(createButton(
                new Dimension(200, 40),
                "Stop server",
                l -> serverAndViewConnector.stopServer()));
        return panel;
    }

    private JPanel createBasePanel(LayoutManager layout, Color color){
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setLayout(layout);
        return panel;
    }

    private JScrollPane createAreaForLogs(){
        logsArea =  new JTextArea();
        logsArea.setEditable(false);
        JScrollPane areaForLogs = new JScrollPane(logsArea);
        areaForLogs.setPreferredSize(new Dimension(300, 400));
        return areaForLogs;
    }

    private JButton createButton(Dimension size, String text, ActionListener listener){
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.addActionListener(listener);
        return button;
    }

    @Override
    public void writeInLogs(String message){
        logsArea.append(message + "\n");
    }

    @Override
    public String getText(String ask){
        return JOptionPane.showInputDialog(ask);
    }


}
