package com.rongxinkj.alexmessage.model;

public class MusicMessageMusic {
    private String title;
    private String description;
    private String musicURL;
    private String hqMusicURL;
    private String thumbMediaId;

    public MusicMessageMusic() {
    }

    public MusicMessageMusic(String title, String description, String musicURL, String hqMusicURL, String thumbMediaId) {
        this.title = title;
        this.description = description;
        this.musicURL = musicURL;
        this.hqMusicURL = hqMusicURL;
        this.thumbMediaId = thumbMediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMusicURL() {
        return musicURL;
    }

    public void setMusicURL(String musicURL) {
        this.musicURL = musicURL;
    }

    public String getHqMusicURL() {
        return hqMusicURL;
    }

    public void setHqMusicURL(String hqMusicURL) {
        this.hqMusicURL = hqMusicURL;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    @Override
    public String toString() {
        return "Music{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", musicURL='" + musicURL + '\'' +
                ", hqMusicURL='" + hqMusicURL + '\'' +
                ", thumbMediaId='" + thumbMediaId + '\'' +
                '}';
    }
}
