package com.example.tabhostgogo;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


public class Tab3PageFragment2 extends Fragment {
    long todayMsec;
    long wholeMsec;
    String smsText;
    MilltoTime timeConvert;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

    }

    public Chronometer chronometer;
    public Chronometer chronometer2;
    private long pauseOffset;

    public long totalT;
    //    private ArrayList<String> dayString=new ArrayList<>();

    public String[] dateList = new String[]{"2019.12.26", "2019.12.27", "2019.12.28", "2019.12.29", "2019.12.30", "2019.12.31", "2020.01.01", "2020.01.02",
            "2020.01.03", "2020.01.04", "2020.01.05", "2020.01.06", "2020.01.07", "2020.01.08", "2020.01.09", "2020.01.10", "2020.01.11", "2020.01.12",
            "2020.01.13", "2020.01.14", "2020.01.15", "2020.01.16", "2020.01.17", "2020.01.18", "2020.01.19", "2020.01.20", "2020.01.21", "2020.01.22"};
    public String[] dayList = new String[]{"일", "월", "화", "수", "목", "금", "토", "일", "월", "화", "수", "목", "금", "토"};

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab3_fragment2, null);

        chronometer = view.findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer2 = view.findViewById(R.id.chronometer2);
        chronometer2.setFormat("%s");
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        final String today = df.format(new Date());
        timeConvert = new MilltoTime();
        Button shareButton = (Button) view.findViewById(R.id.shareButton);
        shareButton.setText("SHARE");
        shareButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent? 이용해서 msec 받아오는 기능 구현할것
                //일단은 하드코딩


                todayMsec = getTime(today);
                wholeMsec = getTime("totalT");

                smsText = "총 몰입시간: " + timeConvert.millToDayTime(wholeMsec) + "\n" +
                        "오늘 몰입시간: " + timeConvert.millToTime(todayMsec);

                //dialog로 번호 정보 불러오기.
                //확인 버튼 누르면 문자가게 할것
                //일단은

                show();


            }

        });


        System.out.println("11" + getTime(today));
        saveTime(today, getTime("pauseT"));
