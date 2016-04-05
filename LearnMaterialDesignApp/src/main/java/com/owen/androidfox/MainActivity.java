package com.owen.androidfox;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Map;
import java.util.WeakHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingLayout;
    @Bind(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.navigationview)
    NavigationView mNavigationView;
    ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        //使用Toolbar
        setSupportActionBar(mToolbar);
        //ActionBarDrawerToggle配合Toolbar，实现Toolbar上菜单按钮开关效果
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setNavigationViewItemClickListener();

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragmentByTag(String.valueOf(position));
            }

            @Override
            public int getCount() {
                return 6;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "xiao";
                    case 1:
                        return "li";
                    case 2:
                        return "fei";
                    case 3:
                        return "dao";
                    case 4:
                        return "owen";
                    case 5:
                        return "learn";
                    default:
                        return "xiao";
                }
            }
        });
        //最简单的绑定TabLayout和ViewPager的方法
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setNavigationViewItemClickListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        mCollapsingLayout.setTitle("Owenplus's Home");
                        break;
                    case R.id.navigation_item_github:
                        mCollapsingLayout.setTitle("Owenplus's Github");
                        break;
                    case R.id.navigation_item_about:
                        mCollapsingLayout.setTitle("关于");
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    @OnClick(R.id.fab_test)
    void popupSnackbar(){
        Snackbar.make(mCoordinatorLayout,"i am snackbar", Snackbar.LENGTH_SHORT)
                .setAction("click me", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"i'am toast", Toast.LENGTH_SHORT).show();
                    }
                })
                .setActionTextColor(Color.CYAN)
                .show();
    }

    private Map<String, Fragment> mFragmentMap = new WeakHashMap<>();
    private Fragment getFragmentByTag(@NonNull String tag) {
        Fragment fragment = null;
        if (mFragmentMap.containsKey(tag)) {
            fragment = mFragmentMap.get(tag);
        } else {
            fragment = MainFragment.newInstance();
            mFragmentMap.put(tag, fragment);
        }

        return fragment;
    }
}
