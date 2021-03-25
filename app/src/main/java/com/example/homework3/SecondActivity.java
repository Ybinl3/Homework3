package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{


    Button playpauseButton;
    Button restartButton;
    TextView musicName;
    ImageView imageSong;
    int index;
    int status;

    int bgIndex;
    int bg2Index;
    int bg3Index;

    int songTime;
    int bg1StartTime;
    int bg2StartTime;
    int bg3StartTime;



    boolean changed = false;
    MusicService musicService;
    MusicCompletionReceiver musicCompletionReceiver;
    Intent startMusicServiceIntent;

    boolean isBound = false;
    boolean isInitialized = false;

    public static final String INITIALIZE_STATUS = "initialization status";
    public static final String MUSIC_PLAYING = "music playing";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        playpauseButton = (Button) findViewById(R.id.playButton);
        restartButton = (Button) findViewById(R.id.restartButton);
        musicName = (TextView) findViewById(R.id.songName);
        imageSong = (ImageView) findViewById(R.id.songImage);
        playpauseButton.setOnClickListener(this);
        restartButton.setOnClickListener(this);
        Intent intent = getIntent();
        index = intent.getIntExtra("SONGNUM", 0);

        bg1StartTime = intent.getIntExtra("BG1_TIME", 0);
        bg2StartTime = intent.getIntExtra("BG2_TIME", 0);
        bg3StartTime = intent.getIntExtra("BG3_TIME", 0);
        songTime = intent.getIntExtra("SONGL", 0);

        if(intent.getStringExtra("NEWSONG").equals("Changed")){
            status = 0;
            changed = true;
        }
        if(index == 0){
            imageSong.setImageResource(R.drawable.gotechgo);
            imageSong.setTag(R.drawable.gotechgo);
            musicName.setText(MusicPlayer.MUSICNAME[index]);
        }
        else if(index == 1){
            imageSong.setImageResource(R.drawable.hokiefight);
            imageSong.setTag(R.drawable.hokiefight);
            musicName.setText(MusicPlayer.MUSICNAME[index]);
        }
        else{
            imageSong.setImageResource(R.drawable.entersandman);
            imageSong.setTag(R.drawable.entersandman);
            musicName.setText(MusicPlayer.MUSICNAME[index]);
        }
        bgIndex = intent.getIntExtra("BG1", 0);
        bg2Index = intent.getIntExtra("BG2", 0);
        bg3Index = intent.getIntExtra("BG3", 0);
        if(savedInstanceState != null){
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS);
            musicName.setText(savedInstanceState.getString(MUSIC_PLAYING));
            int imageID = savedInstanceState.getInt("MUSIC_IMAGE");
            switch(imageID){
                case R.drawable.cheering:
                    imageSong.setImageResource(R.drawable.cheering);
                    imageSong.setTag(R.drawable.cheering);
                    break;
                case R.drawable.clapping:
                    imageSong.setImageResource(R.drawable.clapping);
                    imageSong.setTag(R.drawable.clapping);
                    break;
                case R.drawable.letsgohokies:
                    imageSong.setImageResource(R.drawable.letsgohokies);
                    imageSong.setTag(R.drawable.letsgohokies);
                    break;
                case R.drawable.entersandman:
                    imageSong.setImageResource(R.drawable.entersandman);
                    imageSong.setTag(R.drawable.entersandman);
                    break;
                case R.drawable.hokiefight:
                    imageSong.setImageResource(R.drawable.hokiefight);
                    imageSong.setTag(R.drawable.hokiefight);
                    break;
                case R.drawable.gotechgo:
                    imageSong.setImageResource(R.drawable.gotechgo);
                    imageSong.setTag(R.drawable.gotechgo);
                    break;

            }


        }
        startMusicServiceIntent = new Intent(this, MusicService.class);

        if(!isInitialized){
            startService(startMusicServiceIntent);
            isInitialized = true;
        }

        musicCompletionReceiver = new MusicCompletionReceiver(this);
    }



    public void updateName(String musicName) {
        this.musicName.setText(musicName);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == (R.id.playButton)){
            if(isBound){
                switch (musicService.getPlayingStatus()){
                    case 0:
                        musicService.setSong(index);
                        musicService.setBGSong(bgIndex);
                        musicService.setBG2Song(bg2Index);
                        musicService.setBG3Song(bg3Index);
                        musicService.startMusic();

                        long bg1T = bg1StartTime;
                        long bg2T = bg2StartTime;
                        long bg3T = bg3StartTime;


                        new CountDownTimer(bg1T, 1000){

                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                musicService.startBGMusic();
                                if(bgIndex == 0){
                                    imageSong.setImageResource(R.drawable.clapping);
                                    imageSong.setTag(R.drawable.clapping);
                                }
                                else if(bgIndex == 1){
                                    imageSong.setImageResource(R.drawable.cheering);
                                    imageSong.setImageResource(R.drawable.cheering);
                                }
                                else{
                                    imageSong.setImageResource(R.drawable.letsgohokies);
                                    imageSong.setImageResource(R.drawable.letsgohokies);
                                }

                            }
                        }.start();
                        new CountDownTimer(bg2T, 1000){

                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                musicService.startBG2Music();
                                if(bg2Index == 0){
                                    imageSong.setImageResource(R.drawable.clapping);
                                    imageSong.setTag(R.drawable.clapping);
                                }
                                else if(bg2Index == 1){
                                    imageSong.setImageResource(R.drawable.cheering);
                                    imageSong.setImageResource(R.drawable.cheering);
                                }
                                else{
                                    imageSong.setImageResource(R.drawable.letsgohokies);
                                    imageSong.setImageResource(R.drawable.letsgohokies);
                                }
                            }
                        }.start();
                        new CountDownTimer(bg3T, 1000){

                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                musicService.startBG3Music();
                                if(bg3Index == 0){
                                    imageSong.setImageResource(R.drawable.clapping);
                                    imageSong.setTag(R.drawable.clapping);
                                }
                                else if(bg3Index == 1){
                                    imageSong.setImageResource(R.drawable.cheering);
                                    imageSong.setImageResource(R.drawable.cheering);
                                }
                                else{
                                    imageSong.setImageResource(R.drawable.letsgohokies);
                                    imageSong.setImageResource(R.drawable.letsgohokies);
                                }
                            }
                        }.start();
                        playpauseButton.setText("Pause");
                        break;
                    case 1:
                        musicService.pauseBGMusic();
                        musicService.pauseMusic();

                        //musicService.pauseBG2Music();
                        //musicService.pauseBG3Music();
                        playpauseButton.setText("Resume");
                        break;
                    case 2:
                        musicService.resumeMusic();
                        musicService.resumeBGMusic();
                        musicService.resumeBG2Music();
                        musicService.resumeBG3Music();
                        playpauseButton.setText("Pause");
                }
            }

        }
        else if (v.getId() == R.id.restartButton){
            if(isBound){
                if(index == 0){
                    imageSong.setImageResource(R.drawable.gotechgo);
                    imageSong.setTag(R.drawable.gotechgo);
                }
                else if(index == 1){
                    imageSong.setImageResource(R.drawable.hokiefight);
                    imageSong.setTag(R.drawable.hokiefight);
                }
                else{
                    imageSong.setImageResource(R.drawable.entersandman);
                    imageSong.setTag(R.drawable.entersandman);
                }
                musicService.restartMusic();
                playpauseButton.setText("Play");
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(isInitialized && !isBound){
            bindService(startMusicServiceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE);
        }
        registerReceiver(musicCompletionReceiver, new IntentFilter(MusicService.COMPLETE_INTENT));
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(isBound){
            unbindService(musicServiceConnection);
            isBound = false;
        }

        unregisterReceiver(musicCompletionReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        outState.putString(MUSIC_PLAYING, musicName.getText().toString());
        outState.putInt("MUSIC_IMAGE", (Integer) imageSong.getTag());

        super.onSaveInstanceState(outState);
    }
    private ServiceConnection musicServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MusicService.MyBinder binder = (MusicService.MyBinder) iBinder;
            musicService = binder.getService();
            if(changed){
                musicService.setSongStatus(status);
                musicService.restartMusic();
            }
            isBound = true;

            switch(musicService.getPlayingStatus()){
                case 0:
                    playpauseButton.setText("Start");
                    break;
                case 1:
                    playpauseButton.setText("Pause");
                    break;
                case 2:
                    playpauseButton.setText("Resume");
                    break;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
            isBound = false;
        }
    };

    public void updateImage(int musicImage) {
        if(index == 0){
            imageSong.setImageResource(R.drawable.gotechgo);
            imageSong.setTag(R.drawable.gotechgo);
        }
        else if(index == 1){
            imageSong.setImageResource(R.drawable.hokiefight);
            imageSong.setTag(R.drawable.hokiefight);
        }
        else{
            imageSong.setImageResource(R.drawable.entersandman);
            imageSong.setTag(R.drawable.entersandman);
        }

    }
}