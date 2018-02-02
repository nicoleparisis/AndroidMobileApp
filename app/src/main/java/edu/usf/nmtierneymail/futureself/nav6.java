package edu.usf.nmtierneymail.futureself;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.hardware.camera2.CameraDevice;
import android.Manifest;
import android.graphics.Bitmap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.content.DialogInterface;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.usf.nmtierneymail.futureself.Database.UserCredsDBContract;
import edu.usf.nmtierneymail.futureself.Database.UserCredsDBHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link nav5.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link nav5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav6 extends Fragment implements
        View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    private ImageView imageView;
    private static final int CAMERA_CODE = 1888;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public nav6() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment nav5.
     */
    // TODO: Rename and change types and number of parameters
    public static nav6 newInstance(String param1) {
        nav6 fragment = new nav6();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("loggedin");
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_out:
                logOut();
                break;
            case R.id.takePic:
                takePic();
                break;
        }
    }

    private void logOut() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }
    private void takePic() {
        gatherPermissions();
        openCamera();
    }
    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, CAMERA_CODE);//track the camera result
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CODE) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView = (ImageView)getView().findViewById(R.id.imageView);
            imageView.setImageBitmap(photo);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            mParam1 = getArguments().getString("loggedin");
        }
        return inflater.inflate(R.layout.fragment_nav6, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String desiredPreference =  preferences.getString("loggedInUser", null);

        getView().findViewById(R.id.log_out).setOnClickListener(this);
        getView().findViewById(R.id.takePic).setOnClickListener(this);

        UserCredsDBHelper helper = new UserCredsDBHelper(getActivity());

        // Data repository db is in write mode
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] projection = {
                UserCredsDBContract.UserContract.COLUMN_USERNAME,
                UserCredsDBContract.UserContract.COLUMN_ID,
                UserCredsDBContract.UserContract.COLUMN_FIRST_NAME
        };

        String selection = UserCredsDBContract.UserContract.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { desiredPreference };


        Cursor cursor = db.query(
                UserCredsDBContract.UserContract.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        String name = "";
        while(cursor.moveToNext()) {
            String getName = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserCredsDBContract.UserContract.COLUMN_FIRST_NAME));
            name = getName;
        }
        cursor.close();
        TextView firstName = (TextView)getView().findViewById(R.id.textView4);
        firstName.setText("Welcome " + name);

    }
    public void gatherPermissions(){
        //GATHER ALL PERMISSIONS NEEDED TO RUN APPLICATION
        if ( (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(//REQUEST PERMISSIONS NEEDED FOR APP
                    getActivity(),
                    new String[]{
                            Manifest.permission.CAMERA
                    }, 123);
        }//END IF
    }//END CHECK

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
            Toast.makeText(context, "fra attached", Toast.LENGTH_SHORT).show();
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
}
