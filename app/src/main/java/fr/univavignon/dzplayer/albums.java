package fr.univavignon.dzplayer;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import  android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.media.MediaRecorder;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.os.Environment;

import java.io.IOException;
import java.util.ArrayList;

import android.widget.Spinner;
import android.widget.Toast;




public class albums extends Fragment {
    public ListView lv;
    ImageButton playImage;
    ImageButton stopImage;
    ImageButton microImage;
    MediaRecorder myAudioRecorder = new MediaRecorder();
    String outputFile;

    boolean isRecording=false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albums,container,false);

        /* ************************************ recorder ************************/
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "AudioRecording.3gp";
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.DEFAULT);
        myAudioRecorder.setOutputFile(outputFile);

        /* ************************************ Spinner ************************/
        Spinner albumsSpinner = (Spinner) view.findViewById(R.id.albumsSpinner);
        ArrayList<String> albumsArray = new ArrayList<>();
        albumsArray.add("album 1");
        albumsArray.add("album 2");

        ArrayAdapter<String> albumsAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,albumsArray);
        albumsSpinner.setAdapter(albumsAdapter);

        ArrayList<String> data = new ArrayList<>();
        data.add("hamid");
        data.add("usma");

        /* ************************************ Items ************************/
        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                data
        );
        lv =(ListView) view.findViewById(R.id.albumsListView);
        lv.setAdapter(adapter);
        ArrayList<String> data2 = new ArrayList<>();
        data2.add("hamid111");
        data2.add("usma11");

        /* ************************************ Micro button ************************/
        microImage = (ImageButton) view.findViewById(R.id.microView);
        microImage.setImageResource(R.drawable.ic_mic_black_24dp);
        microImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRecording){
                    microImage.setImageResource(R.drawable.ic_mic_black_24dp);
                    isRecording=false;
                    try {
                        myAudioRecorder.stop();
                        myAudioRecorder.reset();
                    }catch (IllegalStateException ise){
                        System.out.println("+++++++"+ise.getMessage()+"+++++++");

                    }
                }else{

                    isRecording=true;
                    microImage.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                    Toast.makeText(getContext(), "Recording started", Toast.LENGTH_LONG).show();
                    try {

                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                    } catch (IllegalStateException ise) {

                    } catch (IOException ioe) {
                        System.out.println("+++++++++++++"+ ioe.getMessage()+"++++++++++++++++++++++++++++");
                    }

                }

            }
        });

        /* ************************************ Play button ************************/
        playImage =(ImageButton) view.findViewById(R.id.playSongView);
        playImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    System.out.println("+++++"+ e.getMessage()+"+++++++++++++++++++++");
                }
            }
        });




        return view;
    }
    public ListView getLv()
    {
        return lv;
    }
}
