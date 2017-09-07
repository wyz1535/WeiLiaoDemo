package com.leyifu.weiliaodemo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.leyifu.weiliaodemo.HotFragment;
import com.leyifu.weiliaodemo.NewsFragment;
import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.SuggestFragment;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends AppCompatActivity {

    TabLayout tab_layout;
    List<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;
    private FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        initView();
        init();
    }

    private void initView() {
        tab_layout = ((TabLayout) findViewById(R.id.tab_layout));
    }

    private void init() {
        tab_layout.setTabMode(tab_layout.MODE_SCROLLABLE);
        tab_layout.addTab(tab_layout.newTab().setText("推荐"), true);
        tab_layout.addTab(tab_layout.newTab().setText("热点"));
        tab_layout.addTab(tab_layout.newTab().setText("新闻"));
        tab_layout.addTab(tab_layout.newTab().setText("推荐"));
        tab_layout.addTab(tab_layout.newTab().setText("热点"));
        tab_layout.addTab(tab_layout.newTab().setText("新闻"));
        tab_layout.addTab(tab_layout.newTab().setText("推荐"));
        tab_layout.addTab(tab_layout.newTab().setText("热点"));
        tab_layout.addTab(tab_layout.newTab().setText("新闻"));


        fragments.add(new SuggestFragment());
        fragments.add(new HotFragment());
        fragments.add(new NewsFragment());

        supportFragmentManager = getSupportFragmentManager();
        if (fragments != null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_main, fragments.get(0)).commit();
            currentFragment = fragments.get(0);
        }

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                repleseFragment(fragments.get(tab.getPosition()));
                addOrShowFragment(supportFragmentManager, fragments.get(tab.getPosition() % 3));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void addOrShowFragment(FragmentManager supportFragmentManager, Fragment fragment) {
        if (currentFragment == fragment) {
            return;
        }

        if (!fragment.isAdded()) {
            supportFragmentManager.beginTransaction().hide(currentFragment).add(R.id.fragment_main, fragment).commit();
        } else {
            supportFragmentManager.beginTransaction().hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }

    private void repleseFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, fragment).commit();
    }
}
