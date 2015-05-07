package com.dpsn.espice.itcuttingedge.DeCryptonite;

/**
 * Created by Sony on 02-05-2015.
 */


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dpsn.espice.itcuttingedge.DeCryptonite.ConnectWithSite.LoginConnection;
import com.dpsn.espice.itcuttingedge.HomeActivity;
import com.dpsn.espice.itcuttingedge.R;

/*
 * File: LoginActivity.jave
 * ==============================
 * This class handles the login event. When the user clicks the submit button,
 * a query is send to http://preayus.net76.net/mob/login.php?username=USERNAME$pass=PASSWORD
 * If the user had entered correct Username and Password he is directed to NavigationDrawerActivity.
 * Else if the user has been disqualified, he is directed to DisqualifyActivity.
 */

public class LoginActivity extends ActionBarActivity implements OnClickListener  {

    private EditText usernameEditText;
    private EditText passwordEditText;
    public static Button loginButton;

    public static final String LogTag = "Debug";

    public static String mUsername;
    public static String mPassword;

    private LoginConnection loginConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        CreateCustomActionBar(getSupportActionBar());

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginConnection = new LoginConnection(this);

        loginButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_decryptonite_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itcuttingedge) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Creates a custom action bar with ActionBar text centered, font economica
     * and size 35sp. Also a Info icon is placed on the right.
     */
    public void CreateCustomActionBar(android.support.v7.app.ActionBar bar) {
        bar = getSupportActionBar();
        int color = getResources().getColor(R.color.actionbar_color);
        String sColor = "#" + Integer.toHexString(color);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sColor)));
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText(getResources().getString(R.string.decryptonite));

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/e.otf");
        mTitleTextView.setTypeface(custom_font, Typeface.BOLD);
        mTitleTextView.setTextSize(35);

        bar.setCustomView(mCustomView);
        bar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginButton){
            //Get the values entered by the user
            String givenUsername = usernameEditText.getText().toString();
            String givenPassword = passwordEditText.getText().toString();
            if (givenUsername.equals("") || givenPassword.equals("")){
                Toast.makeText(this, "Please fill all the credentials", Toast.LENGTH_SHORT).show();
            }
            else {
                Log.v(LogTag,
                        "Given Username is :" + givenUsername + " Given password is :" + givenPassword);
                //Pass these values to connectWithSite() method
                loginConnection.Connect(givenUsername, givenPassword);
            }
        }
    }
}
