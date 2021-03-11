package com.lyhoangvinh.app.ui.news;

import com.lyhoangvinh.app.model.Newspaper;

import java.util.List;

public interface NewsView {
    void onSuccess(List<Newspaper> data);
}
