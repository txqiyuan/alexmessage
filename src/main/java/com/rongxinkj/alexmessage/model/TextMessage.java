package com.rongxinkj.alexmessage.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文本消息类继承基本消息类
 */
@XStreamAlias("xml")
public class TextMessage extends BaseMessage {
    @XStreamAlias("Content")
    private String content;

    public TextMessage() {
        this.setMsgType("text"); //文本消息类的默认消息类型是text
    }

    public TextMessage(String toUserName, String fromUserName, String createTime, String content) {
        super(toUserName, fromUserName, createTime);
        this.content = content;
        this.setMsgType("text");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "content='" + content + '\'' +
                "} " + super.toString();
    }
}
