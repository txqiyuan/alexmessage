package com.rongxinkj.alexmessage.model;

import java.util.List;

public class NewsMessage extends BaseMessage {
    private String articlCount;
    private List<NewsMessageArticle> newsMessageArticles;

    public NewsMessage() {
        this.setMsgType("news");
    }

    public NewsMessage(String toUserName, String fromUserName, String createTime, String articlCount, List<NewsMessageArticle> newsMessageArticles) {
        super(toUserName, fromUserName, createTime);
        this.articlCount = articlCount;
        this.newsMessageArticles = newsMessageArticles;
        this.setMsgType("news");
    }

    public String getArticlCount() {
        return articlCount;
    }

    public void setArticlCount(String articlCount) {
        this.articlCount = articlCount;
    }

    public List<NewsMessageArticle> getArticles() {
        return newsMessageArticles;
    }

    public void setArticles(List<NewsMessageArticle> newsMessageArticles) {
        this.newsMessageArticles = newsMessageArticles;
    }

    @Override
    public String toString() {
        return "NewsMessage{" +
                "articlCount='" + articlCount + '\'' +
                ", articles=" + newsMessageArticles +
                "} " + super.toString();
    }
}
