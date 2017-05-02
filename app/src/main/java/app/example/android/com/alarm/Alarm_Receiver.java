package app.example.android.com.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by rishabhshridhar on 18/03/17.
 */

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in the Reciever","Yay!");
        String get_your_intent=intent.getExtras().getString("extra");
        Log.e("We are in the key",get_your_intent);
        Integer choosen=intent.getExtras().getInt("addspinner");
        Intent serviceringtone=new Intent(context,RingtonePlayingService.class);
        serviceringtone.putExtra("extra",get_your_intent);
        context.startService(serviceringtone);

    }
}
