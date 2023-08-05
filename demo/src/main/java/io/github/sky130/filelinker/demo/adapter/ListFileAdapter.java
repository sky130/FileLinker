package io.github.sky130.filelinker.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.sky130.filelinker.DataFile;
import io.github.sky130.filelinker.demo.R;

public class ListFileAdapter extends RecyclerView.Adapter {
    private ArrayList<DataFile> list;
    private Context context;
    private FileClickListener fileClickListen;
    private DirectoryClickListener directoryClickListen;


    public ListFileAdapter(Context context, ArrayList<DataFile> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_file, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        DataFile dataFile = list.get(position);
        if (dataFile.isFile()) {
            itemHolder.iv_img.setImageResource(R.drawable.ic_file);
            itemHolder.itemView.setOnClickListener((v) -> {
                if (fileClickListen != null) {
                    fileClickListen.onClick(dataFile);
                }
            });
        } else {
            itemHolder.iv_img.setImageResource(R.drawable.ic_folder);
            itemHolder.itemView.setOnClickListener((v) -> {
                if (directoryClickListen != null) {
                    directoryClickListen.onClick(dataFile);
                }
            });
        }
        itemHolder.tv_title.setText(dataFile.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnDirectoryClickListen(DirectoryClickListener listen) {
        directoryClickListen = listen;
    }

    public void setOnFileClickListen(FileClickListener listen) {
        fileClickListen = listen;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tv_title; // 声明列表项标题的文本视图
        public ImageView iv_img; // 声明列表项描述的文本视图

        public ItemHolder(View v) {
            super(v);
            tv_title = v.findViewById(R.id.item_text);
            iv_img = v.findViewById(R.id.item_image);
        }
    }


    public interface FileClickListener {
        void onClick(DataFile dataFile);

    }

    public interface DirectoryClickListener {
        void onClick(DataFile dataFile);

    }


}
