package com.example.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MusicCompletionReceiver extends BroadcastReceiver {
    SecondActivity secondActivity;

    public MusicCompletionReceiver(){
        //default empty constructor
    }
    public MusicCompletionReceiver(SecondActivity secondActivity){
        this.secondActivity = secondActivity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String musicName = intent.getStringExtra(MusicService.MUSICNAME);
        int musicImage = intent.getIntExtra("MUSICIMAGE", 0);
        secondActivity.updateName(musicName);
        secondActivity.updateImage(musicImage);
    }
}
