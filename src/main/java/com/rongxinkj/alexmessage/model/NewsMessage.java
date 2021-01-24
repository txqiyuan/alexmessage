package com.rongxinkj.alexmessage.model;

import java.util.List;

public class NewsMessage extends BaseMessage {
    private String articlCount;
    private List<Article> articles;

    public NewsMessage() {
        this.setMsgType("news");
    }

    public NewsMessage(String toUserName, String fromUserName, String createTime, String articlCount, List<Article> articles) {
        super(toUserName, fromUserName, createTime);
        this.articlCount = articlCount;
        this.articles = articles;
        this.setMsgType("news");
    }

    public String getArticlCount() {
        return articlCount;
    }

    public void setArticlCount(String articlCount) {
        this.articlCount = articlCount;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "NewsMessage{" +
                "articlCount='" + articlCount + '\'' +
                ", articles=" + articles +
                "} " + super.toString();
    }
}
