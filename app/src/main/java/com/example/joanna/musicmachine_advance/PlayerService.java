package com.example.joanna.musicmachine_advance;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public class PlayerService extends Service {
    private static final String TAG =PlayerService.class.getSimpleName() ;
    private MediaPlayer mPlayer;
    private IBinder mbinder=new LocalBinder();

    public class LocalBinder extends Binder{
        public PlayerService getService(){
            return PlayerService.this;
        }
    }
    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        mPlayer=MediaPlayer.create(this,R.raw.jingle);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return mbinder;
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
