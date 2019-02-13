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
    ArrayList<String> data = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.songs,container,false);
       // ArrayList<String> data = new ArrayList<>();
        data.add("♬ Soolking tata");
        data.add("♬ Soolking vroom vroom");
        data.add("♬ MAMI ma vie deux fois");
        data.add("♬ Khaled aicha");
        data.add("♬ Fianso Lundi");


        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                data
        );
        ListView lv;
        lv =(ListView) view.findViewById(R.id.songsListView);
        lv.setAdapter(adapter);
        ImageButton playImage = (ImageButton) view.findViewById(R.id.playSongView);
        playImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(getContext(),R.raw.kounti);

                }
                mediaPlayer.start();
            }
        });
        ImageButton stopImage = (ImageButton) view.findViewById(R.id.stopSongView);
        stopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                mediaPlayer.pause();
            }
        });
        ImageButton microImage = (ImageButton) view.findViewById(R.id.microView);
        microImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"micro",Toast.LENGTH_LONG).show();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),data.get(position),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
