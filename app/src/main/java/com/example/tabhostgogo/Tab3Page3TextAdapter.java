package com.example.tabhostgogo;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Tab3Page3TextAdapter extends RecyclerView.Adapter<Tab3Page3TextAdapter.ViewHolder> {
    private ArrayList<CodingItem> mData=null;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText ;
        TextView timeText ;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            dateText = itemView.findViewById(R.id.date) ;
            timeText = itemView.findViewById(R.id.time) ;

        }
    }
    Tab3Page3TextAdapter(ArrayList<CodingItem> list) {
        mData = list ;
    }
    @Override
    public Tab3Page3TextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.tab3_page3_recyclerview_item, parent, false) ;
        Tab3Page3TextAdapter.ViewHolder vhold = new Tab3Page3TextAdapter.ViewHolder(view) ;

        return vhold ;
    }

    @Override
    public void onBindViewHolder(Tab3Page3TextAdapter.ViewHolder holder, int position) {
        CodingItem r_item= mData.get(position) ;
        holder.dateText.setText(r_item.getDay()) ;
        holder.timeText.setText(r_item.getCodingTime());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) holder.itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //int deviceWidth = displayMetrics.widthPixels;  // 핸드폰의 가로 해상도를 구함.
        double deviceHeight = displayMetrics.heightPixels;  // 핸드폰의 세로 해상도를 구함.
        deviceHeight = deviceHeight / 8.8;
        //int deviceWidth = (int) (deviceHeight * 1.5);  // 세로의 길이를 가로의 길이의 1.5배로 하고 싶었다.
        holder.itemView.getLayoutParams().height = (int)deviceHeight;  // 아이템 뷰의 세로 길이를 구한 길이로 변경
        holder.itemView.requestLayout(); // 변경 사항 적용

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
