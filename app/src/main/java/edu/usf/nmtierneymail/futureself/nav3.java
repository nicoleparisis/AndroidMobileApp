package edu.usf.nmtierneymail.futureself;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.jetbrains.annotations.Nullable;

import edu.usf.nmtierneymail.futureself.Database.UserCredsDBContract;
import edu.usf.nmtierneymail.futureself.Database.UserCredsDBHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link nav3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link nav3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav3 extends Fragment implements
        View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ConnectivityManager connManager;
    NetworkInfo netInfo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public nav3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav3.
     */
    // TODO: Rename and change types and number of parameters
    public static nav3 newInstance(String param1, String param2) {
        nav3 fragment = new nav3();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.makeCall:
                makeCall("8134801900");
                break;
            case R.id.AlertOff:
                manageAlerts(false);
                break;
            case R.id.AlertOn:
                manageAlerts(true);
                break;
        }
    }

    private void makeCall(String phonenum) {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenum));
        startActivity(intent);
    }
    private void manageAlerts(boolean alertOn) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("SavingsAlertOn", alertOn);
        editor.commit();
        toast("Alerts turned " + alertOn);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav3, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getView().findViewById(R.id.makeCall).setOnClickListener(this);
        gatherPermissions();
        getView().findViewById(R.id.AlertOn).setOnClickListener(this);
        getView().findViewById(R.id.AlertOff).setOnClickListener(this);
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
            Toast.makeText(context, "fra attached", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void gatherPermissions(){
        //GATHER ALL PERMISSIONS NEEDED TO RUN APPLICATION
        if ( (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(//REQUEST PERMISSIONS NEEDED FOR APP
                    getActivity(),
                    new String[]{
                            android.Manifest.permission.CALL_PHONE,
                            android.Manifest.permission.READ_PHONE_STATE
                    }, 123);
        }//END IF
    }//END CHECK
    public void showTelState(View aView){
        // Post: Effects of TelephonyState
        Intent intent =  new Intent(getActivity(), TelephonyState.class);
        startActivity(intent);
    }

    private void toast(String aToast){
        Toast.makeText(getContext(), aToast, Toast.LENGTH_SHORT).show();
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
