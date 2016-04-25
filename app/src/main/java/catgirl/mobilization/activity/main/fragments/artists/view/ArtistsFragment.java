package catgirl.mobilization.activity.main.fragments.artists.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CustomCollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import catgirl.mobilization.activity.main.MainActivityModule;
import catgirl.mobilization.activity.main.fragments.artists.ArtistsComponent;
import catgirl.mobilization.activity.main.fragments.artists.ArtistsModule;
import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.view.FilteredArtistsFragment;
import catgirl.mobilization.activity.main.fragments.artists.presenter.ArtistsPresenter;
import catgirl.mobilization.application.Application;
import catgirl.mvp.implementations.BasePresenterFragment;
import ru.mapsvision.mobilization.R;
import catgirl.mobilization.activity.NavigationDrawerActivity;

public class ArtistsFragment extends BasePresenterFragment<ArtistsPresenter, ArtistsComponent> implements ArtistsView {

    @Bind(R.id.Toolbar) Toolbar toolbar;
    @Bind(R.id.CollapsingToolbar) CustomCollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.ToolbarCenter) View toolbarCenter;

    @Bind(R.id.ViewPager) ViewPager viewPager;
    @Bind(R.id.TabLayout) TabLayout tabLayout;
    @Bind(R.id.ErrorLayout) View errorLayout;
    @Bind(R.id.Loading) View loading;
    @Bind(R.id.ReloadButton) Button reload;

    List<Fragment> fragments = new ArrayList<>();
    List<String> names = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ArtistsComponent createComponent() {
        return Application.getApplicationComponent().plus(new MainActivityModule()).plus(new ArtistsModule());
    }

    @Override
    protected void onComponentCreated() {
        getComponent().inject(this);
    }

    @Override
    protected ArtistsPresenter createPresenter() {
        return getComponent().createPresenter();
    }

    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getView(inflater, container);
        ButterKnife.bind(this, view);

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();

            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                params.height += getResources().getDimensionPixelSize(resourceId);
                collapsingToolbarLayout.setLayoutParams(params);
                toolbarCenter.setPadding(
                        toolbarCenter.getPaddingLeft(),
                        toolbarCenter.getPaddingTop()
                                + getResources().getDimensionPixelSize(resourceId),
                        toolbarCenter.getPaddingRight(),
                        toolbarCenter.getPaddingBottom());
            }
        }

        reload.setOnClickListener(button -> {
            getPresenter().reloadPressed();
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((NavigationDrawerActivity) getActivity()).setToolbar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.artists);
    }

    @Override
    public void showTabs(List<String> tabs) {
        loading.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);

        names = new ArrayList<>(tabs);

        fragments = new ArrayList<>();

        for(String tag : names) {
            FilteredArtistsFragment fragment = new FilteredArtistsFragment();
            Bundle arguments = new Bundle();
            arguments.putString(FilteredArtistsFragment.FILTER_TAG, tag);
            fragment.setArguments(arguments);
            fragments.add(fragment);
        }

        names.add(0, getString(R.string.all_artists));
        FilteredArtistsFragment fragment = new FilteredArtistsFragment();
        fragments.add(0, fragment);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showError() {
        loading.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
    }
}
