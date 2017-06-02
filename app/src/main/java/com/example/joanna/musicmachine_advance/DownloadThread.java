package com.example.joanna.musicmachine_advance;


import android.os.Looper;
import android.util.Log;

public class DownloadThread extends Thread {
    private static final String TAG =DownloadThread.class.getSimpleName() ;
    public DownloadHandler mHandler;

    @Override
    public void run() {
        //create a looper for a thread and create message queue
        Looper.prepare();
        //default:current thread
        mHandler=new DownloadHandler();
        Looper.loop();
    }

}
