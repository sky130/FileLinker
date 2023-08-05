package io.github.sky130.filelinker;

import android.content.Context;
import android.util.Log;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import io.github.sky130.filelinker.util.FileUtils;

public class DataFile {

    private String path;
    private DocumentFile mDocumentFile;
    private Context context;

    protected void setContext(Context context) {
        this.context = context;
    }

    protected void setPath(String path) {
        this.path = path;
    }

    public String getLinkPath() {
        return path;
    }

    public String getPath() {
        return mDocumentFile.getUri().getPath();
    }

    public String getName() {
        return new File(path).getName();
    }


    protected void setDocumentFile(DocumentFile df) {
        this.mDocumentFile = df;
    }

    public DataFile getParentFile() {
        DataFile dataFile = new DataFile();
        if (path.equals("/")) throw new UnsupportedOperationException("It is root path.");
        dataFile.setDocumentFile(mDocumentFile.getParentFile());
        dataFile.setPath(String.join("/", FileUtils.pathReturn(path)));
        dataFile.setContext(context);
        return dataFile;
    }

    public boolean exists() {
        return mDocumentFile.exists();
    }

    public boolean isFile() {
        return mDocumentFile.isFile();
    }

    public boolean isDirectory() {
        return mDocumentFile.isDirectory();
    }

    public boolean canRead() {
        return mDocumentFile.canRead();
    }

    public boolean canWrite() {
        return mDocumentFile.canWrite();
    }

    public ArrayList<DataFile> listFiles() {
        if (isFile()) {
            throw new UnsupportedOperationException("It is not a directory.");
        }
        ArrayList<DataFile> list = new ArrayList<>();
        for (DocumentFile df : mDocumentFile.listFiles()) {
            DataFile dataFile = new DataFile();
            Log.d("test_data,", df.getName());
            dataFile.setPath(path + "/" + df.getName());
            dataFile.setDocumentFile(df);
            dataFile.setContext(context);
            list.add(dataFile);
        }
        return list;
    }

    public OutputStream getOutputStream() throws FileNotFoundException {
        if (isDirectory()) {
            throw new UnsupportedOperationException("It is not a file.");
        }
        return context.getContentResolver().openOutputStream(mDocumentFile.getUri());
    }

    public InputStream getInputStream() throws FileNotFoundException {
        if (isDirectory()) {
            throw new UnsupportedOperationException("It is not a file.");
        }
        return context.getContentResolver().openInputStream(mDocumentFile.getUri());
    }


}
