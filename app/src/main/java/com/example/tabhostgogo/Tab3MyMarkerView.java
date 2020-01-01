package com.example.tabhostgogo;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

public class Tab3MyMarkerView extends MarkerView {

    private TextView tvContent;

    public Tab3MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView)findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {
            int hr, min;
            System.out.println(e.getY());
            hr=(int) e.getY()/60000/60;
            min= (int) (e.getY()-hr*60000*60)/60000;

            tvContent.setText(hr+ "시간 "+min+"분");

//            tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}