package edu.usf.nmtierneymail.futureself;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.usf.nmtierneymail.futureself.Database.UserCredsDBContract;
import edu.usf.nmtierneymail.futureself.Database.UserCredsDBHelper;
import edu.usf.nmtierneymail.futureself.Model.UserCreds;


public class nav1 extends Fragment implements
        View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public SharedPreferences prefs1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 0;
    EditText username;
    EditText password;

    private OnFragmentInteractionListener mListener;

    public nav1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav1.
     */
    // TODO: Rename and change types and number of parameters
    public static nav1 newInstance(String param1, String param2) {
        nav1 fragment = new nav1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        getView().findViewById(R.id.log_in_button).setOnClickListener(this);
        getView().findViewById(R.id.sign_in_button).setOnClickListener(this);
        getView().findViewById(R.id.create_account_button).setOnClickListener(this);
        username = (EditText) getView().findViewById(R.id.editText);
        password = (EditText) getView().findViewById(R.id.editText2);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Log.i("Successful Fragment", "Fragment 1 Attatched");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.create_account_button:
                createAccount(v);
                break;
            case R.id.log_in_button:
                logIn();
                break;
        }
    }
    private void createAccount(View view) {
        Intent intent = new Intent(this.getActivity(), CreateAccountActivity.class);
        EditText editText = (EditText) getView().findViewById(R.id.editText);
        String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            toast("logged in");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("loggedInUser", username.getText().toString());
            editor.commit();

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("loggedin", true);
            getActivity().startActivity(intent);
        } else {
            toast("invalid log in");
        }
    }

    private void logIn() {

        if (validateForm()) {
            if(verifyAccount(username.getText().toString(), password.getText().toString())){
                toast("logged in");

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("loggedInUser", username.getText().toString());
                editor.commit();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("loggedin", true);
                getActivity().startActivity(intent);
            }else{
                toast("Invalid log in");
            }
        } else {

        }
    }

    private boolean validateForm() {
        boolean isValid = true;
        try{
            if( username.getText().toString().length() == 0 ){
                username.setError( "Username is required!" );
                isValid = false;
            }

            if( password.getText().toString().length() == 0 ){
                password.setError( "Password is required!" );
                isValid = false;
            }

        }catch(Exception e){
            Log.e("Exception caught!", e.getMessage());
        }
        return isValid;

    }


    private boolean verifyAccount(String username, String password){
        boolean exists = false;
        boolean isCorrect = false;

        UserCredsDBHelper helper = new UserCredsDBHelper(getActivity());
        toast("Helper created");

        // Data repository db is in write mode
        SQLiteDatabase db = helper.getWritableDatabase();
        toast("SQLiteDatabase created");

        String[] projection = {
                UserCredsDBContract.UserContract.COLUMN_USERNAME,
                UserCredsDBContract.UserContract.COLUMN_PASSWORD,
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

        UserCreds userRecord = new UserCreds();
        while(cursor.moveToNext()) {
            userRecord._id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(UserCredsDBContract.UserContract.COLUMN_ID));
            userRecord._username = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserCredsDBContract.UserContract.COLUMN_USERNAME));
            userRecord._password = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserCredsDBContract.UserContract.COLUMN_PASSWORD));


        }



        cursor.close();

        if(userRecord._id != 0 && userRecord._id != -1){
            exists = true;
            if(password.equals(userRecord._password)){
                isCorrect = true;
            }
        }else{
            isCorrect = false;
        }



        return isCorrect;
    }
    private void toast(String aToast){
        Toast.makeText(getActivity(), aToast, Toast.LENGTH_SHORT).show();
    }

}


