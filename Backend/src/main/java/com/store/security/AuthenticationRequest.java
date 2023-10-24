package com.store.security;

import org.springframework.lang.NonNull;

public class AuthenticationRequest {

    @NonNull
    private String name;

    @NonNull
    private String password;

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "login='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

