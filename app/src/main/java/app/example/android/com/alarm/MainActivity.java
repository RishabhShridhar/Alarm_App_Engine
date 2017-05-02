package app.example.android.com.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import static android.R.layout.simple_spinner_item;
import static android.content.Context.ALARM_SERVICE;

//import static app.example.android.com.alarm.R.id.editText;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    AlarmManager alarmManager;
    Calendar calendar;
    Context context;
    //TextView utext;
    PendingIntent pending_intent;
    int choosen=0;
    Spinner spinner;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        this.context=this;
        final AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        final TimePicker timePicker=(TimePicker)findViewById(R.id.timePicker);
        final Calendar calendar=Calendar.getInstance();
  //      final Spinner spinner = (Spinner) findViewById(R.id.addspinner);

        //spinner.setOnItemSelectedListener(this);

        Button alarm_off=(Button)findViewById(R.id.alarm_off);
        final Intent my_intent=new Intent(this.context,Alarm_Receiver.class);
        Button alarm_on=(Button)findViewById(R.id.alarm_on);


// Selection of the spinner
        Spinner spinner = (Spinner) findViewById(R.id.addspinner);

        ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.addspinner, android.R.layout.simple_spinner_item);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view

       // spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(spinnerArrayAdapter);
      //  spinner.setOnItemSelectedListener(this);

        spinner.setPrompt("Select Ringtone!");


        alarm_off.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                my_intent.putExtra("addspinner",choosen);
                alarmManager.cancel(pending_intent);
                my_intent.putExtra("extra","alarm off");
                sendBroadcast(my_intent);

            }
        });
        alarm_on.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
         //       settheText("Alarm is Active");

                my_intent.putExtra("addspinner",choosen);
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                calendar.set(Calendar.MINUTE,timePicker.getMinute());

                int hour=timePicker.getHour();
                int min=timePicker.getMinute();

                String s=String.valueOf(hour);
                String m=String.valueOf(min);
                if(min<10)
                    m= '0'+String.valueOf(min);

                send_notification("Alarm is set for: "+s+":"+m);
                my_intent.putExtra("extra","alarm on");

                pending_intent=PendingIntent.getBroadcast(MainActivity.this,0,my_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent);


            }
        });
    }





    /*private void settheText(String s) {
        TextView utext=(TextView)findViewById(R.id.textView);

        utext.setText(s);
    }*/

    private void send_notification(String s) {

         NotificationCompat.Builder mBuilder =
                 (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                 .setSmallIcon(R.drawable.notification_icon)
                 .setContentTitle("Alarm alert")
                 .setContentText(s);


                     // Sets an ID for the notification
                     int mNotificationId = 001;
                     // Gets an instance of the NotificationManager service
                     NotificationManager mNotifyMgr =
                             (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                     // Builds the notification and issues it.
                     mNotifyMgr.notify(mNotificationId, mBuilder.build());





                             }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        choosen=(int)id;
        // Toast.makeText
        Toast.makeText(parent.getContext(), (int) id,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
