package com.leyifu.weiliaodemo.bean;

/**
 * Created by hahaha on 2017/9/5 0005.
 */

public class LogInBean {

    /**
     * success : true
     * username : 121@163.com
     * nickname :
     * message : 登录成功
     * msgCount : 0
     */

    private boolean success;
    private String username;
    private String nickname;
    private String message;
    private int msgCount;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }
}
