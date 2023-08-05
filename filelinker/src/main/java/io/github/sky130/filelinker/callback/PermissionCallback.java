package io.github.sky130.filelinker.callback;

import android.net.Uri;

public interface PermissionCallback {

    void onSuccess(Uri data);

    void onFailure();

}
