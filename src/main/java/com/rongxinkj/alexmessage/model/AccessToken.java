package com.rongxinkj.alexmessage.model;

public class AccessToken {
    private String token;
    private long expiresTime;

    //单例模式
    private static AccessToken accessToken;
    public static AccessToken getInstance() {
        if (accessToken == null) {
            accessToken = new AccessToken();
        }
        return accessToken;
    }

    private AccessToken() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(String expiresIn) {
        this.expiresTime = System.currentTimeMillis() + Long.parseLong(expiresIn) * 1000;
    }

    /**
     * 判断是token是否过期
     * @return
     */
    public boolean isExpired() {
        //这里为了保证下一个token和当前token的衔接，把当前token的实际过期时间提前5秒
        return System.currentTimeMillis() + 5000 > expiresTime;
    }
}
