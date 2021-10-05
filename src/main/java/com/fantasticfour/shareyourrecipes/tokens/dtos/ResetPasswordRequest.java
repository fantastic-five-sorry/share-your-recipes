package com.lvl162.learningspringboot.tokens.dtos;

public class ResetPasswordRequest {
    private String token;
    private String newPassword;

    public ResetPasswordRequest() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
