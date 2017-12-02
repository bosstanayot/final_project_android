package kmitl.project.bosstanayot.runranrun.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> MyFragment = new ArrayList<>();
    private List<String> MyPageTittle = new ArrayList<>();

    public MyViewPageAdapter(FragmentManager manager){
        super(manager);
    }

    public void AddFragmentPage(Fragment Frag, String Title){
        MyFragment.add(Frag);
        MyPageTittle.add(Title);
    }

    @Override
    public Fragment getItem(int position) {
        return MyFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MyPageTittle.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}