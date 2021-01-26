package com.rongxinkj.alexmessage.model;

public class NewsMessageArticle {
    private String title;
    private String description;
    private String picURL;
    private String url;

    public NewsMessageArticle() {
    }

    public NewsMessageArticle(String title, String description, String picURL, String url) {
        this.title = title;
        this.description = description;
        this.picURL = picURL;
        this.url = url;
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

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picURL='" + picURL + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
