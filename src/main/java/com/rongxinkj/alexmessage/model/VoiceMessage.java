package com.rongxinkj.alexmessage.model;

public class VoiceMessage extends BaseMessage{
    private String mediaId;

    public VoiceMessage() {
        this.setMsgType("voice");
    }

    public VoiceMessage(String toUserName, String fromUserName, String createTime, String mediaId) {
        super(toUserName, fromUserName, createTime);
        this.mediaId = mediaId;
        this.setMsgType("voice");
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toString() {
        return "VoiceMessage{" +
                "mediaId='" + mediaId + '\'' +
                "} " + super.toString();
    }
}
