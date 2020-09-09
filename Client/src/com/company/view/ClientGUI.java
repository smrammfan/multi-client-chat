package com.company.view;

import com.company.ClientAndViewConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientGUI implements ClientView {
    private JFrame mainFrame;
    private JTextArea messagesArea;
    private JTextField inputMessage;
    private ClientAndViewConnector clientConnector;

    public ClientGUI(ClientAndViewConnector clientConnector) {
        mainFrame = createMainFrame();
        this.clientConnector = clientConnector;
        this.clientConnector.setView(this);
    }

    @Override
    public void show() {
        mainFrame.setVisible(true);
    }


    private JFrame createMainFrame() {
        JFrame mainFrame = getBaseFrame("Char Client", new Dimension(400, 550));
        mainFrame.add(createMainPanel());
        return mainFrame;
    }

    private JFrame getBaseFrame(String title, Dimension size) {
        JFrame frame = new JFrame(title);
        frame.setSize(size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private JPanel createMainPanel() {
        JPanel panel = createBasePanel(new FlowLayout(), Color.GRAY);
        panel.add(createAreaForMessages());
        inputMessage = createTextField(new Dimension(250, 30));
        panel.add(inputMessage);
        panel.add(createButton(new Dimension(100, 30), "Send",
                l -> {
                    clientConnector.sendMessageToServer(inputMessage.getText());
                    inputMessage.setText(" ");
                }));
        return panel;
    }

    private JPanel createBasePanel(LayoutManager layout, Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setLayout(layout);
        return panel;
    }

    private JScrollPane createAreaForMessages() {
        messagesArea = new JTextArea();
        messagesArea.setEditable(false);
        JScrollPane areaForMessage = new JScrollPane(messagesArea);
        areaForMessage.setPreferredSize(new Dimension(350, 400));
        return areaForMessage;
    }

    private JTextField createTextField(Dimension size){
        JTextField field = new JTextField();
        field.setPreferredSize(size);
        return field;
    }

    private JButton createButton(Dimension size, String text, ActionListener listener){
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.addActionListener(listener);
        return button;
    }

    @Override
    public void sendMessage(String message) {
        messagesArea.append(message + "\n");
    }

    @Override
    public String getText(String ask) {
        return JOptionPane.showInputDialog(ask);
    }
}
