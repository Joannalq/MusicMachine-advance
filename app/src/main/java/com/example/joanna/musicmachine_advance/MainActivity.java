package com.example.joanna.musicmachine_advance;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG =MainActivity.class.getSimpleName();
    public static final String KEY_SONG = "song";
    private boolean mybound=false;
    private PlayerService mPlayerService;
    private Button mButton;
    private Button mPlayButton;
    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mybound=true;
            PlayerService.LocalBinder localBinder= (PlayerService.LocalBinder) binder;
            mPlayerService=localBinder.getService();
            if(mPlayerService.isPlaying()){
                mPlayButton.setText("pause");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mybound=false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,PlayerService.class);
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mybound){
            unbindService(mServiceConnection);
            mybound=false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton= (Button) findViewById(R.id.downloadButton);
        mPlayButton= (Button) findViewById(R.id.playButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Download song",Toast.LENGTH_SHORT).show();

                //send message to handler in process
                for (String song:PlayList.songs) {
                    Intent intent=new Intent(MainActivity.this,DownloadIntentService.class);
                    intent.putExtra(KEY_SONG,song);
                    startService(intent);
                }
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(mybound){
                   if(mPlayerService.isPlaying()){
                       mPlayerService.pause();
                       mPlayButton.setText("PLAY");
                   }else {
                       Intent intent=new Intent(MainActivity.this,PlayerService.class);
                       startService(intent);
                       mPlayerService.play();
                       mPlayButton.setText("PAUSE");
                   }
               }
            }
        });
    }
}
