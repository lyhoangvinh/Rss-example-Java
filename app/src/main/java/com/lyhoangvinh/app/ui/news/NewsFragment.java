package com.lyhoangvinh.app.ui.news;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.lyhoangvinh.app.R;
import com.lyhoangvinh.app.model.Newspaper;
import com.lyhoangvinh.app.ui.detail.DetailFragment;
import com.lyhoangvinh.app.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class NewsFragment extends Fragment implements NewsView {

    private RecyclerView recyclerView;

    private NewsPresenter presenter;

    private NewspapersAdapter adapter;

    private Disposable disposable;

    private CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        presenter = new NewsPresenter(this);
        adapter = new NewspapersAdapter(this::replaceFragment);
        recyclerView.setAdapter(adapter);
        disposable = presenter.loadNews(getActivity());
        checkBox = view.findViewById(R.id.checkbox);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkBox.setOnClickListener(view1 -> {
            Utils.putStateNighMode(checkBox.isChecked());
            if (checkBox.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else  {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                checkBox.setChecked(false);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                checkBox.setChecked(true);
                break;
        }
        checkBox.setChecked(Utils.getStateNightMode());
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
