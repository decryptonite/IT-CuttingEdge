package com.dpsn.espice.itcuttingedge;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dpsn.espice.itcuttingedge.DeCryptonite.LoginActivity;


public class HomeActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;

    public final String DECRYPTONITE = "DeCryptonite v4.0";
    public final String HOME = "Home";
    public final String EVENTS = "Events";
    public final String ABOUT = "About";

    private String[] drawerContents;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.navigation_drawer_activity);

        drawerContents = new String[]{
                DECRYPTONITE,
                HOME,
                EVENTS,
                ABOUT
        };

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_drawer_item, drawerContents));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        )  {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);

        CreateCustomActionBar(this.getActionBar());

        //Set Dashboard Screen to come when user logs in
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerList.setItemChecked(0, true);
        setTitle("Dashboard");
    }

    private void CreateCustomActionBar(ActionBar bar) {
        int color = getResources().getColor(R.color.actionbar_color);
        String sColor = "#" + Integer.toHexString(color);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sColor)));
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText(getResources().getString(R.string.app_name));

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/e.otf");
        mTitleTextView.setTypeface(custom_font, Typeface.BOLD);
        mTitleTextView.setTextSize(35);

        bar.setCustomView(mCustomView);
        bar.setDisplayShowCustomEnabled(true);

        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM |
                ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.decryptonite_menu) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
               menu.findItem(R.id.decryptonite_menu).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        this.getActionBar().setTitle(mTitle);
    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }

        /** Swaps fragments in the main content view */
        public void selectItem(int position) {
            android.app.Fragment fragment;
            android.app.FragmentManager fragmentManager =  getFragmentManager();
            switch (drawerContents[position]){
                case HOME:
                    fragment = new HomeFragment();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment)
                            .commit();
                    break;
                case EVENTS:
                    fragment = new EventFragment();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment)
                            .commit();
                    break;
                case ABOUT:
                    fragment = new AboutFragment();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment)
                            .commit();
                    break;
                case DECRYPTONITE:
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    break;
            }
            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(drawerContents[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }
}

