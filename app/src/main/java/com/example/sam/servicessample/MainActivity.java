package com.example.sam.servicessample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String msg = "Android : ";

    private Button play,pause,restart;
    LocalService mService;
    boolean mBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=(Button) findViewById(R.id.play);
        pause=(Button) findViewById(R.id.pause);
        restart=(Button) findViewById(R.id.restart);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        restart.setOnClickListener(this);
        Log.d(msg, "The onCreate() event");
        Intent intent = new Intent(this, LocalService.class);
        startService(intent);
    }
    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");

    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");

    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
        Log.d("mbound value",mBound+"");
        if (mBound) {
            Intent intent=new Intent(this,LocalService.class);
            stopService(intent);
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case(R.id.play):
                    if(mBound)
                        mService.start_mp();
                    break;
                case(R.id.pause):
                    if(mBound)
                        mService.pause_mp();
                    break;
                case (R.id.restart):
                    if(mBound)
                        mService.restart_mp();
                    break;

                default:
                    break;
            }
    }
    /** Defines callbacks for service binding, passed to bindService() */
    public ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
