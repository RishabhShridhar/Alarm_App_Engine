package app.example.android.com.alarm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rishabhshridhar on 18/03/17.
 */

public class RingtonePlayingService extends Service {
    MediaPlayer song;
    private int startId;
    private boolean isrunning;
    private Context context;
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        Intent intentij = new Intent("Myjob.intent.action.Launch");
        String state=intent.getExtras().getString("extra");
        Log.e("Ringtone state is : ", state);
        NotificationManager notifyi=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent_i=new Intent(this.getApplicationContext(),MainActivity.class);
        PendingIntent pending_intent_i=PendingIntent.getActivity(this,0,intent_i,0);
        Notification popup=new Notification.Builder(this)
                .setContentTitle("Alarm Off!")
                .setContentText("Click Here!")
                .setContentIntent(pending_intent_i)
                .setSmallIcon(R.drawable.notification_icon)
                .setAutoCancel(true)
                .build();



        assert state!=null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.e("what is the key?",state);
                break;
            default:
                startId = 0;
                break;
        }

      if(!this.isrunning && startId==1){

          song=MediaPlayer.create(this,R.raw.shapeofyou);

          song.start();
          notifyi.notify(0,popup);
          //Intent intent = new Intent("applicationB.intent.action.Launch");
       //   startActivity(intentij);
          this.isrunning=true;
          this.startId=0;

      }

      else if(!this.isrunning && startId==0){
          //Intent intent = new Intent("Myjob.intent.action.Launch");
         // startActivity(intentij);

          this.isrunning=false;
          this.startId=0;
      }
      else if(this.isrunning && startId==1) {
          //Intent intent = new Intent("Myjob.intent.action.Launch");
          startActivity(intentij);

          this.isrunning = true;
          this.startId = 0;
      }
      else{
          //Intent intent = new Intent("Myjob.intent.action.Launch");
         // startActivity(intentij);
          song.stop();
          song.reset();
          this.isrunning=false;
          this.startId=0;


      };



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Intent intentij = new Intent("Myjob.intent.action.Launch");
        startActivity(intentij);
        super.onDestroy();
        this.isrunning=false;

    }



}
