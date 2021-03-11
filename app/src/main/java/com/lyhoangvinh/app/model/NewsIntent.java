package com.lyhoangvinh.app.model;

import java.util.List;

public class NewsIntent {
    private List<Newspaper> data;
    private State state;
    private String message;

    public NewsIntent(List<Newspaper> data, State state) {
        this.data = data;
        this.state = state;
    }

    public NewsIntent(State state, String message) {
        this.state = state;
        this.message = message;
    }

    public List<Newspaper> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public State getState() {
        return state;
    }


}
