package com.example.joanna.musicmachine_advance;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;


public class PlayerService extends Service {
    private static final String TAG =PlayerService.class.getSimpleName() ;
    private MediaPlayer mPlayer;
    public Messenger messenger=new Messenger(new PlayHandler(this));
    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        mPlayer=MediaPlayer.create(this,R.raw.jingle);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf();
            }
        });
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        mPlayer.release();
    }

    //client methods
    public void play(){
        mPlayer.start();
    }

    public void pause(){
        mPlayer.pause();
    }

    public boolean isPlaying(){
        return  mPlayer.isPlaying();
    }
}
