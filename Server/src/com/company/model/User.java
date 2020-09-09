package com.company.model;

import java.net.Socket;
import java.util.Objects;

public class User {
    private String name;
    private Socket socket;

    public User(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                socket.equals(user.socket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, socket);
    }
}
