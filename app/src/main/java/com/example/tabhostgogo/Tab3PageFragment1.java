package com.example.tabhostgogo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ProgressBar;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Tab3PageFragment1 extends Fragment {
    long allTime;
    long nowTime;
    long per;
    long remainTime;
    TextView time1;
    TextView time2;
    TextView percent;
    MyTimer myTimer;
    long start_Time;
    long finish_Time;
    //long middle_Time;
    Calendar cal1;
    Calendar cal2;
    Calendar cal3;
    View view;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        LayoutInflater lf=getActivity().getLayoutInflater();
        view= lf.inflate(R.layout.tab3_fragment1, null);
        time1=(TextView)view.findViewById(R.id.time1);
        time2=(TextView)view.findViewById(R.id.time2);
        percent=(TextView)view.findViewById(R.id.percent);

        //start_Time=new LoadDate().getDate(2019,12,27,13,00,00);
        //finish_Time=new LoadDate().getDate(2020,1,21,18,30,00);

        cal1=Calendar.getInstance();
        cal1.set(Calendar.YEAR,2019);
        cal1.set(Calendar.MONTH,Calendar.DECEMBER);
        cal1.set(Calendar.DATE,26);
        cal1.set(Calendar.HOUR_OF_DAY,13);
        cal1.set(Calendar.MINUTE,00);
        cal1.set(Calendar.SECOND,0);

        cal2=Calendar.getInstance();
        cal2.set(Calendar.YEAR,2020);
        cal2.set(Calendar.MONTH,Calendar.JANUARY);
        cal2.set(Calendar.DATE,22);
        cal2.set(Calendar.HOUR_OF_DAY,18);
        cal2.set(Calendar.MINUTE,30);
        cal2.set(Calendar.SECOND,0);

//        cal3=Calendar.getInstance();
//        cal3.set(Calendar.YEAR,2019);
//        cal3.set(Calendar.MONTH,Calendar.DECEMBER);
//        cal3.set(Calendar.DATE,27);
//        cal3.set(Calendar.HOUR_OF_DAY,13);
//        cal3.set(Calendar.MINUTE,00);
//        cal3.set(Calendar.SECOND,0);

        start_Time=cal1.getTimeInMillis();
        finish_Time=cal2.getTimeInMillis();
//        middle_Time=cal3.getTimeInMillis();
        //이상없음

        myTimer = new MyTimer(6000, 1000);
        return view;
    }

    class MyTimer extends CountDownTimer
    {
        public MyTimer(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
            this.start();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Date now=new Date();

//            System.out.println("startTime_milli= "+start_Time);
//            System.out.println("finishTime_milli= "+finish_Time);
//            System.out.println("allTime_milli= "+allTime);
//            System.out.println("startTime_date= "+new LoadDate().millToDate(start_Time));
//            System.out.println("finishTime_date= "+new LoadDate().millToDate(finish_Time));
//            System.out.println("allTime_date= "+new LoadDate().millToDate(allTime));
//            System.out.println("middleTime_date= "+new LoadDate().millToDate(middle_Time));
//            System.out.println(new LoadDate().millToTime(middle_Time-start_Time));
//            System.out.println(new LoadDate().millToTime(allTime));

            allTime=finish_Time-start_Time;

            nowTime=now.getTime()-start_Time;
            remainTime=finish_Time-now.getTime();
            per=(long)((double)nowTime*100/allTime);

            time1.setText(new LoadDate().millToTime(nowTime));
            time2.setText(new LoadDate().millToTime(remainTime));
            percent.setText(String.format("%05.2f ",(double)nowTime*100/(double)allTime));




            ProgressBar progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
            progressBar.setProgress((int)per);



        }

        @Override
        public void onFinish() {
            this.start();
        }
    }
}

class LoadDate{
    public static Date getDate(int year,int month,int date,int hour,int minute,int second){
        Calendar cal=Calendar.getInstance();
        cal.set(year,month-1,date,hour,minute,second);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }
    public static String millToDate(long msec){
        SimpleDateFormat formatter=new SimpleDateFormat("dd:HH:mm:ss");
        String date=(String)formatter.format(new Timestamp(msec));

        return date;
    }
    public static String millToTime(long msec){
        final long daySec=24*60*60*1000;
        final long hourSec=60*60*1000;
        final long minSec=60*1000;
        final long secSec=1000;

        long day=msec/daySec;
        long hour=(msec%daySec)/hourSec;
        long min=((msec%daySec)%hourSec)/minSec;
        long sec=(((msec%daySec)%hourSec)%minSec)/secSec;

        //return day+":"+hour+":"+min+":"+sec;
        return String.format("%02d:%02d:%02d:%02d",day,hour,min,sec);
    }
}
