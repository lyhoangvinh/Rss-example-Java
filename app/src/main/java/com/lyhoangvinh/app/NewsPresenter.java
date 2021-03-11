package com.lyhoangvinh.app;

import android.content.Context;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewsPresenter {
    private NewsView view;

    public NewsPresenter(NewsView view) {
        this.view = view;
    }

    public Disposable loadNews(Context context) {
       return new NewsRepo().getData(context)
                .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(newspapers -> {
                    if (view != null) {
                        view.onSuccess(newspapers);
                    }
                })
                .subscribe();
    }

}
