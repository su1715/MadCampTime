package com.example.tabhostgogo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Tab3PagerAdapter extends FragmentStatePagerAdapter {

    public Tab3PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // 해당하는 page의 Fragment를 생성합니다.
//        if()
//        return Tab3PageFragment.create(position);
        switch (position) {
            case 0:
                Tab3PageFragment1 Fragment1 = new Tab3PageFragment1();
                return Fragment1;
            case 1:
                Tab3PageFragment2 Fragment2 = new Tab3PageFragment2();
                return Fragment2;
//            case 2:
//                TabFragment3 Fragment3=new TabFragment3();
//                return Fragment3;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 2;  // 총 5개의 page를 보여줍니다.
    }

}
