package catgirl.mobilization.activity.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.mapsvision.mobilization.R;

public abstract class TabbedFragment extends Fragment {

    @Bind(R.id.ViewPager) ViewPager viewPager;
    @Bind(R.id.TabLayout) TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void getFragmentList(List<Fragment> fragments, List<String> names);
    public abstract View getView(LayoutInflater inflater, ViewGroup container);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getView(inflater, container);
        ButterKnife.bind(this, view);

        List<Fragment> fragments = new ArrayList<>();
        List<String> names = new ArrayList<>();

        getFragmentList(fragments, names);

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return names.get(position);
            }
        });

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
