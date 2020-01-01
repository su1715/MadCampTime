package com.example.tabhostgogo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


public class Tab3PageFragment2 extends Fragment {
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    public Chronometer chronometer;
    private Chronometer chronometer2;
    private long pauseOffset;
    private BarChart barChart;
    private ArrayList<BarEntry> chartTime=new ArrayList<>();
    public long totalT;
    //    private ArrayList<String> dayString=new ArrayList<>();
    private String[] dayString;
    private String[] dateList = new String[]{"2019.12.26",	"2019.12.27",	"2019.12.28",	"2019.12.29",	"2019.12.30",	"2019.12.31",	"2020.01.01",	"2020.01.02",
            "2020.01.03",	"2020.01.04",	"2020.01.05",	"2020.01.06",	"2020.01.07",	"2020.01.08",	"2020.01.09",	"2020.01.10",	"2020.01.11",	"2020.01.12",
            "2020.01.13",	"2020.01.14",	"2020.01.15",	"2020.01.16",	"2020.01.17",	"2020.01.18",	"2020.01.19",	"2020.01.20",	"2020.01.21",	"2020.01.22"};
    private String[] dayList=new String[]{"일", "월","화","수","목","금","토", "일", "월","화","수","목","금","토"};
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.tab3_fragment2, null);

        chronometer = view.findViewById(R.id.chronometer);
        chronometer.setFormat("오늘 몰입 시간: %s");
        chronometer2 = view.findViewById(R.id.chronometer2);
        chronometer2.setFormat("총 몰입 시간: %s");

        saveTime("2019.12.26", 38000000);
        saveTime("2019.12.27", 40000000);
        saveTime("2019.12.28", 36000000);
//        saveTime("20", 0);
        saveTime("2019.12.30", 35000000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
//        System.out.println(df.format(new Date()));
        String today=df.format(new Date());
        System.out.println("11"+ getTime(today));
        saveTime(today,getTime("pauseT"));
//        System.out.println(getTime(day));
        for (int i = 0; i < dateList.length; i++) {
            System.out.println(getTime(dateList[i]));
        }
        System.out.println("83"+totalOffset());
//        saveTime("totalT", totalOffset());
//        saveTime("pauseT", 0);
//        saveTime("startT", 0);
//        Toast toast = Toast.makeText(this, "Hello", Toast.LENGTH_SHORT);
//        toast.show();

        AlarmManager alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), Tab3AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
// Set the alarm to start at approximately 00:00 h(24h format).
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 00);
//repeteat alarm every 24hours
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);

        //그래프 구현


        chartTime=getChartData(today);

        barChart=(BarChart) view.findViewById(R.id.chart);
        BarDataSet barDataSet=new BarDataSet(chartTime,"CodeTime"); //dataset
        barDataSet.setBarBorderWidth(0.8f);
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS); //색

        BarData data=new BarData(barDataSet);
        XAxis xAxis=barChart.getXAxis();
//        ArrayList<String> getAxis=getLabel(today);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        dayString=getLabel(today);
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(dayString);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(dayString));
//        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter); //xaxis
//        xAxis.setAx
        //axis deletion
        YAxis yAxis=barChart.getAxisLeft();
        YAxis yyAxis=barChart.getAxisRight();
        yyAxis.setDrawGridLines(false);
        yyAxis.setDrawAxisLine(false);
        yyAxis.setDrawLabels(false);
        yyAxis.setDrawGridLines(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        barChart.setDrawValueAboveBar(false);
//        private ArrayList<Character> getAxis;
//            return b;
        barChart.setData(data);
//        barChart.setFitBars(true);
        barChart.animateXY(3000, 3000);
//        barChart.invalidate();
        Tab3MyMarkerView marker = new Tab3MyMarkerView(getContext(),R.layout.tab3_markerview);
        marker.setChartView(barChart);
        barChart.setMarker(marker);
//        System.out.println("128: "+getTime(today)+" "+elapsedTime());

        // 버튼 텍스트 변경
        final Button button1=(Button) view.findViewById(R.id.button1);
        if(getBool()){
            button1.setText("Pause");
        }
        else{
            button1.setText("Start");
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getBool()) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - getTime("pauseT"));
                    chronometer.start();
                    chronometer2.setBase(SystemClock.elapsedRealtime() - getTime("pauseT") - totalOffset());
                    chronometer2.start();
                    saveTime("startT", chronometer.getBase());
                    saveBool(true);
                    button1.setText("Pause");

