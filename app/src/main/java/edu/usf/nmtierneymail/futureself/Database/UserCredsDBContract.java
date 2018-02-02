package edu.usf.nmtierneymail.futureself.Database;

/**
 * Created by nicoletierney on 9/24/17.
 */

public class UserCredsDBContract {


        public static final String PROVIDER_NAME = "edu.usf.nmtierney.futureself.ContentProvider.SAContentProvider";
        public static final String URL = "content://" + PROVIDER_NAME;
        public static final String DB_NAME = "userCredentials.db";
        public static final int DB_VERSION = 1;

        public static final class UserContract {
            public static final String TABLE_NAME = "Users";
            public static final String COLUMN_ID = "id";
            public static final String COLUMN_FIRST_NAME = "firstname";
            public static final String COLUMN_LAST_NAME = "lastname";
            public static final String COLUMN_USERNAME = "username";
            public static final String COLUMN_PASSWORD = "password";

        }

        public static final class StudentGradeContract{
            //public static final String TABLE_NAME = "Users";
            // public static final String COLUMN_NAME_STUDENT_ID = "studentid";
            // public static final String COLUMN_NAME_ASSIGNMENTID = "assignmentid";
            // public static final String COLUMN_NAME_GRADE = "grade";

            // public static final Uri ASSIGNMENTURI = Uri.parse(URL + "/" + AssignmentContract.TABLE_NAME);

             //public static final UriMatcher sURIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);

            // public static final int ASSIGNMENT = 1;
            //  public static final int ASSIGNMENTID = 2;
            //  public static final int STUDENT = 3;
            //  public static final int STUDENT = 3;

            static{
                //  sURIMATCHER.addURI(PROVIDER_NAME, AssignmentContract.TABLE_NAME, ASSIGNMENT);
                //   sURIMATCHER.addURI(PROVIDER_NAME, AssignmentContract.TABLE_NAME, ASSIGNMENTID);

                //  sURIMATCHER.addURI(PROVIDER_NAME, StudentGradeContract.TABLE_NAME, STUDENT);

                //  sURIMATCHER.addURI(PROVIDER_NAME, StudentGradeContract.TABLE_NAME, STUDENTID);

            }
        }
}
