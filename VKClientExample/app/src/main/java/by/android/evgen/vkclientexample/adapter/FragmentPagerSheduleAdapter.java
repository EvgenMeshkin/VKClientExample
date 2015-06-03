package by.android.evgen.vkclientexample.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


/**
 * Created by Yauheni_Meshkin on 5/26/2015.
 */
public class FragmentPagerSheduleAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mTabs;

    public FragmentPagerSheduleAdapter(FragmentManager fm, List<Fragment> mTabs) {
        super(fm);
        this.mTabs = mTabs;
    }

    @Override
    public Fragment getItem(int i) {
        return mTabs.get(i);
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "FRIENDS";
        } else return "DIALOGS";
    }

}


