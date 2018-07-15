package com.imobilenetid.helloj_oxy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity implements BerandaFragment.SendMessage{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    //public static final String EXTRA_OPEN_PAGE = "com.imobilenetid.helloj_oxy.PAGE";

    //public static final int PAGE_BERANDA = 0;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public static ViewPager mViewPager;

    //private int mPageOpen;

    //firebase auth object
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        p.setScrollFlags(0);
        toolbar.setLayoutParams(p);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        //tablayout addtab able to add title in tablayout xml add tabitem in design view will produce the same tab title
        tabLayout.addTab(tabLayout.newTab().setText("Beranda"));
        tabLayout.addTab(tabLayout.newTab().setText("Pesanan"));
        tabLayout.addTab(tabLayout.newTab().setText("Pengiriman"));


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_pesan) {

            return true;
        }
        /*
        Log-out menu
         */
        if (id == R.id.action_logout) {

            /*
            Clear user data on shared-preference
             */
            AppSharedPreferences.logOutCurrentUser(this);

            /*
            Sign-out from Firebase authentication
             */
            //FirebaseAuth.getInstance().signOut();
            firebaseAuth.signOut();

            /*
            Go back to sign-in activity
             */
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter( FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem (int position) {
            switch (position){
                case 0 :
                    return new BerandaFragment();
                case 1 :
                    return new PesananFragment();
                case 2 :
                    return new PengirimanFragment();
                case 3 :
                    return new NotifikasiFragment();
                default :
                    return null;
            }
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle (int position) {
            switch (position){
                case 0 :
                    return "Beranda";
                case 1 :
                    return "Pesanan";
                case 2 :
                    return "Pengiriman";
                case 3 :
                    return "Notifikasi";
            }
            return null;
        }
    }


    @Override
    public PesananFragment sendData(String message, String messageRO) {
        String tag = "android:switcher:" + R.id.container + ":" + 1;
        PesananFragment f = (PesananFragment) getSupportFragmentManager().findFragmentByTag(tag);

        f.displayReceivedData(message, messageRO);
        //mViewPager.setCurrentItem(1, true);
        return new PesananFragment();

    }


}
