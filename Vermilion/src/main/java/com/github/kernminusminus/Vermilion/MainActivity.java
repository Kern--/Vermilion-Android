package com.github.kernminusminus.Vermilion;

import java.util.Locale;

import android.graphics.drawable.ColorDrawable;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.kernminusminus.Vermilion.model.ColorModel;
import com.github.kernminusminus.Vermilion.view.SimpleColorListenerView;
import com.larswerkman.holocolorpicker.ColorPicker;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.

            ColorModel.Colors curColor = ColorModel.Colors.get(i);

            View custom = inflater.inflate(R.layout.tab_background, null);

            SimpleColorListenerView colorView = (SimpleColorListenerView)custom.findViewById(R.id.tab_highlight);
            colorView.listenForColorChange(curColor.name);

            TextView titleView = (TextView)custom.findViewById(R.id.tab_title);
            titleView.setText(String.valueOf(curColor.name.charAt(0)));

            actionBar.addTab(
                    actionBar.newTab()
                            .setCustomView(custom)
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());

        tab.getCustomView().findViewById(R.id.tab_highlight).setVisibility(View.VISIBLE);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        tab.getCustomView().findViewById(R.id.tab_highlight).setVisibility(View.GONE);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(ColorModel.Colors.get(position).name, ColorModel.Colors.get(position).index);
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            return ColorModel.Colors.get(position).name;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements ColorPicker.OnColorChangedListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_COLOR = "color";

        private final ColorModel mModel;
        private String mColor;


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(String color, int index) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_COLOR, color);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
            mModel = ColorModel.getInstance();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            ColorPicker picker = (ColorPicker)rootView.findViewById(R.id.picker);
            picker.setShowOldCenterColor(false);
            picker.setOnColorChangedListener(this);

            mColor = getArguments().getString(ARG_SECTION_COLOR);
            TextView prompt = (TextView)rootView.findViewById(R.id.prompt);
            prompt.setText(getString(R.string.prompt_choose_base_color) + " " + mColor.toLowerCase());

            return rootView;
        }

        @Override
        public void onColorChanged(int color) {
            TextView prompt = (TextView)getView().findViewById(R.id.prompt);

            String oldText = prompt.getText().toString();

            Spannable coloredText = new SpannableString(oldText);
            coloredText.setSpan(new ForegroundColorSpan(color), getString(R.string.prompt_choose_base_color).length() + 1, oldText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            prompt.setText(coloredText);

            mModel.setBaseColor(mColor, color);
        }
    }

}
