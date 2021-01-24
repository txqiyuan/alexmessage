package com.rongxinkj.alexmessage.model;

public class MusicMessage extends BaseMessage {
    private Music music;

    public MusicMessage() {
        this.setMsgType("music");
    }

    public MusicMessage(String toUserName, String fromUserName, String createTime, Music music) {
        super(toUserName, fromUserName, createTime);
        this.music = music;
        this.setMsgType("music");
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    @Override
    public String toString() {
        return "MusicMessage{" +
                "music=" + music +
                "} " + super.toString();
    }
}
