package com.lyhoangvinh.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class NewsFragment extends Fragment implements NewsView {

    private RecyclerView recyclerView;

    private NewsPresenter presenter;

    private NewspapersAdapter adapter;

    private Disposable disposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        presenter = new NewsPresenter(this);
        adapter = new NewspapersAdapter(new OnClickNewsListener() {
            @Override
            public void invoke(Newspaper news) {

            }
        });
        recyclerView.setAdapter(adapter);
        disposable = presenter.loadNews(getActivity());
        return view;
    }

    @Override
    public void onSuccess(List<Newspaper> data) {
        adapter.submitList(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
