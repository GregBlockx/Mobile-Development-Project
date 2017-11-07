package com.blockx.greg.becomerich.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blockx.greg.becomerich.Fragments.EducationFragment;
import com.blockx.greg.becomerich.Fragments.MainFragment;
import com.blockx.greg.becomerich.Fragments.MarketFragment;
import com.blockx.greg.becomerich.Fragments.WorkFragment;
import com.blockx.greg.becomerich.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.mipmap.user_icon,
            R.mipmap.work_icon,
            R.mipmap.market_icon,
            R.mipmap.education_icon};
    public static final String GAME_PREFERENCES = "GamePrefs";
    private InterstitialAd mInterstitialAd;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int adCounter;
    private ActionBar actionBar;
    private Boolean exit = false;
    private String[] tabsTitles = {"Become Rich","Work","Market","Education"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        actionBar = getSupportActionBar();

        //Roept sharedpreferences aan, haalt waardes eruit en steekt deze in lokale variabelen
        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //Intialize ad
        adCounter = sharedPreferences.getInt("adcounter", 0);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Intent startDoWorkActivity = new Intent(MainActivity.this, DoWorkActivity.class);
                startActivity(startDoWorkActivity);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
            }
        });

        //Set app title
        String position = sharedPreferences.getString("tab_opened", null);
        if (position == null) {
            viewPager.setCurrentItem(0, true);
            getSupportActionBar().setTitle(tabsTitles[0]);
        } else if (position.equals("0")) {
            viewPager.setCurrentItem(0, true);
            getSupportActionBar().setTitle(tabsTitles[0]);
        } else if (position.equals("1")) {
            viewPager.setCurrentItem(1, true);
            getSupportActionBar().setTitle(tabsTitles[1]);
        } else if (position.equals("2")) {
            viewPager.setCurrentItem(2, true);
            getSupportActionBar().setTitle(tabsTitles[2]);
        } else if (position.equals("3")) {
            viewPager.setCurrentItem(3, true);
            getSupportActionBar().setTitle(tabsTitles[3]);
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub

                editor.putString("tab_opened", "" + position);
                editor.commit();
                actionBar.setTitle(tabsTitles[position]);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int pos) {
                // TODO Auto-generated method stub

            }
        });


    }

    //Set tab icons
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);


        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            Intent info = new Intent(this, InfoActivity.class);
            startActivity(info);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MainFragment(), "Main");
        adapter.addFrag(new WorkFragment(), "Work");
        adapter.addFrag(new MarketFragment(), "Market");
        adapter.addFrag(new EducationFragment(), "Education");
        viewPager.setAdapter(adapter);
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
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return null;
        }
    }

    public void goToHunger(View view) {
        editor.putString("tab_opened", "0");
        editor.commit();
        Intent startHungerActivity = new Intent(this, HungerActivity.class);
        startActivity(startHungerActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goToHealth(View view) {
        editor.putString("tab_opened", "0");
        editor.commit();
        Intent startHealthActivity = new Intent(this, HealthActivity.class);
        startActivity(startHealthActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goToDoWork(View view) {
        editor.putString("tab_opened", "1");
        editor.commit();
        Intent startDoWorkActivity = new Intent(this, DoWorkActivity.class);

        if (mInterstitialAd.isLoaded() && adCounter >= 5) {
            mInterstitialAd.show();
            adCounter = 0;
            editor.putInt("adcounter", adCounter);
            editor.commit();
        } else {
            adCounter++;
            editor.putInt("adcounter", adCounter);
            editor.commit();
            startActivity(startDoWorkActivity);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    public void goToDoCriminalActivity(View view) {
        editor.putString("tab_opened", "1");
        editor.commit();
        Intent startDoCriminalJobActivity = new Intent(this, DoCriminalJobActivity.class);
        startActivity(startDoCriminalJobActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goToBank(View view) {
        editor.putString("tab_opened", "1");
        editor.commit();
        Intent startGoToBankActivity = new Intent(this, BankActivity.class);
        startActivity(startGoToBankActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goToChooseResidency(View view) {
        editor.putString("tab_opened", "2");
        editor.commit();
        Intent startChooseResidencyActivity = new Intent(this, ChooseResidencyActivity.class);
        startActivity(startChooseResidencyActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goToChooseTransport(View view) {
        editor.putString("tab_opened", "2");
        editor.commit();
        Intent startChooseTransportActivity = new Intent(this, ChooseTransportActivity.class);
        startActivity(startChooseTransportActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goToChooseWeapon(View view) {
        editor.putString("tab_opened", "2");
        editor.commit();
        Intent startChooseWeaponActivity = new Intent(this, ChooseWeaponActivity.class);
        startActivity(startChooseWeaponActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goToChooseEducation(View view) {
        editor.putString("tab_opened", "3");
        editor.commit();
        Intent startChooseEducationActivity = new Intent(this, ChooseEducationActivity.class);
        startActivity(startChooseEducationActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goToChooseCriminalSkills(View view) {
        editor.putString("tab_opened", "3");
        editor.commit();
        Intent startChooseCriminalSkillActivity = new Intent(this, ChooseCriminalSkillsActivity.class);
        startActivity(startChooseCriminalSkillActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

}
