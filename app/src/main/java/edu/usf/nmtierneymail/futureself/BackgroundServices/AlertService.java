package edu.usf.nmtierneymail.futureself.BackgroundServices;

import android.app.IntentService;
import android.app.Service;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.database.Cursor;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Context;
import android.content.Intent;
import android.Manifest;
import android.widget.Toast;

import java.io.FileReader;
import java.util.List;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;

import edu.usf.nmtierneymail.futureself.AlertActivity;
import edu.usf.nmtierneymail.futureself.Database.AlertDBContract;
import edu.usf.nmtierneymail.futureself.Database.AlertDBHelper;
import edu.usf.nmtierneymail.futureself.Database.UserCredsDBContract;
import edu.usf.nmtierneymail.futureself.Database.UserCredsDBHelper;
import edu.usf.nmtierneymail.futureself.MainActivity;

/**
 * Created by nicoletierney on 10/15/17.
 */

public class AlertService extends IntentService{


    public AlertService() {
        super("AlertService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean AlertTurnedOn =  preferences.getBoolean("SavingsAlertOn", false);
        String username =  preferences.getString("loggedInUser", null);

        Integer userid = getUserId(username);

        String json = null;

        if(AlertTurnedOn){
            try {

                InputStream is = getAssets().open("SavingsTips.json");
                int size = is.available();

                byte[] buffer = new byte[size];

                is.read(buffer);

                is.close();

                json = new String(buffer, "UTF-8");

                JSONObject obj = new JSONObject(json);
                JSONArray ja_data = obj.getJSONArray("Tips");

                for(int j=0; j<ja_data.length(); j++) {//go through all of the alerts and display the ones that have not yet been displayed to the user

                    JSONObject jo = ja_data.getJSONObject(j);
                    Integer alertId = jo.getInt("id");
                    String title = jo.getString("title");
                    String text = jo.getString("text");

                    if (seenAlert(alertId, userid)) {
                        continue;

                    }else{
                        markSeenAlert(alertId, userid);
                        displayAlert(title, text);
                        break;
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else{
            //do nothing
        }

    }

    private void displayAlert(String title, String text) {
        Intent intent = new Intent(getBaseContext(), AlertActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        startActivity(intent);
    }

    private Integer getUserId(String username) {
        UserCredsDBHelper helper = new UserCredsDBHelper(getApplicationContext());
        // Data repository db is in write mode
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] projection = {
                UserCredsDBContract.UserContract.COLUMN_USERNAME,
                UserCredsDBContract.UserContract.COLUMN_ID
        };

        String selection = UserCredsDBContract.UserContract.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };


        Cursor cursor = db.query(
                UserCredsDBContract.UserContract.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        Integer userId = null;
        while(cursor.moveToNext()) {
            userId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(UserCredsDBContract.UserContract.COLUMN_ID));

        }
        cursor.close();
        return userId;
    }

    public boolean seenAlert(Integer alertId, Integer userId) {
       boolean hasSeenAlert = false;

        AlertDBHelper helper = new AlertDBHelper(getApplicationContext());

        // Data repository db is in write mode
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] projection = {
                AlertDBContract.AlertContract.Alert_Id,
                AlertDBContract.AlertContract.User_Id
        };

        String selection = AlertDBContract.AlertContract.Alert_Id + " = ? AND " + AlertDBContract.AlertContract.User_Id + " = ?";
        String[] selectionArgs = { Integer.toString(alertId), Integer.toString(userId) };


        Cursor cursor = db.query(
                AlertDBContract.AlertContract.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            Integer itemId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(AlertDBContract.AlertContract.Alert_Id));
            itemIds.add(itemId);
        }
        cursor.close();

        if(itemIds.size() > 0){
            hasSeenAlert = true;
        }

        return hasSeenAlert;
    }

    public void markSeenAlert(Integer alertId, Integer userId) {
        AlertDBHelper helper = new AlertDBHelper(getApplicationContext());

        // Data repository db is in write mode
        SQLiteDatabase db = helper.getWritableDatabase();

        // Map of values created, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AlertDBContract.AlertContract.Alert_Id, alertId);

        values.put(AlertDBContract.AlertContract.User_Id, userId);


        // values inserted in row; insertion = primary key value of the new row
        db.insert(AlertDBContract.AlertContract.TABLE_NAME, null, values);

    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        stopSelf();
    }
    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }

}
