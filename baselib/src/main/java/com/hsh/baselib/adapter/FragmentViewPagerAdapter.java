package com.hsh.baselib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Benko on 9/22/15.
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {


    @Setter
    @Getter
    private List<Fragment> fragmentList;

    public FragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList == null ? null : fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }
}
