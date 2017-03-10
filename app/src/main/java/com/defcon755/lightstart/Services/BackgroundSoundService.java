package com.defcon755.lightstart.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.defcon755.lightstart.R;


/**
 * F
 * Created by 972542 on 10/17/2016.
 */
public class BackgroundSoundService extends Service {
    private static final String TAG = null;
    MediaPlayer player;
    public static String currentUser;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BackgroundSoundService.currentUser.equals("N")){
            player = MediaPlayer.create(this, R.raw.song_n);
        }else if (BackgroundSoundService.currentUser.equals("P")){
            player = MediaPlayer.create(this, R.raw.song_p);
        }
        player.setLooping(true); // Set looping
        player.setVolume(100, 100);

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return 1;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TO DO
        player.start();
    }

    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }

    public void onPause() {

    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }

}
