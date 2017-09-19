package com.androidcat.acnet.entity.request;

public class ChangePswRequest extends BaseRequest {

    private String userName=null;
    private String password=null;
    private String nePassword=null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNePassword() {
        return nePassword;
    }

    public void setNePassword(String newPassword) {
        this.nePassword = newPassword;
    }
}