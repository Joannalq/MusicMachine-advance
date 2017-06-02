package com.example.joanna.musicmachine_advance;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG =MainActivity.class.getSimpleName();
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //avoid null
        final DownloadThread thread=new DownloadThread();
        thread.setName("Download");
        thread.start();

        mButton= (Button) findViewById(R.id.downloadButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Download song",Toast.LENGTH_SHORT).show();


                //use a Runnable with new threads
                //method1:
               /* Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        downloadSong();
                    }
                };
                Thread thread=new Thread(runnable);*/

                //send message to handler in process
                for (String song:PlayList.songs) {
                    //messages can be re-used in the message pool
                    Message message=Message.obtain();
                    //send the song name to the message
                    message.obj=song;
                    //add to message queue.
                    thread.mHandler.sendMessage(message);
                }
            }
        });
    }
//method1:
 /*   private void downloadSong() {
        //before loop, need stop 10 ms
        long endTime=System.currentTimeMillis()+10*1000;
        while (System.currentTimeMillis()<endTime){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG,"download song");
        }
    }*/
}
