package io.github.sky130.filelinker.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class FileUtils {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startFor(Fragment fragment, String packageName, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                        Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION |
                        Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)
                .putExtra("android.provider.extra.SHOW_ADVANCED", true)
                .putExtra("android.content.extra.SHOW_ADVANCED", true);
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, buildUri2(packageName));
        fragment.startActivityForResult(intent, requestCode);
    }

    public static Uri buildUri(String packageName) {
        return DocumentsContract.buildTreeDocumentUri(
                "com.android.externalstorage.documents", "primary:Android/data" + packageName
        );
    }

    public static Uri buildUri2(String packageName) {
        return DocumentsContract.buildDocumentUri(
                "com.android.externalstorage.documents", "primary:Android/data/" + packageName
        );
    }

    public static String uriToPath(Uri uri) {
        return uri.getPath();
    }

    public static String pathReturn(String path) {
        if (!path.startsWith("/"))
            throw new UnsupportedOperationException("It is not a standard path");
        ArrayList<String> listPath = new ArrayList<>(Arrays.asList(path.split("/")));
        if (listPath.size() == 1) {
            return path;
        }
        listPath.remove(listPath.size() - 1);
        return String.join("/", listPath);
    }

}
