package com.example.tabhostgogo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

class CodingItem {
    String day;
    long ctime;
    String cTimeString;


    public CodingItem(String day,long ctime) {
        this.day=day;
        this.ctime=ctime;
        this.cTimeString=this.millToTime(ctime);
    }

    public String getDay() {
        return day;
    }
    public String getCodingTime() {
        return cTimeString;
    }

    public static String millToTime(long msec){
        final long daySec=24*60*60*1000;
        final long hourSec=60*60*1000;
        final long minSec=60*1000;
        final long secSec=1000;

        //long day=msec/daySec;
        long hour=(msec%daySec)/hourSec;
        long min=((msec%daySec)%hourSec)/minSec;
        long sec=(((msec%daySec)%hourSec)%minSec)/secSec;

        //return day+":"+hour+":"+min+":"+sec;
        return String.format("%02d:%02d:%02d",hour,min,sec);
    }
}

public class Tab3PageFragment3 extends Fragment {

    public BarChart barChart;
    Tab3PageFragment2 f2=new Tab3PageFragment2();
    private String[] dayString;
    private ArrayList<BarEntry> chartTime=new ArrayList<>();

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        LayoutInflater lf=getActivity().getLayoutInflater();
        View view= lf.inflate(R.layout.tab3_fragment3, null);


        ArrayList<CodingItem> timeRecycler=new ArrayList<>();
        CodingItem item1=new CodingItem("2019.12.26",(long)100000000);
        CodingItem item2=new CodingItem("2019.12.27",(long)698000000);
        CodingItem item3=new CodingItem("2019.12.28",(long)634000000);
        CodingItem item4=new CodingItem("2019.12.29",(long)200000000);
        CodingItem item5=new CodingItem("2019.12.30",(long)880000000);
        CodingItem item6=new CodingItem("2019.12.31",(long)300000000);
        CodingItem item7=new CodingItem("2020.01.01",(long)240000000);
        CodingItem item8=new CodingItem("2020.01.02",(long)400000000);
        CodingItem item9=new CodingItem("2020.01.03",(long)500000000);
        CodingItem item10=new CodingItem("2020.01.04",(long)600000000);
        timeRecycler.add(item1);
        timeRecycler.add(item2);
        timeRecycler.add(item3);
        timeRecycler.add(item4);
        timeRecycler.add(item5);
        timeRecycler.add(item6);
        timeRecycler.add(item7);
        timeRecycler.add(item8);
        timeRecycler.add(item9);
        timeRecycler.add(item10);

        RecyclerView tab3recyclerView=view.findViewById(R.id.tab3_recycler);
        tab3recyclerView.addItemDecoration(new DividerItemDecoration(tab3recyclerView.getContext(),1));
        tab3recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Tab3Page3TextAdapter tab3Adapter=new Tab3Page3TextAdapter(timeRecycler);
        tab3recyclerView.setAdapter(tab3Adapter);

        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        String today=df.format(new Date());
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

        pickDate(view);

        return view;
    }
    private ArrayList<BarEntry> getChartData(String date){
        ArrayList<BarEntry> a=new ArrayList<>();
        int index=0;
        for (int i=0;i<f2.dateList.length;i++){
            if(date.equals(f2.dateList[i])) {
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
                a.add(new BarEntry((float)j, (float) getTime(f2.dateList[index])));
            }
//            System.out.println(dateList[index]);
            index++;
        }
//        System.out.println(a);
        return a;
    }

    private String[] getLabel(String date){
        String[] a=new String[7];
        try {
            int index=0;
            String day1 = getDateDay(date, "yyyy.MM.dd");
            for (int i=0;i<7;i++){
                if(f2.dayList[i].equals(day1)){
                    System.out.println("269");
                    index=i;
                    break;
                }
            }
            index++;
            System.out.println("index"+index);
            for(int j=index;j<index+7;j++){
                a[j-index]=f2.dayList[j];
//                System.out.println(j);
            }
            return a;
        }
        catch (Exception e){
            System.out.println("parse err"+e);
        }

        return a;
    }
    public long getTime(String day) {
        SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getLong(day, 0);
    }
    public void saveTime(String day, long codeTime) {
        SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(day, codeTime);
        editor.commit();
    }
    private long totalOffset() {
        long result = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        for (String i : f2.dateList) {
            if (i.equals(df.format(new Date()))) {
                return result;
            }
            System.out.println("ff "+result);
            result += getTime(i);

        }
        return result;
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

                final DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        LayoutInflater factory = LayoutInflater.from(getContext());
                        final String msg = String.format("%d.%02d.%02d", year, month + 1, date);

                        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                        alert.setTitle("몰입 시간");
                        alert.setMessage("코딩 얼마나 했는지 적으라구~");

                        LinearLayout layout=new LinearLayout(getContext());
                        layout.setOrientation(LinearLayout.HORIZONTAL);

                        final EditText hourInput = new EditText(getContext());
                        hourInput.setHint("Hour");
                        layout.addView(hourInput);

                        final EditText minuteInput = new EditText(getContext());
                        minuteInput.setHint("Minute");
                        layout.addView(minuteInput);

                        alert.setView(layout);

//                        final EditText hourInput = new EditText(getContext());
//                        alert.setView(hourInput);
//
//                        final EditText minInput = new EditText(getContext());
//                        alert.setView(minInput);

//                        int hour=0;
//                        int min=0;
                        final Tab3PageFragment3 t3=new Tab3PageFragment3();

                        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//                                Toast.makeText(getContext(), "간을 입력해주세요", Toast.LENGTH_SHORT).show();
                                int hour=0;
                                int min=0;
                                try {
                                    hour = Integer.parseInt(hourInput.getText().toString());
                                    min = Integer.parseInt(minuteInput.getText().toString());
                                    //예외처리해줘야함 범위
                                }
                                catch (NumberFormatException e){
                                    Toast.makeText(getContext(), "맞는 시간을 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                                Long fixHour= (long) hour*1000*60*60 + (long) min*1000*60;

                                saveTime(msg, fixHour);

                                //오늘날짜라면
                                //total update
                                SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
                                String today=df.format(new Date());


                                saveTime("totalT", getTime(today)+totalOffset());
//                        chronometer.setBase();
                                System.out.println(msg);
                                System.out.println(today);
                                System.out.println("RR");
                                if(msg.equals(today)){
                                    System.out.println("들어옴");
                                    saveTime("pauseT", SystemClock.elapsedRealtime() - fixHour);
                                }

                                f2.chronometer.setBase(SystemClock.elapsedRealtime()-getTime("pauseT"));
                                f2.chronometer2.setBase(SystemClock.elapsedRealtime()-getTime("pauseT")-totalOffset());

                                t3.barChart.notifyDataSetChanged();
                                t3.barChart.invalidate();
                            }
                        });
                        alert.setNegativeButton("no",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });

                        alert.show();
                        
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