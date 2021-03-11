package com.lyhoangvinh.app;

public class Newspaper {
    private String imageUrl;
    private String title;
    private String link;
    private String content;

    public Newspaper() {}

    public Newspaper(String imageUrl, String title, String link, String content) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.link = link;
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
