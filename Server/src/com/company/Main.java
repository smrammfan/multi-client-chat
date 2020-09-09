package com.company;

import com.company.model.ChatServer;
import com.company.view.ServerGUI;

public class Main {

    public static void main(String[] args) {
        ServerAndViewConnector connector = new ServerAndViewConnector();
	    ServerGUI serverGUI = new ServerGUI(connector);
	    serverGUI.showFrame();
        Thread server = new ChatServer(8880, connector);
        server.start();

    }
}
