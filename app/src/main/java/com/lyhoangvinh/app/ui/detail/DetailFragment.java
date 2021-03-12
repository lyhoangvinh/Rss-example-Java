package com.lyhoangvinh.app.ui.detail;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.lyhoangvinh.app.R;
import com.lyhoangvinh.app.model.Newspaper;
import com.lyhoangvinh.app.utils.Utils;

public class DetailFragment extends Fragment {

    public static DetailFragment getInstance(Newspaper news) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("NEWS", news);
        fragment.setArguments(bundle);
        return fragment;
    }

    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        webView = view.findViewById(R.id.webView);
        return view;
    }

    @SuppressLint({"RequiresFeature", "SetJavaScriptEnabled"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            Newspaper newspaper = (Newspaper) arguments.getSerializable("NEWS");
            webView.setWebViewClient(new WebViewer());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(newspaper.getLink());
        }
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        checkBox.setChecked(Utils.getStateNightMode());
        if (Utils.getStateNightMode()) {
            WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
        } else  {
            WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
        }

        checkBox.setOnClickListener(view1 -> {
            Utils.putStateNighMode(checkBox.isChecked());
            if (checkBox.isChecked()) {
                WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
            } else {
                WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
            }
        });
    }

    private static class WebViewer extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.popBackStack();
        }
    }
}
