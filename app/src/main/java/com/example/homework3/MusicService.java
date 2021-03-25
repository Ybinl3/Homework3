package com.example.homework3;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    MusicPlayer musicPlayer;

    private final IBinder iBinder = new MyBinder();

    public static final String COMPLETE_INTENT = "complete intent";
    public static final String MUSICNAME = "music name";

    @Override
    public void onCreate(){
        super.onCreate();
        musicPlayer = new MusicPlayer(this);
    }
    public void setSong(int index){
        musicPlayer.setMusicIndex(index);
    }
    public void setSongStatus(int status){
        musicPlayer.setSongStatus(status);
    }
    public void startMusic(){
        musicPlayer.playMusic();
    }
    public void startBGMusic(){
        musicPlayer.playBGMusic();
    }
    public void startBG2Music(){
        musicPlayer.playBG2Music();
    }
    public void startBG3Music(){
        musicPlayer.playBG3Music();
    }
    public void pauseMusic(){
        musicPlayer.pauseMusic();
    }
    public void pauseBGMusic(){
        musicPlayer.pauseBGMusic();
    }
    /*
    public void pauseBG2Music(){
        musicPlayer.pauseBG2Music();
    }
    public void pauseBG3Music(){
        musicPlayer.pauseBG3Music();
    }
    */

    public void resumeMusic(){
        musicPlayer.resumeMusic();
    }
    public void resumeBGMusic(){
        musicPlayer.resumeBGMusic();
    }
    public void resumeBG2Music(){
        musicPlayer.resume2BGMusic();
    }
    public void resumeBG3Music(){
        musicPlayer.resume3BGMusic();
    }
    public void restartMusic(){
        musicPlayer.restartMusic();
    }
    public int getPlayingStatus(){
        return musicPlayer.getMusicStatus();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public void onUpdateMusicName(String musicName, int musicIndex) {
        Intent intent = new Intent(COMPLETE_INTENT);
        intent.putExtra(MUSICNAME, musicName);
        intent.putExtra("MUSICIMAGE", musicIndex);
        sendBroadcast(intent);
    }


    public void setBGSong(int index) {
        musicPlayer.setBGIndex(index);
    }
    public void setBG2Song(int index) {
        musicPlayer.setBG2Index(index);
    }
    public void setBG3Song(int index) {
        musicPlayer.setBG3Index(index);
    }



    public class MyBinder extends Binder {
        MusicService getService(){
            return MusicService.this;
        }
    }
}
