package com.example.joanna.musicmachine_advance;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;


public class ActivityHandler extends Handler {
    private MainActivity mMainActivity;

    public ActivityHandler(MainActivity mainActivity){
        mMainActivity=mainActivity;
    }
    @Override
    public void handleMessage(Message msg) {
        if(msg.arg1==0){
            //Music is not playing
            if(msg.arg2==1){
                mMainActivity.changePlayButtonText("play");
            }else {
                //play the music
                Message message = Message.obtain();
                message.arg1 = 0;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                //change the play button to say "pause"
                mMainActivity.changePlayButtonText("pause");
            }
        }else {
            //Music is not playing
            if (msg.arg2 == 1) {
                mMainActivity.changePlayButtonText("pause");
            } else {
                //play the music
                Message message = Message.obtain();
                message.arg1 = 1;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                //change the play button to say "pause"
                mMainActivity.changePlayButtonText("play");
            }
        }
    }
}
