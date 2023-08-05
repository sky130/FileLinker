package io.github.sky130.filelinker.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.sky130.filelinker.callback.PermissionCallback;

public class LiteFragment extends Fragment {
    private PermissionCallback callback;


    public void setCallback(PermissionCallback callback) {
        this.callback = callback;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            this.getActivity().getContentResolver().takePersistableUriPermission(data.getData(), data.getFlags() & (
                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION));//关键是这里，这个就是保存这个目录的访问权限
        }
        if (resultCode == Activity.RESULT_OK) {
            callback.onSuccess(data.getData());
        } else {
            callback.onFailure();
        }

    }


}
