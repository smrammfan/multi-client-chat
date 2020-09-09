package com.company.model;

public abstract class PersonThread extends Thread {
    public abstract void exit();
    public abstract void sendMessage(String message);
    public abstract User getUser();


}
