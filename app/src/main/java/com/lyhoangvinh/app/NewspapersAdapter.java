package com.lyhoangvinh.app;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class NewspapersAdapter extends RecyclerView.Adapter<NewspapersAdapter.NewspapersViewHolder> {

    private final List<Newspaper> data = new ArrayList<>();

    private OnClickNewsListener onClickNewsListener;

    public NewspapersAdapter(OnClickNewsListener onClickNewsListener) {
        this.onClickNewsListener = onClickNewsListener;
    }

    @NonNull
    @Override
    public NewspapersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewspapersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false), onClickNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewspapersViewHolder holder, int position) {
        if (!data.isEmpty()) {
            holder.initData(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void submitList(List<Newspaper> newList) {
        data.clear();
        data.addAll(newList);
        notifyDataSetChanged();
    }

    static class NewspapersViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvContent;
        ImageView imv;

        private Newspaper news;

        public void initData(Newspaper news) {
            this.news = news;
            tvTitle.setText(news.getTitle());
            tvContent.setText(news.getContent());
            if (TextUtils.isEmpty(news.getImageUrl())) {
                imv.setImageDrawable(ContextCompat.getDrawable(App.getInstance(), R.drawable.no_image));
            } else {
                Picasso.get().load(news.getImageUrl()).into(imv);
            }
        }

        public NewspapersViewHolder(@NonNull View itemView, OnClickNewsListener onClickNewsListener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            imv = itemView.findViewById(R.id.imv);
            itemView.setOnClickListener(view -> {
                if (onClickNewsListener != null && news != null) {
                    onClickNewsListener.invoke(news);
                }
            });
        }
    }
}