//        System.out.println(getTime(day));
        for (int i = 0; i < dateList.length; i++) {
            System.out.println(getTime(dateList[i]));
        }
        System.out.println("83" + totalOffset());
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


        // 버튼 텍스트 변경
        final Button button1 = (Button) view.findViewById(R.id.button1);
        if (getBool()) {
            button1.setText("Pause");
        } else {
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
        Button button2 = (Button) view.findViewById(R.id.button1);


        pickDate(view);

//        saveTime(today,0);
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
        String today = df.format(new Date());
        saveTime(today, getTime("pauseT"));
        saveTime("totalT", getTime(today) + totalOffset());
        if (getBool()) { //돌아가고 있었으면 시간 기록함.
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
        String today = df.format(new Date());
        saveTime(today, getTime("pauseT"));
        //공유를 위해 총 시간 저장
        saveTime("totalT", getTime(today) + totalOffset());


        if (getBool()) { //현재까지 더해줘야 함.
            chronometer.setBase(getTime("startT"));
            chronometer2.setBase(getTime("startT") - totalOffset());
            chronometer.start();
            chronometer2.start();
        } else {
            chronometer.setBase(SystemClock.elapsedRealtime() - getTime("pauseT"));
            chronometer2.setBase(SystemClock.elapsedRealtime() - getTime("pauseT") - totalOffset());
        }
//        Toast toast = Toast.makeText(this, "resume "+getBool()+" "+elapsedTime()+ " " +getTime("startT")+ " "+SystemClock.elapsedRealtime(), Toast.LENGTH_SHORT);
//        toast.show();
    }

    public void buttonChronometer(View v) {
        if (!getBool()) {
            chronometer.setBase(SystemClock.elapsedRealtime() - getTime("pauseT"));
            chronometer.start();
            chronometer2.setBase(SystemClock.elapsedRealtime() - getTime("pauseT") - totalOffset());
            chronometer2.start();
            saveTime("startT", chronometer.getBase());
            saveBool(true);

//            Toast toast = Toast.makeText(this, "start "+getBool()+" "+elapsedTime()+ " "+getTime("startT"), Toast.LENGTH_SHORT);
//            toast.show();


        } else {
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
            System.out.println("ff " + result);
            result += getTime(i);

        }
        return result;
    }

    void show() {
        final List<String> listItems = new ArrayList<>();
        final ArrayList<PhoneBook> phone_books = Loader.getData(getContext());

        for (int i = 0; i < phone_books.size(); i++) {
            listItems.add(phone_books.get(i).getName());
        }

        final CharSequence[] items = listItems.toArray(new String[listItems.size()]);
        final List SelectedItems = new ArrayList();
        int defaultItem = 0;
        SelectedItems.add(defaultItem);



        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Send SMS");
        builder.setSingleChoiceItems(items, defaultItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectedItems.clear();
                        SelectedItems.add(which);
                    }
                });

        builder.setPositiveButton("Send",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (!SelectedItems.isEmpty()) {
                            int index = (int) SelectedItems.get(0);
                            String sendNumber = phone_books.get(index).getNumber();
                            final SmsManager sms = SmsManager.getDefault();


                            sms.sendTextMessage(sendNumber, null, smsText, null, null);

                        }
                        Toast.makeText(getContext(),
                                "Shared: \n" + smsText, Toast.LENGTH_LONG)
                                .show();

                    }
                });

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();

    }

    private void pickDate (View view){

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
//                        LayoutInflater factory = LayoutInflater.from(getContext());
                        final String msg = String.format("%d.%02d.%02d", year, month + 1, date);

                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("몰입 시간");
                        alert.setMessage("코딩 얼마나 했는지 적으라구~");

                        LinearLayout layout = new LinearLayout(getContext());
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                        layout.setGravity(Gravity.CENTER);

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
                        final Tab3PageFragment3 t3 = new Tab3PageFragment3();

                        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//                                Toast.makeText(getContext(), "간을 입력해주세요", Toast.LENGTH_SHORT).show();
                                int hour = 0;
                                int min = 0;
                                try {
                                    hour = Integer.parseInt(hourInput.getText().toString());
                                    min = Integer.parseInt(minuteInput.getText().toString());
                                    //예외처리해줘야함 범위
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getContext(), "맞는 시간을 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                                Long fixHour = (long) hour * 1000 * 60 * 60 + (long) min * 1000 * 60;

                                saveTime(msg, fixHour);

                                //오늘날짜라면
                                //total update
                                SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
                                String today = df.format(new Date());


                                saveTime("totalT", getTime(today) + totalOffset());
//                        chronometer.setBase();
                                System.out.println(msg);
                                System.out.println(today);
                                System.out.println("RR");
                                if (msg.equals(today)) {
                                    saveTime("fixT", fixHour);
                                    chronometer.setBase(SystemClock.elapsedRealtime() - getTime("fixT"));
                                    chronometer2.setBase(SystemClock.elapsedRealtime() - getTime("fixT") - totalOffset());
                                }
                                else {
                                    chronometer.setBase(SystemClock.elapsedRealtime() - getTime("pauseT"));
                                    chronometer2.setBase(SystemClock.elapsedRealtime() - getTime("pauseT") - totalOffset());
                                }
//                                t3.barChart.notifyDataSetChanged();
//                                t3.barChart.invalidate();
                            }
                        });
                        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
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
                Calendar minCalender = Calendar.getInstance();
                minCalender.set(2019, 11, 26);

                Calendar maxCalender = Calendar.getInstance();
                maxCalender.set(2020, 0, 22);

                System.out.println(SystemClock.elapsedRealtime());
                dialog.getDatePicker().setMinDate(minCalender.getTimeInMillis());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.getDatePicker().setMaxDate(maxCalender.getTimeInMillis());    //입력한 날짜 이후로 클릭 안되게 옵션


                dialog.show();

            }
        });
    }


}
class MilltoTime{
    public static String millToDayTime(long msec){
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
