package kvashchuk.java.com.e_city_v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Connecting extends Activity {
    String gameID = "";
    private static BufferedReader bufferedReader = null;
    String urlNewGame = "http://mytomcatapp-dergachovda.rhcloud.com/NewGame";
    PhoneID phoneID = new PhoneID(this);

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public BufferedReader theConnection(String urleGame, String inputString) throws IOException {
        URL url = new URL(urleGame);
        URLConnection connection = url.openConnection();
        Log.d("inputString", inputString);
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(inputString);
        out.close();

        bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        return bufferedReader;
    }

    public String getDeviceId() {


        return "";
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public String getGameIdFromServer(String deviceId) {
        try {
            BufferedReader in = theConnection(urlNewGame, deviceId);
            String returnString;
            while ((returnString = in.readLine()) != null) {
                String[] tempId = returnString.split(":");
                gameID = tempId[1];
            }
            in.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return gameID;
    }

    public String connectingALL(final String deviceId) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL url = new URL(urlNewGame);
            URLConnection connection = url.openConnection();
            String inputString = deviceId;
            Log.d("inputString", inputString);
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(inputString);
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String returnString = "";
            String gameIDnew;

            while ((returnString = in.readLine()) != null) {
                String[] tempId = returnString.split(":");
                gameID = tempId[1];

            }
            in.close();
            return gameID;
        } catch (Exception e) {
            Log.d("Exception", e.toString());
            return null;
        }

    }
}
