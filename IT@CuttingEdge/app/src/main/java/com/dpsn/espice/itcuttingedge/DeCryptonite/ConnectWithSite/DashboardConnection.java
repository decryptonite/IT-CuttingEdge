package com.dpsn.espice.itcuttingedge.DeCryptonite.ConnectWithSite;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dpsn.espice.itcuttingedge.DeCryptonite.DashboardFragment;
import com.dpsn.espice.itcuttingedge.DeCryptonite.LoginActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Sony on 03-05-2015.
 */
public class DashboardConnection {

    Context mContext;

    public DashboardConnection(Context context){
        mContext = context;
    }
    public void Connect(final String username, final String password) {
        //This method use AysncTask to handle the connection with the server

        class SiteAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                return LoginDoInBackground(params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.v(LoginActivity.LogTag, "Display  Information; " + s);
                DashboardFragment.displayInformation(s);
            }
        }

        SiteAsyncTask siteAsyncTask = new SiteAsyncTask();
        siteAsyncTask.execute(username, password);
    }

    public String LoginDoInBackground(String... params){
        String paramUsername = params[0];
        String paramPassword = params[1];
        Log.v(LoginActivity.LogTag, "paramUsername is : " + paramUsername +
                " paramPassword is : " + paramPassword);

        // Create an intermediate to connect with the Internet
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(LoginConnection.site + paramUsername + "&pass=" + paramPassword);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);

            InputStream inputStream = httpResponse.getEntity().getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String bufferedStrChunk = null;

            while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                stringBuilder.append(bufferedStrChunk + " ");
            }
            Log.v(LoginActivity.LogTag, "Return of doInBackground :" +
                    paramUsername + "<br>" + paramPassword +
                    "<br>" + stringBuilder.toString());

            return paramUsername + "<br>" + paramPassword + "<br>" +
                    stringBuilder.toString();

        } catch (ClientProtocolException e) {
            Log.v(LoginActivity.LogTag, "ClientProtocolException");
            Toast.makeText(mContext, "Error: Please Check Your Internet Connection",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            Log.v(LoginActivity.LogTag, "IOException");
            Toast.makeText(mContext, "Error: Please Check Your Internet Connection",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return null;
    }
}
