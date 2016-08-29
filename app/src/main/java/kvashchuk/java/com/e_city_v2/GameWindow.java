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
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameWindow extends Activity implements OnClickListener {

    EditText inputValue = null;
    TextView displayMessage = null;
    TextView inputValueId = null;
    TextView phoneID = null;
    TextView moveServers = null;
    TextView noInternet = null;

    Button toMakeMove;
    Button iGiveUp;
    String newString = null;
    String gameID = null;
    String urlNewGame = "http://mytomcatapp-dergachovda.rhcloud.com/NewGame";
    String urlplayingGame = "http://mytomcatapp-dergachovda.rhcloud.com/move";
    final static String IGIVEUP = "I GIVE UP";
    String lastLetter = "";
final public static String GAMEIDMESSAGE = "gameIDmessage";
    Connecting connecting = new Connecting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_window);
        Intent intent = getIntent();

        gameID = intent.getStringExtra(GAMEIDMESSAGE);
        //gameID = intent.getStringExtra("gameID1");


        inputValue = (EditText) findViewById(R.id.enterCity);
        inputValueId = (TextView) findViewById(R.id.gameID);

        inputValueId.setText(gameID);

        displayMessage = (TextView) findViewById(R.id.displayMessage);
        phoneID = (TextView) findViewById(R.id.phoneID);
        moveServers = (TextView) findViewById(R.id.moveServers);
        noInternet = (TextView) findViewById(R.id.noInternet);
//inputValue.setText(gameID);
        toMakeMove = (Button) findViewById(R.id.toMakeMove);
        iGiveUp = (Button) findViewById(R.id.iGiveUp);

        toMakeMove.setOnClickListener(this);
        iGiveUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toMakeMove:

                new Thread(new Runnable() {
                    public void run() {

                        try {

                            final String inputString = "GameID:" + gameID + "@Move:" + inputValue.getText().toString();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    if (inputString.equals("")) {
                                        displayMessage.setText("You don't make move ");
                                    } else {
                                        displayMessage.setText("");
                                    }
                                }
                            });

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    displayMessage.setText(inputString);
                                }
                            });

                            BufferedReader in = Connecting.theConnection(urlplayingGame, inputString);

                            String returnString;
                            while ((returnString = in.readLine()) != null) {
                                newString = returnString;
                            }
                            in.close();

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    if (inputString.equals("")) {
                                        inputValue.setText("");
                                    } else {
                                        moveServers.setText(newString);
                                        lastLetter = String.valueOf(newString.charAt(newString.length() - 1));
                                        inputValue.setText(lastLetter);
                                        inputValue.setSelection(1);

                                    }

                                }
                            });

                        } catch (Exception e) {
                            Log.d("Exception", e.toString());
                        }

                    }
                }).start();

                break;
            case R.id.iGiveUp:
                new Thread(new Runnable() {
                    public void run() {

                        try {
                            final String inputString = "GameID:" + gameID + "@Move:I GIVE UP";
                            BufferedReader in = Connecting.theConnection(urlplayingGame, inputString);
                            String returnString;
                            while ((returnString = in.readLine()) != null) {
                                newString = returnString;

                            }
                            in.close();

                            final String finalReturnString = newString;
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    moveServers.setText(newString);
                                }
                            });

                        } catch (Exception e) {
                            Log.d("Exception", e.toString());
                        }

                    }
                }).start();

                break;

        }
    }
}
