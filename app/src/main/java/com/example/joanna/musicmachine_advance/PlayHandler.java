package com.example.joanna.musicmachine_advance;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;


public class PlayHandler extends Handler {
    private PlayerService mPlayerService;

    public PlayHandler (PlayerService playerService){
        mPlayerService=playerService;
    }

    @Override
    public void handleMessage(Message msg) {
       switch (msg.arg1){
           case 0://play
               mPlayerService.play();
               break;

           case 1://pause
               mPlayerService.pause();
               break;

           case 2://isplaying
               int isplaying=mPlayerService.isPlaying()?1:0;
               Message message=Message.obtain();
               message.arg1=isplaying;
               if(msg.arg2==1){
                   message.arg2=1;
               }
               message.replyTo=mPlayerService.messenger;
               try {
                   msg.replyTo.send(message);
               } catch (RemoteException e) {
                   e.printStackTrace();
               }

       }
    }
}
