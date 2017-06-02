package com.example.joanna.musicmachine_advance;

import android.content.Intent;
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
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton= (Button) findViewById(R.id.downloadButton);
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
    }
}
