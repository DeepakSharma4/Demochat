package com.example.hp.demochat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by hp on 4/2/2017.
 */

public class myadpter extends FragmentStatePagerAdapter {
    private int tabcount;

    public myadpter(FragmentManager fm,int tabcount) {
        super(fm);
        this.tabcount=tabcount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                tabone tab1=new tabone();
                return tab1;
            case 1:
                tabsec tab2=new tabsec();
                return tab2;
            case 2:
                tabthird tab3=new tabthird();
                return tab3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabcount;
    }
}