//            Toast toast = Toast.makeText(this, "start "+getBool()+" "+elapsedTime()+ " "+getTime("startT"), Toast.LENGTH_SHORT);
//            toast.show();


                } else {
                    chronometer.stop();
                    chronometer2.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    saveTime("pauseT", pauseOffset);
//            totalT+=elapsedTime();
                    saveBool(false);
                    button1.setText("Start");
//            Toast toast = Toast.makeText(this, "pauseclick "+getBool()+" "+elapsedTime(), Toast.LENGTH_SHORT);
//            toast.show();
                }
            }
        });

        //Edit function
        Button button2=(Button) view.findViewById(R.id.button1);

        pickDate(view);

//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("ff");
//                pickDate();
//            }
//        });




        return view;
    }

        @Override
        public void onPause() {
            super.onPause();
            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
//        System.out.println(df.format(new Date()));
            String today=df.format(new Date());
            saveTime(today,getTime("pauseT"));
            saveTime("totalT", getTime(today)+totalOffset());
            if(getBool()) { //돌아가고 있었으면 시간 기록함.
                saveTime("pauseT", elapsedTime());
//            totalT+=elapsedTime();
            }

//        System.out.println("pause"+getBool());
//        System.out.println(elapsedTime());
//        System.out.println(SystemClock.elapsedRealtime());
//        Toast toast = Toast.makeText(this, "pause"+getBool()+" "+elapsedTime(), Toast.LENGTH_SHORT);
//        toast.show();
        }

        @Override
        public void onResume() {
            super.onResume();
//        Boolean runBool=getBool();

            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
//        System.out.println(df.format(new Date()));
            String today=df.format(new Date());
            saveTime(today,getTime("pauseT"));
            //공유를 위해 총 시간 저장
            saveTime("totalT", getTime(today)+totalOffset());


            if(getBool()){ //현재까지 더해줘야 함.


                chronometer.setBase(getTime("startT"));
                chronometer2.setBase(getTime("startT")-totalOffset());
                chronometer.start();
                chronometer2.start();
            }else{
                chronometer.setBase(SystemClock.elapsedRealtime()-getTime("pauseT"));
                chronometer2.setBase(SystemClock.elapsedRealtime()-getTime("pauseT")-totalOffset());
            }
//        Toast toast = Toast.makeText(this, "resume "+getBool()+" "+elapsedTime()+ " " +getTime("startT")+ " "+SystemClock.elapsedRealtime(), Toast.LENGTH_SHORT);
//        toast.show();
        }

        public void buttonChronometer(View v) {
            if (!getBool()) {
                chronometer.setBase(SystemClock.elapsedRealtime() - getTime("pauseT"));
                chronometer.start();
                chronometer2.setBase(SystemClock.elapsedRealtime() - getTime("pauseT")-totalOffset());
                chronometer2.start();
                saveTime("startT", chronometer.getBase());
                saveBool(true);

//            Toast toast = Toast.makeText(this, "start "+getBool()+" "+elapsedTime()+ " "+getTime("startT"), Toast.LENGTH_SHORT);
//            toast.show();


            }else{
                chronometer.stop();
                chronometer2.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                saveTime("pauseT", pauseOffset);
//            totalT+=elapsedTime();
                saveBool(false);

//            Toast toast = Toast.makeText(this, "pauseclick "+getBool()+" "+elapsedTime(), Toast.LENGTH_SHORT);
//            toast.show();
            }

        }

