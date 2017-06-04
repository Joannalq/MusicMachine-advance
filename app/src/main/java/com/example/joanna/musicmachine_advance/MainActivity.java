package com.example.joanna.musicmachine_advance;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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
    private Button mButton;
    private Button mPlayButton;
    private Messenger mServiceMessagner;
    private Messenger mActivityMessagner=new Messenger(new ActivityHandler(this));
    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mybound=true;
            mServiceMessagner=new Messenger(binder);
            Message message=Message.obtain();
            message.arg1=2;
            message.arg2 = 1;
            message.replyTo=mActivityMessagner;
            try {
                mServiceMessagner.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mybound=false;
        }
    };

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
                   Intent intent=new Intent(MainActivity.this,PlayerService.class);
                   startService(intent);
                   Message message=Message.obtain();
                   message.arg1=2;
                   message.replyTo=mActivityMessagner;
                   try {
                       mServiceMessagner.send(message);
                   } catch (RemoteException e) {
                       e.printStackTrace();
                   }
               }
            }
        });
    }
    public void changePlayButtonText(String text) {
        mPlayButton.setText(text);
    }

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
}
