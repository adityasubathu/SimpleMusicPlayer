package com.adityasubathu.simplemusicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int READ_STORAGE_PERMISSION_REQUEST_FLAG = 1;
    List<String> songsList = new ArrayList<>();
    ListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_FLAG);
        } else {
            prepareAdapter();
        }
    }

    void getMusicInfo() {
        ContentResolver resolver = getContentResolver();
        Uri songsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songsCursor = resolver.query(songsUri, null, null, null, MediaStore.Audio.Media.TITLE);

        if(songsCursor != null && songsCursor.moveToFirst()) {
            int songTitleIndex = songsCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtistIndex = songsCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {
                String title = songsCursor.getString(songTitleIndex);
                String artist = songsCursor.getString(songArtistIndex);
                songsList.add(title + "\n" + artist);
            }while (songsCursor.moveToNext());
        }
        Objects.requireNonNull(songsCursor).close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case READ_STORAGE_PERMISSION_REQUEST_FLAG : {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prepareAdapter();
                }
            }

        }
    }

    void prepareAdapter(){
        listView = findViewById(R.id.listView);
        getMusicInfo();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songsList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Snackbar snackbar = Snackbar.make(view, songsList.get(position), Snackbar.LENGTH_SHORT);
                snackbar.show();

            }
        });
    }
}

class songsListAdapter extends BaseAdapter{

    public songsListAdapter() {
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
