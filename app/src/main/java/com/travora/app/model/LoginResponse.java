package com.travora.app.model;

public class LoginResponse{

    private boolean response;

    public LoginResponse(boolean response){
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

}
