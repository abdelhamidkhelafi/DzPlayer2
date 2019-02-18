package fr.univavignon.dzplayer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import  android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Songs extends Fragment {
    MediaPlayer mediaPlayer ;
    ImageButton playImage;
    ImageButton stopImage;
    ImageButton microImage;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<Song> songsArray = new ArrayList<>();
    boolean isRecording=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.songs,container,false);
        // GETTING THE ARRAY LIST FROM SERVER //
        songsArray.add(new Song("Tata ","Soolking","url"));
        songsArray.add(new Song("Dalida ","Soolking","url"));
        songsArray.add(new Song("Aicha ","Khelad","url"));
        songsArray.add(new Song("Deux fois","mami","url"));
        songsArray.add(new Song("Lundi ","Fianso","url"));
        // ................................... //
        for(Song song :songsArray){
            data.add("♬ "+song.getSinger()+" "+song.getName());
        }


        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                data
        );
        ListView lv;
        lv =(ListView) view.findViewById(R.id.songsListView);
        lv.setAdapter(adapter);

        playImage = (ImageButton) view.findViewById(R.id.playSongView);
        playImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer == null){


                }
                mediaPlayer.start();
            }
        });

        stopImage = (ImageButton) view.findViewById(R.id.stopSongView);
        stopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                mediaPlayer.pause();
            }
        });

        microImage = (ImageButton) view.findViewById(R.id.microView);
        microImage.setImageResource(R.drawable.ic_mic_black_24dp);
        microImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRecording){

                    microImage.setImageResource(R.drawable.ic_mic_black_24dp);
                    isRecording=false;

                }else{
                    isRecording=true;
                    microImage.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                }

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),songsArray.get(position).getName(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
