package com.example.tabhostgogo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

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

    Tab3PageFragment2 f2=new Tab3PageFragment2();
    String [] datelist=f2.dateList;


    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        LayoutInflater lf=getActivity().getLayoutInflater();
        View view= lf.inflate(R.layout.tab3_fragment3, null);


        ArrayList<CodingItem> timeRecycler=new ArrayList<>();
        SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
//        System.out.println(pref.getLong(datelist[0],0));
        for(int i=0;i<datelist.length;i++){
            timeRecycler.add(new CodingItem(datelist[i], pref.getLong(datelist[i],0)));
        }

//        CodingItem item1=new CodingItem("2019.12.26",(long)100000000);
//        CodingItem item2=new CodingItem("2019.12.27",(long)698000000);
//        CodingItem item3=new CodingItem("2019.12.28",(long)634000000);
//        CodingItem item4=new CodingItem("2019.12.29",(long)200000000);
//        CodingItem item5=new CodingItem("2019.12.30",(long)880000000);
//        CodingItem item6=new CodingItem("2019.12.31",(long)300000000);
//        CodingItem item7=new CodingItem("2020.01.01",(long)240000000);
//        CodingItem item8=new CodingItem("2020.01.02",(long)400000000);
//        CodingItem item9=new CodingItem("2020.01.03",(long)500000000);
//        CodingItem item10=new CodingItem("2020.01.04",(long)600000000);
//        timeRecycler.add(item1);
//        timeRecycler.add(item2);
//        timeRecycler.add(item3);
//        timeRecycler.add(item4);
//        timeRecycler.add(item5);
//        timeRecycler.add(item6);
//        timeRecycler.add(item7);
//        timeRecycler.add(item8);
//        timeRecycler.add(item9);
//        timeRecycler.add(item10);

        RecyclerView tab3recyclerView=view.findViewById(R.id.tab3_recycler);
        tab3recyclerView.addItemDecoration(new DividerItemDecoration(tab3recyclerView.getContext(),1));
        tab3recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Tab3Page3TextAdapter tab3Adapter=new Tab3Page3TextAdapter(timeRecycler);
        tab3recyclerView.setAdapter(tab3Adapter);

        return view;
    }

}