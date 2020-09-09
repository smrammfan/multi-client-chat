package com.company.model;

import com.company.ClientAndViewConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends Thread {
    private String hostname;
    private int port;
    private ClientAndViewConnector viewConnector;
    private PrintWriter writer;

    public ChatClient(String hostname, int port, ClientAndViewConnector viewConnector){
        this.hostname = hostname;
        this.port = port;
        this.viewConnector = viewConnector;
        this.viewConnector.setClient(this);
    }


    @Override
    public void run() {
        try(Socket socket = new Socket(hostname, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(viewConnector.getText("Write your name"));

            while (true){
                if (reader.ready()){
                    viewConnector.sendMessageToView(reader.readLine());
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        writer.println(message);
    }

}
