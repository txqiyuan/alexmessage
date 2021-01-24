package com.rongxinkj.alexmessage.model;

public class ImageMessage extends BaseMessage {
    private String mediaId;

    public ImageMessage() {
    }

    public ImageMessage(String toUserName, String fromUserName, String createTime, String mediaId) {
        super(toUserName, fromUserName, createTime);
        this.mediaId = mediaId;
        this.setMsgType("image");
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toString() {
        return "ImageMessage{" +
                "mediaId='" + mediaId + '\'' +
                "} " + super.toString();
    }
}
