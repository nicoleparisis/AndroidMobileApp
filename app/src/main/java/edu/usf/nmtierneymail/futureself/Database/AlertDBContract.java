package edu.usf.nmtierneymail.futureself.Database;

/**
 * Created by nicoletierney on 10/15/17.
 */

public class AlertDBContract {
    public static final String PROVIDER_NAME = "edu.usf.nmtierney.futureself.ContentProvider.SAContentProvider";
    public static final String URL = "content://" + PROVIDER_NAME;
    public static final String DB_NAME = "AlertHistory.db";
    public static final int DB_VERSION = 1;

    public static final class AlertContract {
        public static final String TABLE_NAME = "AlertHistory";
        public static final String COLUMN_ID = "id";
        public static final String Alert_Id = "alertid";
        public static final String User_Id = "userid";

    }
}
