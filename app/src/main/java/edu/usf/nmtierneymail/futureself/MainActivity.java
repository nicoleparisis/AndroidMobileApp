package edu.usf.nmtierneymail.futureself;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toast;
import android.database.Cursor;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.Manifest;
import java.util.Timer;
import java.util.TimerTask;

import edu.usf.nmtierneymail.futureself.BackgroundServices.AlertService;
import edu.usf.nmtierneymail.futureself.Database.UserCredsDBHelper;

public class MainActivity extends AppCompatActivity {

    ConnectivityManager connManager;
    NetworkInfo netInfo;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction t = fm.beginTransaction();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String desiredPreference =  preferences.getString("loggedInUser", null);
            if(desiredPreference != null && desiredPreference.length() > 1) { //username is not null meaning a user is logged in
                switch (item.getItemId()) {
                    case R.id.navigation_dashboard:
                        t.replace(R.id.content, new nav6()).commit();
                        return true;
                    case R.id.navigation_lifestyle:
                        t.replace(R.id.content, new nav2()).commit();
                        return true;
                    case R.id.navigation_investments:
                        t.replace(R.id.content, new nav3()).commit();
                        return true;
                    case R.id.navigation_housing:
                        t.replace(R.id.content, new nav4()).commit();
                        return true;
                    case R.id.navigation_cashopportunities:
                        t.replace(R.id.content, new nav5()).commit();
                        return true;
                }
                return false;
            }else{
                toast("sorry not logged in");
                t.replace(R.id.content, new nav1()).commit();
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gatherPermissions();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String desiredPreference =  preferences.getString("loggedInUser", null);
        if(desiredPreference != null && desiredPreference.length() > 1) { //username is not null meaning a user is logged in
            Bundle bundle=new Bundle();
            bundle.putString("loggedInUser", desiredPreference);
            //set Fragmentclass Arguments
            nav6 fragobj=new nav6();
            fragobj.setArguments(bundle);
            t.replace(R.id.content, fragobj).commit();

            Timer time = new Timer();
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent alertIntent = new Intent(getBaseContext(), AlertService.class);
                    if(!isServiceRunning(AlertService.class)){

                        startService(alertIntent);

                    }else{
                        stopService(alertIntent);
                    }

                }
            }, 0, 50000);


        } else {

            t.replace(R.id.content, new nav1()).commit();
        }

        connManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = connManager.getActiveNetworkInfo();

    }
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    public void isConnected(View view) {  // for connected_button
        boolean isConnected = netInfo != null &&
                netInfo.isConnectedOrConnecting();
        toast(String.format("Connected?: %s", isConnected));
    }
    public void reportStatus(View view) {
        // Post: The first NUM_CHARACTERS_IN_DISPLAY characters in connectivity status were toasted
        int NUM_CHARACTERS_IN_DISPLAY = 40;
        String beginStatus =
                (netInfo.toString()).substring(0, NUM_CHARACTERS_IN_DISPLAY);
        toast(String.format("Connectivity status: %s", beginStatus));
    }
    public void gatherPermissions(){
        //GATHER ALL PERMISSIONS NEEDED TO RUN APPLICATION
        if ( (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(//REQUEST PERMISSIONS NEEDED FOR APP
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE
                    }, 123);
        }//END IF
    }//END CHECK
    public void showTelState(View aView){
        // Post: Effects of TelephonyState
        Intent intent =  new Intent(this, TelephonyState.class);
        startActivity(intent);
    }

    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }



}
