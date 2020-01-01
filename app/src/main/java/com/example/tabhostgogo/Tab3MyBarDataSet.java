package com.example.tabhostgogo;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

public class Tab3MyBarDataSet extends BarDataSet {


    public Tab3MyBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {

        if(getEntryForIndex(index).getX() < 7200000) // less than 95 green
            return mColors.get(5);
        else if(getEntryForIndex(index).getX() <  7200000*2) // less than 100 orange
            return mColors.get(4);
        else if(getEntryForIndex(index).getX() <  7200000*3) // less than 100 orange
            return mColors.get(3);
        else if(getEntryForIndex(index).getX() <  7200000*4) // less than 100 orange
            return mColors.get(2);
        else if(getEntryForIndex(index).getX() <  7200000*5) // less than 100 orange
            return mColors.get(1);
        else // greater or equal than 100 red
            return mColors.get(0);
    }

//    @Override
//    public BarEntry getEntryForXValue(float xValue, float closestToY) {
//        return super.getEntryForXValue(xValue, closestToY);
//    }

}