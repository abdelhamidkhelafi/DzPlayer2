package fr.univavignon.dzplayer;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.SeekBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    SeekBar seekBar;
    Handler handler;
    Runnable runnable;
    RequestQueue queue ;
    boolean paused =false;
    final List<String> ACTIONS= Arrays.asList("augmenter","baisser","jouer","pause","reprendre","arrêter");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        handler = new Handler();
        View view = inflater.inflate(R.layout.songs,container,false);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        AudioSavePathInDevice = getContext().getExternalCacheDir().getAbsolutePath()+ "/" + "AudioRecording.amr";
        // GETTING THE ARRAY LIST FROM SERVER //
        songsArray.add(new Song("Tata ","Soolking","url"));
        songsArray.add(new Song("Dalida ","Soolking","url"));
        songsArray.add(new Song("Aicha ","Khelad","url"));
        songsArray.add(new Song("Deux fois","mami","url"));
        songsArray.add(new Song("Lundi ","Fianso","url"));
        songsArray.add(new Song("Tata ","Soolking","url"));
        songsArray.add(new Song("Dalida ","Soolking","url"));
        songsArray.add(new Song("Aicha ","Khelad","url"));
        songsArray.add(new Song("Deux fois","mami","url"));
        songsArray.add(new Song("Lundi ","Fianso","url"));
        songsArray.add(new Song("Tata ","Soolking","url"));
        songsArray.add(new Song("Dalida ","Soolking","url"));
        songsArray.add(new Song("Aicha ","Khelad","url"));
        songsArray.add(new Song("Deux fois","mami","url"));
        songsArray.add(new Song("Lundi ","Fianso","url"));
        // ................................... //
        for(Song song :songsArray){
            data.add("♬ "+song.getSinger()+" "+song.getName());
        }
        queue = Volley.newRequestQueue(getContext());

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
                    Uri mediaPath = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.ob);
                    mediaPlayer.setDataSource(getContext().getApplicationContext(), mediaPath);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            seekBar.setMax(mediaPlayer.getDuration());
                            playCycle();
                            mediaPlayer.start();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(fromUser){
                            mediaPlayer.seekTo(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                Toast.makeText(getContext(), "Playing",
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


                        String url ="http://192.168.0.14:8080/uploadFile";

                    // Request a string response from the provided URL.
                    SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try{
                                        JSONObject responseJson = new JSONObject(response);

                                        Toast.makeText(getContext(), responseJson.getString("text"),
                                                Toast.LENGTH_SHORT).show();

                                        JSONObject getparams = new JSONObject();
                                        getparams.put("requete",responseJson.getString("text"));
                                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                                "http://192.168.0.14:8080/action/", getparams,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        try {


                                                            actionHandler(response);

                                                        }catch (Exception e){

                                                        }

                                                    }


                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(getContext(), "erreur serveur 2",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        queue.add(jsonObjReq);
                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }



                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Server not responding ....",
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                    smr.addFile("file", AudioSavePathInDevice);

                    queue.add(smr);


                }else{
                    isRecording=true;
                    microImage.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);



                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
                        mediaRecorder.setAudioSamplingRate(16000);
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

    public void playCycle(){
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if(mediaPlayer.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                        playCycle();
                }
            };
            handler.postDelayed(runnable,1000);
        }
    }

    private void actionHandler(JSONObject response){
        try{
            switch(response.getString("action") ) {
                case "augmenter":

                    break;

                case "baisser":

                    break;

                case "jouer":
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.reset();
                        }
                    try {
                        // mediaPlayer.reset();
                        Uri mediaPath = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.ob);
                        mediaPlayer.setDataSource(getContext().getApplicationContext(), mediaPath);
                        mediaPlayer.prepare();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                seekBar.setMax(mediaPlayer.getDuration());
                                playCycle();
                                mediaPlayer.start();
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    break;
                case "pause":
                    if(mediaPlayer.isPlaying())
                    {
                        mediaPlayer.pause();
                        paused =true;
                    }
                    break;
                case "reprendre":
                    if(paused)
                    {

                            mediaPlayer.start();

                    }else{
                        Toast.makeText(getContext(),  "the media play wasn't in pause",
                                Toast.LENGTH_SHORT).show();
                    }


                    break;
                case "arrêter":

                    if(mediaPlayer.isPlaying() || paused)
                    {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                    break;
            }

        }catch (Exception e)
        {

        }
    }

    private String chercherSon(String s)
    {

        return null;
    }



}
