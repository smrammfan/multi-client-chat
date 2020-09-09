package com.company;

import com.company.model.ChatClient;
import com.company.view.ClientGUI;
import com.company.view.ClientView;

public class Main {

    public static void main(String[] args) {
        ClientAndViewConnector connector = new ClientAndViewConnector();
        ClientView view = new ClientGUI(connector);
        ChatClient client = new ChatClient("localhost", 8880, connector);
        view.show();
        client.start();
    }
}
