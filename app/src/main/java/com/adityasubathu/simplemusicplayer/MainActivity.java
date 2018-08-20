package com.adityasubathu.simplemusicplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int READ_STORAGE_PERMISSION_REQUEST_FLAG = 1;
    List<String> songsList = new ArrayList<>(), artistList = new ArrayList<>();
    ListView listView;
    songsListAdapter adapter;

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
                songsList.add(title);
                artistList.add(artist);
            } while (songsCursor.moveToNext());
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
        adapter = new songsListAdapter(this, songsList, artistList);

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

    private List<String> songsList, artistList;
    private Context context;

    songsListAdapter(Context c, List<String> songs, List<String> artist) {
        context = c;
        songsList = songs;
        artistList = artist;
    }

    @Override
    public int getCount() {
        return songsList.size();
    }

    @Override
    public Object getItem(int position) {
        return songsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = Objects.requireNonNull(inflater).inflate(R.layout.songs_list_adapter_layout, parent, false);

        TextView index = convertView.findViewById(R.id.index);
        TextView song = convertView.findViewById(R.id.song);
        TextView artist = convertView.findViewById(R.id.artist);

        index.setText(Integer.toString(position+1));
        song.setText(songsList.get(position));
        artist.setText(artistList.get(position));

        return convertView;
    }
}
