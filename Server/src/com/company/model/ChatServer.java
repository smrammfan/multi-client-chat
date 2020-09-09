package com.company.model;


import com.company.ServerAndViewConnector;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ChatServer extends Thread{
    private final List<PersonThread> userThreads;
    private final int port;
    private final ServerAndViewConnector serverAndViewConnector;
    private boolean isWorking;

    public ChatServer(int port, ServerAndViewConnector connector) {
        userThreads = new LinkedList<>();
        this.port = port;
        this.serverAndViewConnector = connector;
        serverAndViewConnector.setServer(this);
        isWorking = true;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            serverAndViewConnector.writeInLogs("Server started on port " + port);
            while(isWorking) {
                Socket userSocket = serverSocket.accept();
                User user = new User("", userSocket);
                UserThread userThread = new UserThread(user, this);
                userThreads.add(userThread);
                userThread.start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void exit(){
        isWorking = false;
        userThreads.forEach(PersonThread::exit);
        sendMessage("Server was stopped");
        sendAll("Server was stopped");
        super.interrupt();

    }

    public void sendAll(String message) {
        for (PersonThread user : userThreads) {
                user.sendMessage(message);
        }
    }

    public void removeUser(PersonThread userThread) {
        userThreads.remove(userThread);
        userThread.exit();
    }

    public boolean hasUsers(){
        return userThreads.size() != 0;
    }

    public void sendMessage(String message) {
        serverAndViewConnector.writeInLogs(message);
    }

    public String getUserNames(){
        return userThreads
                .stream()
                .map(userThread -> userThread.getUser().getName())
                .reduce((name, accum) -> name + ", " + accum).orElse("null");
    }

    public void removeUser(String name){
        Optional<PersonThread> userThread = userThreads
                .stream()
                .filter(u -> u.getUser().getName().equals(name))
                .findAny();
        if(userThread.isPresent()){
            userThread.get().sendMessage("[Server]: You will remove");
            removeUser(userThread.get());
            serverAndViewConnector.writeInLogs("User \"" + name + "\" was removed");
            sendAll("User \"" + name + "\" was removed");
        }else {
            sendMessage("No users with name \"" + name + "\"");
        }
    }

    public void printAllUsers() {
        if (hasUsers()) {
            sendMessage("Connected users: " + getUserNames());
        } else {
            sendMessage("No other users connected");
        }
    }

}
