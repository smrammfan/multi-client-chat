package com.company;

import com.company.model.ChatServer;
import com.company.view.ServerView;

public class ServerAndViewConnector {
    private ChatServer server;
    private ServerView serverView;

    public void setServer(ChatServer server) {
        this.server = server;
    }

    public void setGui(ServerView serverView) {
        this.serverView = serverView;
    }

    public void removeUser(){
        server.removeUser(serverView.getText("Write name of user you want to remove"));
    }

    public void writeInLogs(String message){
        serverView.writeInLogs(message);
    }

    public void stopServer(){
        server.exit();
    }
}
