package be.thomasmore.brrr.common.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.thomasmore.brrr.Application;
import be.thomasmore.brrr.common.fragment.CityStreamFragment;
import be.thomasmore.brrr.common.fragment.DetailFragment;
import be.thomasmore.brrr.common.fragment.ExampleFragment;
import be.thomasmore.brrr.common.fragment.TitledFragment;
import be.thomasmore.brrr.data.model.Beacon;

public class TabPagerFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, TitledFragment> tabs;

    public TabPagerFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        initTabs(context);
    }

    private void initTabs(Context context) {
        tabs = new HashMap<>();

        for (Beacon beacon:Application.getBeaconList()) {
            int i = Integer.parseInt(beacon.getIds().getMinor());
//            tabs.put(i, DetailFragment.getInstance(context));
        }

        tabs.put(TabConstants.TAB_CITY_STREAM, DetailFragment.getInstance(context, 0));
        tabs.put(TabConstants.TAB_CITY_MAP, DetailFragment.getInstance(context, 1));
        tabs.put(TabConstants.TAB_FILTER, DetailFragment.getInstance(context, 2));
        tabs.put(TabConstants.TAB_FILTER1, DetailFragment.getInstance(context, 3));
        tabs.put(TabConstants.TAB_FILTER2, DetailFragment.getInstance(context, 4));
        tabs.put(TabConstants.TAB_FILTER3, DetailFragment.getInstance(context, 5));
        tabs.put(TabConstants.TAB_FILTER4, DetailFragment.getInstance(context, 6));
        tabs.put(TabConstants.TAB_FILTER5, DetailFragment.getInstance(context, 7));
        tabs.put(TabConstants.TAB_FILTER6, DetailFragment.getInstance(context, 8));
        tabs.put(TabConstants.TAB_FILTER7, DetailFragment.getInstance(context, 9));
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

}
