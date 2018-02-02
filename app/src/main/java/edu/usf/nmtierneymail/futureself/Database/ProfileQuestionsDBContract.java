package edu.usf.nmtierneymail.futureself.Database;

import android.net.Uri;
import android.content.UriMatcher;
/**
 * Created by nicoletierney on 10/2/17.
 */

public class ProfileQuestionsDBContract {

    public static final String PROVIDER_NAME = "edu.usf.nmtierney.futureself.ContentProvider.SAContentProvider";
    public static final String URL = "content://" + PROVIDER_NAME;
    public static final String DB_NAME = "ProfileQuestions.db";
    public static final int DB_VERSION = 1;

    public static final class ProfileQuestionContract {
        public static final String TABLE_NAME = "profilequestions";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION_TEXT = "questionText";

    }

    public static final class UserQuestionContract{
        public static final String TABLE_NAME = "profilequestionsanswers";
        public static final String COLUMN_USER_ID = "userid";
        public static final String COLUMN_QUESTION = "questionid";
        public static final String COLUMN_QUESTION_ANSWER = "answer";

        public static final Uri profileURI = Uri.parse(URL + "/" + ProfileQuestionContract.TABLE_NAME);

        public static final UriMatcher sURIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);


        static{
            //  sURIMATCHER.addURI(PROVIDER_NAME, AssignmentContract.TABLE_NAME, ASSIGNMENT);
            //   sURIMATCHER.addURI(PROVIDER_NAME, AssignmentContract.TABLE_NAME, ASSIGNMENTID);

            //  sURIMATCHER.addURI(PROVIDER_NAME, StudentGradeContract.TABLE_NAME, STUDENT);

            //  sURIMATCHER.addURI(PROVIDER_NAME, StudentGradeContract.TABLE_NAME, STUDENTID);

        }
    }
}
