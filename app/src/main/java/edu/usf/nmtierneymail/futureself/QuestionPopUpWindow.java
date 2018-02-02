package edu.usf.nmtierneymail.futureself;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import edu.usf.nmtierneymail.futureself.Database.ProfileQuestionsDBContract;
import edu.usf.nmtierneymail.futureself.Database.ProfileQuestionsDBHelper;
import edu.usf.nmtierneymail.futureself.Database.UserCredsDBContract;
import edu.usf.nmtierneymail.futureself.Database.UserCredsDBHelper;

public class QuestionPopUpWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_pop_up_window);

        Intent i = getIntent();
        final String qt = i.getStringExtra("QuestionText");
        final int qid = i.getIntExtra("QuestionId", 0);

        EditText et = (EditText) findViewById(R.id.inputforq);
        et.setHint(qt);

        Button submit_button = (Button) findViewById(R.id.done);
        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveQuestionAnswer(qid, qt);
            }
        });
    }

    public void saveQuestionAnswer(int id, String qtext){
        ProfileQuestionsDBHelper helper = new ProfileQuestionsDBHelper(getApplicationContext());
        toast("Helper created");

        // Data repository db is in write mode
        SQLiteDatabase db = helper.getWritableDatabase();
        toast("SQLiteDatabase created");

        // Map of values created, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ProfileQuestionsDBContract.UserQuestionContract.COLUMN_QUESTION, id);
        toast("value 1 created!");
        values.put(ProfileQuestionsDBContract.UserQuestionContract.COLUMN_QUESTION_ANSWER, qtext);
        toast("value 2 created!");

        // values inserted in row; insertion = primary key value of the new row
        long insertion = db.insert(ProfileQuestionsDBContract.UserQuestionContract.TABLE_NAME, null, values);
        toast("values Inserted; 'insertion=" + insertion);

    }
    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }
}
