package edu.usf.nmtierneymail.futureself.Database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nicoletierney on 10/2/17.
 */

public class ProfileQuestionsDBHelper extends SQLiteOpenHelper {
    private ContentResolver myResolver;
    public static final String TABLE_NAME1 = "profilequestions";
    public static final String PRIMARY_KEY_NAME = "id";
    public static final String FIELD1_NAME = "questionText";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "profilequestions.db";
    public static final String TABLE_SPECIFICATIONS =
            "CREATE TABLE " + TABLE_NAME1 + "(" +
                    PRIMARY_KEY_NAME + " INTEGER PRIMARY KEY, " + FIELD1_NAME + " STRING, " + ")";

    public static final String TABLE_NAME2 = "profilequestionsanswers";
    public static final String FIELD1_NAME3 = "userid";
    public static final String FIELD1_NAME4 = "questionid";
    public static final String FIELD1_NAME5 = "questionText";
    public static final int DATABASE_VERSION2 = 1;
    public static final String DATABASE_NAME2 = "profilequestionsanswers.db";
    public static final String TABLE_SPECIFICATIONS2 =
            "CREATE TABLE " + TABLE_NAME2 + "(" +
                    FIELD1_NAME3 + " INTEGER, " + FIELD1_NAME4 + " INTEGER, " + FIELD1_NAME5 + " STRING, " + ")";

    public ProfileQuestionsDBHelper(Context context) {
        // A database exists, named DATABASE_NAME, with TABLE_SPECIFICATIONS
        super(context, ProfileQuestionsDBContract.DB_NAME, null, DATABASE_VERSION);
        myResolver = context.getContentResolver();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SPECIFICATIONS);
        db.execSQL(TABLE_SPECIFICATIONS2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
