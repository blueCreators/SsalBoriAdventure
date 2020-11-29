package org.techtown.ppackchinda;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService extends Service{
    private IBinder mBinder = new MyBinder();
    MediaPlayer m;

    @Override
    public void onCreate() {
        super.onCreate();
        m = MediaPlayer.create(this,R.raw.letsmarch);
        m.setVolume((float)0.5,(float)0.5);
        m.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        m.start();
        return super.onStartCommand(intent,flags,startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        m.stop();
        m.release();
        m = null;
    }

    public boolean isPlayeing()
    {
        return (m!=null && m.isPlaying());
    }


    public class MyBinder extends Binder {

        public org.techtown.ppackchinda.MyService getService() {
            return org.techtown.ppackchinda.MyService.this;
        }
    }
}
