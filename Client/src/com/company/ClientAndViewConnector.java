package com.company;

import com.company.model.ChatClient;
import com.company.view.ClientView;

public class ClientAndViewConnector {
    private ClientView view;
    private ChatClient client;

    public void setView(ClientView view) {
        this.view = view;
    }

    public void setClient(ChatClient client) {
        this.client = client;
    }

    public void sendMessageToView(String message){
        view.sendMessage(message);
    }

    public void sendMessageToServer(String message){
        client.sendMessage(message);
    }

    public String getText(String ask){
        return view.getText(ask);
    }
}
