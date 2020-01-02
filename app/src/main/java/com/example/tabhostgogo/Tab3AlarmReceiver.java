package com.example.tabhostgogo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tab3AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
//        MainActivity ma= new MainActivity();

        Tab3PageFragment2 t2=new Tab3PageFragment2();

//        Date c = Calendar.getInstance();
//        c.add(Calendar.DATE, -1);
//        .getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        String formatted=df.format(yesterday());
//        Date dateObj = curFormater.parse(dateStr);

        t2.saveTime(formatted, t2.getTime(formatted));
//        t2.chronometer.setBase(SystemClock.elapsedRealtime());
//        t2.chronometer2.setBase();
//        ma.totalT=0;

        t2.saveTime("pauseT", 0);
        t2.saveTime("startT", 0);

    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

}