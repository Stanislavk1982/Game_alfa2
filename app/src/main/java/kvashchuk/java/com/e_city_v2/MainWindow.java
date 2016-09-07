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
import java.util.StringTokenizer;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainWindow extends Activity implements OnClickListener {
    PhoneID phoneID = new PhoneID(this);
    Connecting connecting = new Connecting();
    TextView textViewGameID = null;
    TextView noInternet = null;
    TextView textPhoneID = null;
    TextView idNew = null;

    Button newGame;
    Button kvashchuk;
    Button dergachov;
    String urlNewGame = "http://mytomcatapp-dergachovda.rhcloud.com/NewGame";
    String gameID = "-5";
    String gameIdnew = "-999";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        newGame = (Button) findViewById(R.id.newGame);
        //  kvashchuk = (Button) findViewById(R.id.kvashchuk);
        //  dergachov = (Button) findViewById(R.id.dergachev);
        textViewGameID = (TextView) findViewById(R.id.gameID);
        textPhoneID = (TextView) findViewById(R.id.phoneID);
        idNew = (TextView) findViewById(R.id.idNEW);

        newGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.newGame: {
                new Thread(new Runnable() {
                    public void run() {

                        try {
                            final String deviceId = phoneID.getDeviceId2().toString();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    textPhoneID.setText(deviceId);
                                }
                            });
/*
                            BufferedReader in = connecting.theConnection(urlNewGame, deviceId);
                            String returnString;
                            while ((returnString = in.readLine()) != null) {
                                String[] tempId = returnString.split(":");
                                gameID = tempId[1];
                                connecting.setGameID(gameID);
                            }
                            in.close();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    textViewGameID.setText(gameID);
                                }
                            }); */
                        } catch (Exception e) {
                            Log.d("Exception", e.toString());
                        }
                    }
                }).start();
                Intent intent = new Intent(this, GameWindow.class);
                //gameIdnew = connecting.getGameID();
                //gameIdnew = textPhoneID.getText().toString();
                //intent.putExtra(GameWindow.GAMEIDMESSAGE, gameIdnew);
                //idNew.setText(gameID);
                startActivity(intent);
                break;
            }
        }


    }
}
