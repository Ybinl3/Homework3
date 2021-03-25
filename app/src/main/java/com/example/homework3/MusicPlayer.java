package com.example.homework3;

import android.media.MediaPlayer;


public class MusicPlayer{
    MediaPlayer player;
    MediaPlayer player2;
    MediaPlayer player3;
    MediaPlayer player4;



    int currentPosition = 0;
    int currentBGPosition = 0;
    int currentBG2Position = 0;
    int currentBG3Position = 0;
    int musicIndex = 0;
    int bgIndex = 0;
    int bg2Index = 0;
    int bg3Index = 0;
    private int musicStatus = 0; //0 before playing, 1 playing, 2 paused, 3 new song selected
    private int bgStatus = 0;
    private int bg2Status = 0;
    private int bg3Status = 0;
    private MusicService musicService;

    static final int[] MUSICPATH = new int[]{
            R.raw.gotechgo,
            R.raw.hokiefight,
            R.raw.entersandman
    };
    static final int[] BGPATH = new int[]{
            R.raw.clapping,
            R.raw.cheering,
            R.raw.lestgohokies
    };
    static final String[] MUSICNAME = new String[]{
            "Go Tech Go!",
            "Hokie Fight",
            "Enter Sandman"
    };
    static final String[] BGNAME = new String[]{
            "Clapping",
            "Cheering",
            "Go Hokies!"
    };
    public MusicPlayer(MusicService service){
        this.musicService = service;
    }
    public int getMusicStatus(){
        return musicStatus;
    }
    public String getMusicName(){
        return MUSICNAME[musicIndex];
    }
    public String getBGName(){
        return BGNAME[bgIndex];
    }
    public String getBG2Name(){
        return BGNAME[bg2Index];
    }
    public String getBG3Name(){
        return BGNAME[bg3Index];
    }
    public void setMusicIndex(int index){
        musicIndex = index;
    }
    public void playMusic(){
        player = MediaPlayer.create(this.musicService, MUSICPATH[musicIndex]);
        player2 = MediaPlayer.create(this.musicService, BGPATH[bgIndex]);
        player3 = MediaPlayer.create(this.musicService, BGPATH[bg2Index]);
        player4 = MediaPlayer.create(this.musicService, BGPATH[bg3Index]);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                musicService.onUpdateMusicName(getMusicName(), musicIndex);
                restartMusic();
            }

        });
        player.start();

        musicStatus = 1;
    }
    public void playBGMusic(){
        //player2 checks when to start by check player current position
        player2 = MediaPlayer.create(this.musicService, BGPATH[bgIndex]);
        player2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                player2.release();
                player2 = null;
                musicService.onUpdateMusicName(getMusicName(), musicIndex);
            }

        });
        if(musicStatus == 1){
            player2.start();
            bgStatus = 1;
        }

    }
    public void playBG2Music(){
        //player2 checks when to start by check player current position
        player3 = MediaPlayer.create(this.musicService, BGPATH[bg2Index]);
        player3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                player3.release();
                player3 = null;
                musicService.onUpdateMusicName(getMusicName(), musicIndex);
            }

        });
        if(musicStatus == 1){
            player3.start();


            bg2Status = 1;
        }

    }
    public void playBG3Music(){
        //player2 checks when to start by check player current position
        player4 = MediaPlayer.create(this.musicService, BGPATH[bg3Index]);
        player4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                player4.release();
                player4 = null;
                musicService.onUpdateMusicName(getMusicName(), musicIndex);
            }

        });
        if(musicStatus == 1){
            player4.start();


            bg3Status = 1;
        }

    }
    public void resumeBGMusic(){
        if(player2 != null && bgStatus == 2){
            player2.seekTo(currentBGPosition);
            player2.start();
            bgStatus=1;
        }
    }
    public void resume2BGMusic(){
        if(player3 != null && bgStatus == 2){
            player3.seekTo(currentBG2Position);
            player3.start();
            bg2Status=1;
        }
    }
    public void resume3BGMusic(){
        if(player4 != null && bgStatus == 2){
            player4.seekTo(currentBG3Position);
            player4.start();
            bg3Status=1;
        }
    }
    public void pauseBGMusic(){
        if(player2 != null && player2.isPlaying()){
            player2.pause();
            currentBGPosition = player2.getCurrentPosition();
            bgStatus = 2;
        }
        if(player3 != null && player3.isPlaying()){
            player3.pause();
            currentBG2Position = player3.getCurrentPosition();
            bg2Status = 2;
        }
        if(player4 != null && player4.isPlaying()){
            player4.pause();
            currentBG3Position = player4.getCurrentPosition();
            bg3Status = 2;
        }
    }
    /*
    public void pauseBG2Music(){
        if(player3 != null && player3.isPlaying()){
            player3.pause();
            currentBG2Position = player3.getCurrentPosition();
            bg2Status = 2;
        }
    }
    public void pauseBG3Music(){
        if(player4 != null && player4.isPlaying()){
            player4.pause();
            currentBG3Position = player4.getCurrentPosition();
            bg3Status = 2;
        }
    }
    */
    public void resumeMusic(){
        if(player != null){
            player.seekTo(currentPosition);
            player.start();
            musicStatus=1;
        }
    }

    public void pauseMusic(){
        if(player != null && player.isPlaying()){
            player.pause();
            currentPosition = player.getCurrentPosition();
            musicStatus = 2;
        }
    }
    public void restartMusic(){
        if(player != null){
            currentPosition = 0;
            musicStatus = 0;
            player.seekTo(0);
            player.pause();
            musicStatus = 0;
        }
        if(player2 != null){
            currentBGPosition = 0;
            bgStatus = 0;
            player2.seekTo(0);
            player2.pause();
            bgStatus = 0;
        }
        if(player3 != null){
            currentBG2Position = 0;
            bg2Status = 0;
            player3.seekTo(0);
            player3.pause();
            bg2Status = 0;
        }
        if(player4 != null){
            currentBG3Position = 0;
            bg3Status = 0;
            player4.seekTo(0);
            player4.pause();
            bg3Status = 0;
        }
    }



    public void setSongStatus(int status) {
        musicStatus = status;
    }

    public void setBGIndex(int index) {
        bgIndex = index;
    }
    public void setBG2Index(int index) {
        bg2Index = index;
    }
    public void setBG3Index(int index) {
        bg3Index = index;
    }
}