package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.alarmmanagers.MainActivity;
import com.example.alarmmanagers.R;

public class NotiHandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent gi = getIntent();
        if(gi.getBooleanExtra("isOk",true)){
            MainActivity.cancelAlarm();
            finish();
        }
        else{
            int snooze = MainActivity.getSnooze()+1;
            MainActivity.setSnooze(snooze);
            int min = MainActivity.getCalSet();
            MainActivity.setAlarm(MainActivity.setCalSet(min));
        }
    }
}