package com.rongxinkj.alexmessage.model;

public class VideoMessage extends BaseMessage {
    private String mediaId;
    private String title;
    private String description;

    public VideoMessage() {
        this.setMsgType("video");
    }

    public VideoMessage(String toUserName, String fromUserName, String createTime, String mediaId, String title, String description) {
        super(toUserName, fromUserName, createTime);
        this.mediaId = mediaId;
        this.title = title;
        this.description = description;
        this.setMsgType("video");
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
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

    @Override
    public String toString() {
        return "VideoMessage{" +
                "mediaId='" + mediaId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
