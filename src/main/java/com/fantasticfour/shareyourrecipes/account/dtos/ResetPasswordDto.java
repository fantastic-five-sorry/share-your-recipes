package com.fantasticfour.shareyourrecipes.account.dtos;

import javax.validation.constraints.NotBlank;

public class ResetPasswordDto {
    @NotBlank
    private String confirmNewPassword;

    @NotBlank
    private String token;

    @NotBlank
    private String newPassword;

    public String getConfirmNewPassword() {
        return this.confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
