package io.github.sky130.filelinker.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import io.github.sky130.filelinker.demo.R;

public class HistoryAdapter extends RecyclerView.Adapter {
    private final ArrayList<String> list;
    private final Context context;
    private OnClickListener onClickListener;


    public HistoryAdapter(Context context, String path) {
        this.list = new ArrayList<>(Arrays.asList(path.split("/")));
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_histoty, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        String name = list.get(position);
        if (position ==0){
            itemHolder.tv_title.setText("根目录");
        }else{
            itemHolder.tv_title.setText(name);
        }
        itemHolder.itemView.setOnClickListener((v) -> {
            if (onClickListener != null) {
                onClickListener.onClick(list,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnOnClickListener(OnClickListener listen) {
        onClickListener = listen;
    }


    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tv_title; // 声明列表项标题的文本视图

        public ItemHolder(View v) {
            super(v);
            tv_title = v.findViewById(R.id.item_text);
        }
    }


    public interface OnClickListener {
        void onClick( ArrayList<String> list,int position);

    }


}
