package com.lyhoangvinh.app.model;

import java.io.Serializable;

public class Newspaper implements Serializable {
    private final String imageUrl;
    private final String title;
    private final String link;
    private final String content;

    public Newspaper(String imageUrl, String title, String link, String content) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.link = link;
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getContent() {
        return content;
    }

}
