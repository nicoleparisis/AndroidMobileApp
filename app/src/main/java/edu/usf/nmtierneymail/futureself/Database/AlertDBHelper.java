package edu.usf.nmtierneymail.futureself.Database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nicoletierney on 10/15/17.
 */

public class AlertDBHelper extends SQLiteOpenHelper {
    private ContentResolver myResolver;
    public static final String TABLE_NAME1 = "AlertHistory";
    public static final String PRIMARY_KEY_NAME = "id";
    public static final String FIELD1_NAME = "alertid";
    public static final String FIELD2_NAME = "userid";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AlertHistory.db";
    public static final String TABLE_SPECIFICATIONS =
            "CREATE TABLE " + TABLE_NAME1 + "(" +
                    PRIMARY_KEY_NAME + " INTEGER PRIMARY KEY, " + FIELD1_NAME + " INTEGER, " + FIELD2_NAME + " INTEGER " +")";

    public AlertDBHelper(Context context) {
        // A database exists, named DATABASE_NAME, with TABLE_SPECIFICATIONS
        super(context, AlertDBContract.DB_NAME, null, DATABASE_VERSION);
        myResolver = context.getContentResolver();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SPECIFICATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
