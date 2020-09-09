package com.company.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Objects;

public class UserThread extends PersonThread{
    private final User user;
    private final ChatServer server;
    private PrintWriter writer;
    private boolean isWorking;

    public UserThread(User user, ChatServer server){
        this.server = server;
        this.user = user;
        isWorking = true;
    }

    @Override
    public void exit(){
        isWorking = false;
        super.interrupt();
        try {
            user.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(user.getSocket().getInputStream()))){
            writer = new PrintWriter(user.getSocket().getOutputStream(), true);
            user.setName(reader.readLine());
            String message = "New user connected: " + user.getName();
            server.sendMessage(message);
            server.printAllUsers();
            server.sendAll(message);

            String clientMessage;
            while (isWorking) {
                clientMessage = reader.readLine();
                if(Objects.isNull(clientMessage))
                    break;
                message = "[" + user.getName() + "]: " + clientMessage;
                server.sendAll(message);
            }

            server.removeUser(this);
            user.getSocket().close();

            message = user.getName() + " has quited.";
            server.sendAll(message);
            server.sendMessage(message);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }

    @Override
    public void sendMessage(String message){
        writer.println(message);
    }

    @Override
    public User getUser(){
        return user;
    }



}
