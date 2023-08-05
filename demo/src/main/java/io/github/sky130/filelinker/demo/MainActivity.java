package io.github.sky130.filelinker.demo;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import io.github.sky130.filelinker.DataFile;
import io.github.sky130.filelinker.FileLinker;
import io.github.sky130.filelinker.callback.PermissionCallback;
import io.github.sky130.filelinker.demo.adapter.HistoryAdapter;
import io.github.sky130.filelinker.demo.adapter.ListFileAdapter;
import io.github.sky130.filelinker.demo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FileLinker fileLinker;
    private String path = "/"; // FileLinker 根目录默认为 <storage>/Android/data/
    private DataFile dataFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getApplicationContext().getResources().getConfiguration().uiMode != 0x21)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fileLinker = FileLinker.init(this); // 初始化FileLinker
        fileLinker.setPackageName("ml.sky233.zero.music");// Android13起作用
        fileLinker.requestPermission(new PermissionCallback(){

            @Override
            public void onSuccess(Uri data) {
                ArrayList<DataFile> dataFileList = fileLinker.getFileList();// Android13必须指定包名
                loadFileList(dataFileList);
            }

            @Override
            public void onFailure() {

            }

        });
        binding.recyclerFile.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerHistory.setLayoutManager(new LinearLayoutManager(this, HORIZONTAL, false));
        binding.buttonReturn.setOnClickListener((v)->{
            if (dataFile == null) return;
            path = dataFile.getLinkPath();
            dataFile = dataFile.getParentFile();
            loadFileList(dataFile.listFiles());
        });
    }

    public void loadFileList(ArrayList<DataFile> dataFileList) {
        ListFileAdapter fileAdapter = new ListFileAdapter(this, dataFileList);
        fileAdapter.setOnFileClickListen((dataFile) -> {
            // TODO 文件打开方式
        });
        fileAdapter.setOnDirectoryClickListen((dataFile) -> {
            this.dataFile = dataFile;
            path = dataFile.getLinkPath();
            loadFileList(dataFile.listFiles());
        });
        loadHistory();
        binding.recyclerFile.setAdapter(fileAdapter);
    }

    public void loadHistory() {
        HistoryAdapter historyAdapter = new HistoryAdapter(this, path);
        historyAdapter.setOnOnClickListener((list, position) -> {
            // TODO 返回之前的路径
        });
        binding.recyclerHistory.setAdapter(historyAdapter);
    }
}