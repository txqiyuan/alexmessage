package com.rongxinkj.alexmessage.model;

public class MusicMessage extends BaseMessage {
    private MusicMessageMusic musicMessageMusic;

    public MusicMessage() {
        this.setMsgType("music");
    }

    public MusicMessage(String toUserName, String fromUserName, String createTime, MusicMessageMusic musicMessageMusic) {
        super(toUserName, fromUserName, createTime);
        this.musicMessageMusic = musicMessageMusic;
        this.setMsgType("music");
    }

    public MusicMessageMusic getMusic() {
        return musicMessageMusic;
    }

    public void setMusic(MusicMessageMusic musicMessageMusic) {
        this.musicMessageMusic = musicMessageMusic;
    }

    @Override
    public String toString() {
        return "MusicMessage{" +
                "music=" + musicMessageMusic +
                "} " + super.toString();
    }
}
