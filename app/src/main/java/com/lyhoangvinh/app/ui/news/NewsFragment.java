package com.lyhoangvinh.app.ui.news;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.lyhoangvinh.app.R;
import com.lyhoangvinh.app.model.Newspaper;
import com.lyhoangvinh.app.ui.detail.DetailFragment;

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
        adapter = new NewspapersAdapter(this::replaceFragment);
        recyclerView.setAdapter(adapter);
        disposable = presenter.loadNews(getActivity());
        return view;
    }

    public void replaceFragment(Newspaper news) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.container, DetailFragment.getInstance(news), null);
            ft.addToBackStack(null).commit();
            fragmentManager.executePendingTransactions();

        }
    }

    @Override
    public void onSuccess(List<Newspaper> data) {
        adapter.submitList(data);
    }

    @Override
    public void onError(String message) {
        if (getActivity() != null)
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
