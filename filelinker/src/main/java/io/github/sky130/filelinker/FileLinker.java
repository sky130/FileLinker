package io.github.sky130.filelinker;

import android.annotation.SuppressLint;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.sky130.filelinker.callback.PermissionCallback;
import io.github.sky130.filelinker.fragment.LiteFragment;
import io.github.sky130.filelinker.util.FileUtils;

public class FileLinker {
    @SuppressLint("StaticFieldLeak")
    private AppCompatActivity activity;
    private LiteFragment mFragment;
    private String mPackageName = "";


    private FileLinker() {
    }

    @SuppressLint("CommitTransaction")
    public static FileLinker init(AppCompatActivity activity) {
        FileLinker fileLinker = new FileLinker();
        fileLinker.activity = activity;
        fileLinker.mFragment = new LiteFragment();
        FragmentTransaction transaction = fileLinker.activity.getSupportFragmentManager().beginTransaction();
        transaction.add(fileLinker.mFragment, "LiteFragmentTag"); // 为片段事务提供一个唯一的标记
        transaction.attach(fileLinker.mFragment);
        transaction.commit(); // 提交片段事务
        fileLinker.activity.getSupportFragmentManager().executePendingTransactions();
        return fileLinker;
    }

    public void setPackageName(String pn) {
        this.mPackageName = pn;
    }

    public boolean isGetPermission() {
        List<UriPermission> list = activity.getContentResolver().getPersistedUriPermissions();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //Android13的解决方案
            if (mPackageName.isEmpty())
                throw new UnsupportedOperationException("PackageName not set");
            for (UriPermission i : list) {
                if (i.getUri().toString().indexOf(mPackageName) != 0) {
                    return true;
                }
            }
            return false;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android11~12的解决方案
            for (UriPermission i : list) {
                if (i.getUri().toString().indexOf("Android/data") != 0) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private Uri getFileUri(String packageName) {
        List<UriPermission> list = activity.getContentResolver().getPersistedUriPermissions();
        if (packageName.isEmpty()) throw new UnsupportedOperationException("PackageName not set");
        for (UriPermission i : list) {
            if (i.getUri().toString().indexOf(mPackageName) != 0) {
                return i.getUri();
            }
        }
        return null;
    }

    private Uri getFileUri() {
        List<UriPermission> list = activity.getContentResolver().getPersistedUriPermissions();
        for (UriPermission i : list) {
            if (i.getUri().toString().indexOf("Android/data") != 0) {
                return i.getUri();
            }
        }
        return null;
    }

    public void requestPermission(PermissionCallback callback) {
        mFragment.setCallback(callback);
        int REQUEST_CODE = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //Android13的解决方案
            if (mPackageName.isEmpty())
                throw new UnsupportedOperationException("PackageName not set");
            FileUtils.startFor(mFragment, mPackageName, REQUEST_CODE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android11~12的解决方案
            FileUtils.startFor(mFragment, "", REQUEST_CODE);
        }
    }

    public ArrayList<DataFile> getFileList() {
        ArrayList<DataFile> list = new ArrayList<>();
        DocumentFile dfs = null;
        String path = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //Android13的解决方案
            if (mPackageName.isEmpty())
                throw new UnsupportedOperationException("PackageName not set");
            dfs = DocumentFile.fromTreeUri(activity, getFileUri(mPackageName));

            path = "/" + mPackageName;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android11~12的解决方案
            dfs = DocumentFile.fromTreeUri(activity, getFileUri());
            path = "";
        }

        if (dfs != null) {
            for (DocumentFile df : dfs.listFiles()) {
                DataFile dataFile = new DataFile();
                dataFile.setContext(activity);
                dataFile.setPath(path + "/" + df.getName());
                dataFile.setDocumentFile(df);
                list.add(dataFile);
            }
        }
        return list;
    }

}
