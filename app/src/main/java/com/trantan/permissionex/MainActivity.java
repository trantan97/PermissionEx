package com.trantan.permissionex;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String IMAGE_DIRECTORY_PATH = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
    private static final int READ_EXTERNAL_STORAGE_REQUEST = 1;
    private static final String JPG_EXTENSION = ".jpg";
    private RecyclerView mRecyclerView;
    private ArrayList<String> mImagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermission();
    }

    public static Intent getProfileIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private void loadData(ArrayList<String> imagePaths) {
        mImagePaths = imagePaths;
        RecyclerAdapter adapter = new RecyclerAdapter(mImagePaths);
        mRecyclerView.setAdapter(adapter);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            new GetImagePath().execute(IMAGE_DIRECTORY_PATH);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            new GetImagePath().execute(IMAGE_DIRECTORY_PATH);
        } else {
            Toast.makeText(this, getString(R.string.permission_deny),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<String> getImagePaths(String path) {
        ArrayList<String> imagePaths = new ArrayList<>();
        File imageDirectory = new File(path);
        File[] images = imageDirectory.listFiles();
        for (File image : images) {
            if (image.getName().toLowerCase().endsWith(JPG_EXTENSION)) {
                imagePaths.add(image.getAbsolutePath());
            }
            if (image.isDirectory()) {
                imagePaths.addAll(getImagePaths(image.getAbsolutePath()));
            }
        }
        return imagePaths;
    }

    class GetImagePath extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            return getImagePaths(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<String> imagePaths) {
            loadData(imagePaths);
        }
    }
}
