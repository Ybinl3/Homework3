package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerM;
    Spinner spinnerBG;
    Spinner spinnerBG2;
    Spinner spinnerBG3;

    SeekBar seekBar1;
    SeekBar seekBar2;
    SeekBar seekBar3;

    Button playB;
    String currentSongName;
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerM = findViewById(R.id.spinnerMusic);
        spinnerBG = findViewById(R.id.spinner2);
        spinnerBG2 = findViewById(R.id.spinner3);
        spinnerBG3 = findViewById(R.id.spinner4);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.song_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerM.setAdapter(adapter);
        spinnerM.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.bg_names, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBG.setAdapter(adapter2);
        spinnerBG2.setAdapter(adapter2);
        spinnerBG3.setAdapter(adapter2);

        spinnerBG.setOnItemSelectedListener(this);
        spinnerBG2.setOnItemSelectedListener(this);
        spinnerBG3.setOnItemSelectedListener(this);

        seekBar1 = (SeekBar) findViewById(R.id.seekBar);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);



        playB = (Button) findViewById(R.id.button);







    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        if(text.equals("Go Tech Go!")){
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.gotechgo);
            int durationSong = mp.getDuration();
            seekBar1.setMax(durationSong);
            seekBar2.setMax(durationSong);
            seekBar3.setMax(durationSong);
        }
        else if(text.equals("Hokie Fight")){
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.hokiefight);
            int durationSong = mp.getDuration();
            seekBar1.setMax(durationSong);
            seekBar2.setMax(durationSong);
            seekBar3.setMax(durationSong);
        }
        else{
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.entersandman);
            int durationSong = mp.getDuration();
            seekBar1.setMax(durationSong);
            seekBar2.setMax(durationSong);
            seekBar3.setMax(durationSong);
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /*
        Let user go to the second screen
        Save the name of the song, and background options and the seek
     */
    public void play(View view){

        Intent i1 = new Intent(this, SecondActivity.class);
        String text = spinnerM.getSelectedItem().toString();
        String bgtext1 = spinnerBG.getSelectedItem().toString();
        String bgtext2 = spinnerBG2.getSelectedItem().toString();
        String bgtext3 = spinnerBG3.getSelectedItem().toString();
        if(bgtext1.equals("Clapping")){
            i1.putExtra("BG1", 0);
        }
        else if(bgtext1.equals("Cheering")){
            i1.putExtra("BG1", 1);
        }
        else{
            i1.putExtra("BG1", 2);
        }
        if(bgtext2.equals("Clapping")){
            i1.putExtra("BG2", 0);
        }
        else if(bgtext2.equals("Cheering")){
            i1.putExtra("BG2", 1);
        }
        else{
            i1.putExtra("BG2", 2);
        }
        if(bgtext3.equals("Clapping")){
            i1.putExtra("BG3", 0);
        }
        else if(bgtext3.equals("Cheering")){
            i1.putExtra("BG3", 1);
        }
        else{
            i1.putExtra("BG3", 2);
        }

        i1.putExtra("BG1_TIME", seekBar1.getProgress());
        i1.putExtra("BG2_TIME", seekBar2.getProgress());
        i1.putExtra("BG3_TIME", seekBar3.getProgress());

        if(count == 0){
            currentSongName = text;
            i1.putExtra("NEWSONG", "First");
        }
        else{
            if(!currentSongName.equals(text)){
                i1.putExtra("NEWSONG", "Changed");
                currentSongName = text;
            }
            else{
                i1.putExtra("NEWSONG", "Not Changed");
            }
        }

        if(text.equals("Go Tech Go!")){
            i1.putExtra("SONGNUM", 0);
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.gotechgo);
            int durationSong = mp.getDuration();
            i1.putExtra("SONGL", durationSong);
        }
        else if(text.equals("Hokie Fight")){
            i1.putExtra("SONGNUM", 1);
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.hokiefight);
            int durationSong = mp.getDuration();
            i1.putExtra("SONGL", durationSong);
        }
        else{
            i1.putExtra("SONGNUM", 2);
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.entersandman);
            int durationSong = mp.getDuration();
            i1.putExtra("SONGL", durationSong);
        }
        count++;
        startActivity(i1);


    }
}