//    public void resetChronometer(View v) {
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        pauseOffset = 0;
//    }

        public long elapsedTime() {
            long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
//        long elapsedMillis = SystemClock.elapsedRealtime() - getTime("startT");
            return elapsedMillis;
        }

        //sharedpreference

        public void saveTime(String day, long codeTime) {
            SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putLong(day, codeTime);
            editor.commit();
        }

        public long getTime(String day) {
            SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
            return pref.getLong(day, 0);
        }

        private void saveBool(Boolean bool) {
            SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("bool", bool);
            editor.commit();
        }

        private boolean getBool() {
            SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
            return pref.getBoolean("bool", false);
        }

        private long totalOffset() {
            long result = 0;
            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
            for (String i : dateList) {
                if (i.equals(df.format(new Date()))) {
                    return result;
                }
                System.out.println("ff "+result);
                result += getTime(i);

            }
            return result;
        }

        private String[] getLabel(String date){
            String[] a=new String[7];
            try {
                int index=0;
                String day1 = getDateDay(date, "yyyy.MM.dd");
                for (int i=0;i<7;i++){
                    if(dayList[i].equals(day1)){
                        System.out.println("269");
                        index=i;
                        break;
                    }
                }
                index++;
                System.out.println("index"+index);
                for(int j=index;j<index+7;j++){
                    a[j-index]=dayList[j];
//                System.out.println(j);
                }
                return a;
            }
            catch (Exception e){
                System.out.println("parse err"+e);
            }

            return a;
        }

        private ArrayList<BarEntry> getChartData(String date){
            ArrayList<BarEntry> a=new ArrayList<>();
            int index=0;
            for (int i=0;i<dateList.length;i++){
                if(date.equals(dateList[i])) {
                    index = i;
                    break;
                }
            }
            index-=6;
            for (int j=0;j<7;j++){
                System.out.println(index);
                if(index<0){
                    a.add(new BarEntry((float)j, 0f));
                }
                else {
//            System.out.println(getTime(dateList[index]));
                    a.add(new BarEntry((float)j, (float)getTime(dateList[index])));
                }
//            System.out.println(dateList[index]);
                index++;
            }
//        System.out.println(a);
            return a;
        }

        public String getDateDay(String date, String dateType) throws Exception {

            String day = "";

            SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
            Date nDate = dateFormat.parse(date);

            Calendar cal = Calendar.getInstance();
            cal.setTime(nDate);

            int dayNum = cal.get(Calendar.DAY_OF_WEEK);

            switch (dayNum) {
                case 1:
                    day = "일";
                    break;
                case 2:
                    day = "월";
                    break;
                case 3:
                    day = "화";
                    break;
                case 4:
                    day = "수";
                    break;
                case 5:
                    day = "목";
                    break;
                case 6:
                    day = "금";
                    break;
                case 7:
                    day = "토";
                    break;

            }
            System.out.println("day"+ day);
            return day;
        }

    private void pickDate(View view) {

        //Calendar를 이용하여 년, 월, 일, 시간, 분을 PICKER에 넣어준다.
        final Calendar cal = Calendar.getInstance();

        Log.e(TAG, cal.get(Calendar.YEAR) + "");
        Log.e(TAG, cal.get(Calendar.MONTH) + 1 + "");
        Log.e(TAG, cal.get(Calendar.DATE) + "");
        Log.e(TAG, cal.get(Calendar.HOUR_OF_DAY) + "");
        Log.e(TAG, cal.get(Calendar.MINUTE) + "");

        //DATE PICKER DIALOG
        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d 년 %d 월 %d 일", year, month + 1, date);
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        //여기에 추가적으로 작성

                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));



//                Date minDate=new Date();
//                Calendar minCalendar=Calendar.getInstance();
//                minCalendar.set(2019,12,26);
//                minDate=minCalendar.getTime();
                Calendar minCalender=Calendar.getInstance();
                minCalender.set(2019,11,26);

                Calendar maxCalender=Calendar.getInstance();
                maxCalender.set(2020,0,22);

                System.out.println(SystemClock.elapsedRealtime());
                dialog.getDatePicker().setMinDate(minCalender.getTimeInMillis());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.getDatePicker().setMaxDate(maxCalender.getTimeInMillis());    //입력한 날짜 이후로 클릭 안되게 옵션


                dialog.show();

            }
        });
    }




    }


