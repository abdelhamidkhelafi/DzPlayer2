package fr.univavignon.dzplayer;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import  android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.http.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Songs extends Fragment {
    MediaPlayer mediaPlayer ;
    ImageButton playImage;
    ImageButton stopImage;
    ImageButton microImage;
    MediaRecorder mediaRecorder;
    String AudioSavePathInDevice;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<Song> songsArray = new ArrayList<>();
    boolean isRecording=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.songs,container,false);
        AudioSavePathInDevice = getContext().getExternalCacheDir().getAbsolutePath()+ "/" + "AudioRecording.raw";
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

                try {
                   // mediaPlayer.reset();
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(getContext(), "Recording Playing",
                        Toast.LENGTH_LONG).show();

            }
        });

        stopImage = (ImageButton) view.findViewById(R.id.stopSongView);
        stopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    //mediaPlayer.release();
                    mediaPlayer.reset();
                    Toast.makeText(getContext(), "Stop music ",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

        /* ****************************** Recorder***************************************** */
        microImage = (ImageButton) view.findViewById(R.id.microView);
        microImage.setImageResource(R.drawable.ic_mic_black_24dp);
        microImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRecording){

                    microImage.setImageResource(R.drawable.ic_mic_black_24dp);
                    mediaRecorder.stop();
                    isRecording=false;
                    Toast.makeText(getContext(), "Stop recording ",
                            Toast.LENGTH_SHORT).show();


                    try {
                        HttpPost httppost = new HttpPost(uri);


                        File f = new File(AudioSavePathInDevice);;
                        FileBody bin = new FileBody(f);


                        MultipartEntity reqEntity = new MultipartEntity();
                        reqEntity.addPart("file", bin);
                        reqEntity.addPart("paramName", paramValue);

                        httppost.setEntity(reqEntity);

                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity resEntity = response.getEntity();

                        String postResponse = response.getStatusLine();
                    } catch (Exception e) {
                        // show error
                    }

                }else{
                    isRecording=true;
                    microImage.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);



                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        mediaRecorder.setOutputFile(AudioSavePathInDevice);
                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        Toast.makeText(getContext(), "Recording",
                                Toast.LENGTH_LONG).show();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return view;
    }


    public void setMediaPlayer(MediaPlayer m){
        mediaPlayer = m;

    }
    public void setMediaRecorder (MediaRecorder m){
        mediaRecorder = m;

    }





}
