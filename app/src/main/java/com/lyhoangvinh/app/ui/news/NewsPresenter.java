package com.lyhoangvinh.app.ui.news;

import android.content.Context;

import com.lyhoangvinh.app.model.State;
import com.lyhoangvinh.app.repo.NewsRepo;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewsPresenter {
    private final NewsView view;

    public NewsPresenter(NewsView view) {
        this.view = view;
    }

    public Disposable loadNews(Context context) {
       return new NewsRepo().getData(context)
                .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(intent -> {
                    if (view != null) {
                        if (intent.getState() == State.SUCCESS) {
                            view.onSuccess(intent.getData());
                        } else {
                            view.onError(intent.getMessage());
                        }
                    }
                })
                .subscribe();
    }

}
