package com.fantasticfour.shareyourrecipes.user.dtos;

public class ChangePasswordDto {
    private String oldPassword;

    private String newPassword;

    private String confirmNewPassword;

    public String getConfirmNewPassword() {
        return this.confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    // public String getToken() {
    // return token;
    // }

    // public void setToken(String token) {
    // this.token = token;
    // }

}
