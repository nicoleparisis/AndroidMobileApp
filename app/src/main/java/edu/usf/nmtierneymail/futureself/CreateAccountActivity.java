package edu.usf.nmtierneymail.futureself;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import edu.usf.nmtierneymail.futureself.Database.UserCredsDBContract;
import edu.usf.nmtierneymail.futureself.Database.UserCredsDBHelper;

import static edu.usf.nmtierneymail.futureself.Database.UserCredsDBContract.UserContract;


public class CreateAccountActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText username;
    EditText password;
    EditText password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
       // String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText(message);
        Button submit_button = (Button) findViewById(R.id.submit_button);
        firstName = (EditText)findViewById(R.id.first_name);
        lastName = (EditText)findViewById(R.id.last_name);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        password2 = (EditText)findViewById(R.id.password2);

        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createAccount();
            }
        });

    }
    public void createAccount(){
        boolean isInfoValid = validateForm();

        if(isInfoValid){

            UserCredsDBHelper helper = new UserCredsDBHelper(getApplicationContext());
            toast("Helper created");

            // Data repository db is in write mode
            SQLiteDatabase db = helper.getWritableDatabase();
            toast("SQLiteDatabase created");

            // Map of values created, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(UserContract.COLUMN_FIRST_NAME, firstName.getText().toString());
            toast("value 1 created!");

            values.put(UserContract.COLUMN_LAST_NAME, lastName.getText().toString());
            toast("value 2 created!");

            values.put(UserContract.COLUMN_USERNAME, username.getText().toString());
            toast("value 3 created!");

            values.put(UserContract.COLUMN_PASSWORD, password.getText().toString());
            toast("value 4 created!");

            // values inserted in row; insertion = primary key value of the new row
            long insertion = db.insert(UserContract.TABLE_NAME, null, values);
            toast("values Inserted; 'insertion=" + insertion);

            toast("ACCOUNT CREATION SUCCESSFUL");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }

    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }

    private boolean usernameExists(String username){
        boolean exists = false;

        UserCredsDBHelper helper = new UserCredsDBHelper(getApplicationContext());
        toast("Helper created");

        // Data repository db is in write mode
        SQLiteDatabase db = helper.getWritableDatabase();
        toast("SQLiteDatabase created");

        String[] projection = {
                UserContract.COLUMN_USERNAME,
                UserContract.COLUMN_ID
        };

        String selection = UserContract.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };


        Cursor cursor = db.query(
                UserContract.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(UserContract.COLUMN_ID));
            itemIds.add(itemId);
        }
        cursor.close();

        if(itemIds.size() > 0){
            exists = true;
        }

        return exists;
    }

    private boolean validateForm() {

        boolean isValid = true;

        if( firstName.getText().toString().length() == 0 ){
            isValid = false;
            firstName.setError( "First name is required!" );
        }

        if( lastName.getText().toString().length() == 0 ){
            isValid = false;
            lastName.setError( "Last name is required!" );
        }

        if( username.getText().toString().length() == 0 ){
            isValid = false;
            username.setError( "Username is required!" );
        }else{
            if(usernameExists(username.getText().toString())){
                isValid = false;
                username.setError( "Username exists!" );
            }
        }

        if( password.getText().toString().length() == 0 ){
            isValid = false;
            password.setError( "Password is required!" );
        }

        if( password2.getText().toString().length() == 0 ){
            isValid = false;
            password2.setError( "Re-type Password is required!" );
        }


        if( !password.getText().toString().equals(password2.getText().toString()) ){
            isValid = false;
            password.setError( "Passwords do not match" );
            password2.setError( "Passwords do not match" );
        }
        return isValid;
    }

}
