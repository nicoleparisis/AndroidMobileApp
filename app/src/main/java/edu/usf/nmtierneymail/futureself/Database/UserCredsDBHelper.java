package edu.usf.nmtierneymail.futureself.Database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nicoletierney on 9/24/17.
 */

public class UserCredsDBHelper extends SQLiteOpenHelper {
    private ContentResolver myResolver;
    public static final String USER_TABLE_NAME = "users";
    public static final String PRIMARY_KEY_NAME = "id";
    public static final String FIELD1_NAME = "firstname";
    public static final String FIELD2_NAME = "lastname";
    public static final String FIELD3_NAME = "username";
    public static final String FIELD4_NAME = "password";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "userCredentials.db";
    public static final String TABLE_SPECIFICATIONS =
            "CREATE TABLE " + USER_TABLE_NAME + "(" +
                    PRIMARY_KEY_NAME + " INTEGER PRIMARY KEY, " + FIELD1_NAME + " STRING, "
                    + FIELD2_NAME + " STRING, " + FIELD3_NAME + " STRING, " + FIELD4_NAME + " STRING" + ")";

    public UserCredsDBHelper(Context context) {
        // A database exists, named DATABASE_NAME, with TABLE_SPECIFICATIONS
        super(context, UserCredsDBContract.DB_NAME, null, DATABASE_VERSION);
        myResolver = context.getContentResolver();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SPECIFICATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
