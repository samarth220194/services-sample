package com.example.sam.servicessample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sam on 30-Jan-17.
 */

public class LocalService extends Service  {

    private MediaPlayer mediaPlayer;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    public LocalService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "Service Binded", Toast.LENGTH_LONG).show();
        mediaPlayer=MediaPlayer.create(this,R.raw.jingle);
        if(!mediaPlayer.isPlaying())
        mediaPlayer.start();
        return mBinder;
    }

    /** Called when the service is being created. */
    @Override
    public void onCreate() {
        super.onCreate();


    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }


    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {

        LocalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocalService.this;
        }
    }
    public void start_mp(){
        if(!mediaPlayer.isPlaying())
        mediaPlayer.start();
    }
    public void pause_mp(){
        mediaPlayer.pause();
    }
    public void restart_mp(){
        if(mediaPlayer.isPlaying()) mediaPlayer.release();
        mediaPlayer=MediaPlayer.create(this,R.raw.jingle);
        mediaPlayer.start();
    }


}
