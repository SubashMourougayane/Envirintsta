package bluepanther.envirinsta.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

import bluepanther.envirinsta.R;

/**
 * Created by hhs on 22/2/17.
 */

public class Profile_new extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    final int[] tabIcons = {
            R.drawable.ic_action_group_sv,
            R.drawable.ic_action_indi_sv
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_new);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        System.out.println("In notif class tabs");
        adapter.addFragment(new Progroup_tab(), "Group");
        adapter.addFragment(new Proindi_tab(), "Personal");

        viewPager.setAdapter(adapter);
        System.out.println("Tabset");
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_group_sv);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_indi_sv);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);

        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
