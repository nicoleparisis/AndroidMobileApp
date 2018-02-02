package edu.usf.nmtierneymail.futureself;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AlertActivity extends Activity {
    Context con = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("title");
        String text = getIntent().getStringExtra("text");
        super.onCreate(savedInstanceState);
        gatherPermissions();
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle(title)
                .setMessage(text)
                .setCancelable(false)
                .setNegativeButton("Return",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();  // close dialog box only
                                ((Activity) con).finish();
                            }
                        }
                )
                .setPositiveButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                System.exit(0);  // close this app
                            }
                        }
                );

        // Postcondition
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void gatherPermissions(){
        //GATHER ALL PERMISSIONS NEEDED TO RUN APPLICATION
        if ( (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(//REQUEST PERMISSIONS NEEDED FOR APP
                    this,
                    new String[]{
                            android.Manifest.permission.SYSTEM_ALERT_WINDOW
                    }, 123);
        }//END IF
    }//END CHECK
}
