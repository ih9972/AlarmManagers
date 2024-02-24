package com.example.alarmmanagers;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.example.AlarmReceiver;

public class MainActivity extends AppCompatActivity {
    private static int ALARM_RQST_CODE = 1;
    private static PendingIntent alarmIntent;
    private static AlarmManager alarmMgr;
    private static Context context;
    private static int snooze;
    private static Calendar calSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        snooze = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
    }

    /**
     * openTimePickerDialog method
     * <p> Open dialog to use to pick time of day
     * </p>
     *
     * @param is24r boolean indicating whether time picker dialog for 24-hour
     */
    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);
        timePickerDialog.setTitle("Choose time");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        /**
         * onTimeSet method
         * <p> Return the time of day picked by the user
         * </p>
         *
         * @param view the time picker view that triggered the method
         * @param hourOfDay the hour the user picked
         * @param minute the minute the user picked
         */
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            calSet = (Calendar) calNow.clone();
            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                calSet.add(Calendar.DATE, 1);
            }
            setAlarm(calSet);
        }
    };

    /**
     * setAlarm method
     * <p> Set the alarm by the time of day thr user picked
     * </p>
     *
     * @param calSet the Calendar object that represent the tod the user selected
     */
    public static void setAlarm(Calendar calSet) {
        ALARM_RQST_CODE++;
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context,
                ALARM_RQST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.set(AlarmManager.RTC_WAKEUP,
                calSet.getTimeInMillis(), alarmIntent);
        snooze = 0;
    }

    public void todAlarm(View view) {
        openTimePickerDialog(true);
    }

    public static void cancelAlarm() {
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context,
                ALARM_RQST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(alarmIntent);
        ALARM_RQST_CODE--;
    }

    public void ballzz(View view) {
        finish();
    }

    public static void setSnooze(int value) {
        snooze = value;
    }

    public static int getSnooze() {
        return snooze;
    }

    public static void setCalSet(int min){
        calSet.set(Calendar.MINUTE, min);
    }

    public static int getCalSet() {
        return calSet.get(Calendar.MINUTE);
    }
}