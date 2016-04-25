package catgirl.mobilization.activity.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import catgirl.mvp.implementations.BaseComponentActivity;
import ru.mapsvision.mobilization.R;
import catgirl.mobilization.activity.NavigationDrawerActivity;
import catgirl.mobilization.activity.main.fragments.artists.view.ArtistsFragment;
import catgirl.mobilization.application.Application;
import catgirl.mobilization.application.Config;


public class MainActivity extends BaseComponentActivity<MainActivityComponent>
        implements
        FragmentManager.OnBackStackChangedListener,
        NavigationView.OnNavigationItemSelectedListener,
        NavigationDrawerActivity
{

    private static final String CURRENT_ITEM_KEY = "current-item";

    private ActionBarDrawerToggle mDrawerToggle;

    private MenuConfig menuConfig;
    private int currentMenuItemId = 0;

    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.MainActivity_NavigationView) NavigationView mNavigationView;
    @Bind(R.id.container) ViewGroup container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        getComponent().inject(this);

        menuConfig = new MenuConfig();

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        setUpNavigationMenu(savedInstanceState != null);

        if(savedInstanceState != null) {
            onBackStackChanged();
        }
    }

    @Override
    public MainActivityComponent createComponent() {
        return Application.getApplicationComponent().plus(new MainActivityModule());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(getIntent() != null && getIntent().getData() != null)
            onNewIntent(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_ITEM_KEY, currentMenuItemId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(mNavigationView))
            mDrawerLayout.closeDrawers();
        else if(currentMenuItemId != 0)
            super.onBackPressed();
        else
            finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item);
    }

    private void setUpNavigationMenu(boolean fromSavedInstance) {
        mNavigationView.setNavigationItemSelectedListener(this);

        final Menu menu = mNavigationView.getMenu();

        int groupId = 0;

        for(int i = 0; i < menuConfig.menuItems.size(); i++) {
            MenuConfigItem item = menuConfig.menuItems.get(i);
            if(item.type == MenuItemType.ITEM_SEPARATOR) {
                groupId++;
                continue;
            }
            MenuItem menuItem = menu.add(groupId, i, i, item.name);
            menuItem.setIcon(item.icon);
        }

        menu.setGroupCheckable(0, true, true);

        if(!fromSavedInstance)
            switchFragment(currentMenuItemId);
    }

    @Override
    public void onBackStackChanged() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return;
        }

        updateUiForFragment(
                Integer.parseInt(getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName())
        );
    }

    public void updateUiForFragment(int menuItemId) {
        MenuConfigItem item = menuConfig.menuItems.get(menuItemId);
        mNavigationView.getMenu().getItem(menuItemId).setChecked(true);
        currentMenuItemId = menuItemId;
    }

    public void switchFragment(int id) {
        MenuConfigItem item = menuConfig.menuItems.get(id);

        if(item.type == MenuItemType.ITEM_ABOUT) {
            try {
                Toast.makeText(
                        this,
                        String.format(getString(R.string.activity_main_about_text), getPackageManager().getPackageInfo(getPackageName(), 0).versionName),
                        Toast.LENGTH_LONG
                ).show();
                updateUiForFragment(currentMenuItemId);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return;
        }

        if(item.type == MenuItemType.ITEM_WEBSITE) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Config.website));
            startActivity(i);
            updateUiForFragment(currentMenuItemId);
            return;
        }

        updateUiForFragment(id);

        Class fragmentClass = null;

        switch(item.type) {
            case ITEM_ARTISTS:
                fragmentClass = ArtistsFragment.class;
                break;
            default:
                break;
        }

        if(fragmentClass != null)
            try {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(id));

                if (fragment == null) {
                    fragment = (Fragment) fragmentClass.newInstance();
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(
                                R.id.container,
                                fragment,
                                String.valueOf(id))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(String.valueOf(id))
                        .commit();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if(currentMenuItemId == menuItem.getItemId())
            return true;

        mDrawerLayout.closeDrawers();

        switchFragment(menuItem.getItemId());

        return true;
    }

    public void selectMenuItem(MenuItemType type) {
        switchFragment(menuConfig.getId(type));
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setSupportActionBar(toolbar);
    }

    public enum MenuItemType {
        ITEM_ARTISTS,
        ITEM_ABOUT,
        ITEM_WEBSITE,
        ITEM_SEPARATOR
    }

    public static class MenuConfigItem {
        MenuItemType type;
        String name;
        int icon;

        public MenuConfigItem(MenuItemType type, String name, int icon) {
            this.type = type;
            this.name = name;
            this.icon = icon;
        }

        public static MenuConfigItem getSeparator() {
            return new MenuConfigItem(MenuItemType.ITEM_SEPARATOR, null, 0);
        }
    }

    class MenuConfig {
        List<MenuConfigItem> menuItems;

        public MenuConfig() {
            menuItems = new ArrayList<>();

            menuItems.add(new MenuConfigItem(MenuItemType.ITEM_ARTISTS, getString(R.string.artists), R.drawable.ic_list_white_24dp));
            menuItems.add(MenuConfigItem.getSeparator());
            menuItems.add(new MenuConfigItem(MenuItemType.ITEM_WEBSITE, getString(R.string.activity_main_website), R.drawable.ic_web_black_24dp));
            menuItems.add(new MenuConfigItem(MenuItemType.ITEM_ABOUT, getString(R.string.activity_main_about), R.drawable.ic_help_black_24dp));
        }

        public int getId(MenuItemType type) {
            int id = -1;
            for(int i = 0; i < menuItems.size(); i++)
                if(menuItems.get(i).type == type)
                    return i;
            return id;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        YandexMetrica.onResumeActivity(this);
    }

    @Override
    protected void onPause() {
        YandexMetrica.onPauseActivity(this);
        super.onPause();
    }
}
