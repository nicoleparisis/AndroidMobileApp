package edu.usf.nmtierneymail.futureself;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.graphics.Color;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.Date;
import java.util.Calendar;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link nav4.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link nav4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav4 extends Fragment {
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

    public nav4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav4.
     */
    // TODO: Rename and change types and number of parameters
    public static nav4 newInstance(String param1, String param2) {
        nav4 fragment = new nav4();
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
    public void isConnected(View view) {  // for connected_button
        boolean isConnected = netInfo != null &&
                netInfo.isConnectedOrConnecting();
        Toast.makeText(getActivity(), "connected? : %s" + isConnected, Toast.LENGTH_SHORT).show();
        //toast(String.format("Connected?: %s", isConnected));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav4, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        ApiGetter task = new ApiGetter();
        task.execute("https://www.quandl.com/api/v3/datasets/ZILLOW/N309_MVALFAH.json?api_key=4vE8avxLej5XszYz_NRr");
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
    private class ApiGetter extends AsyncTask<String, Void, String> {

        private Exception exception;
        TextView textView10;

        protected void onPreExecute() {
           // housingMarketData = (TextView) getView().findViewById(R.id.housingMarketData);
           // housingMarketData.setText("");
        }
    protected String doInBackground(String... urls) {

        String url_param = urls[0];

        try {
            URL url = new URL(url_param);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }

        try {
            JSONObject jObject = new JSONObject(response);

            JSONObject j2 = jObject.getJSONObject("dataset");
            Log.i("INFO", j2.toString());
            JSONArray jArray = j2.getJSONArray("data");
            Log.i("INFO", jArray.toString());
            String title = j2.getString("name");
            DataPoint[] dp = new DataPoint[jArray.length()];
            for (int i=0; i < jArray.length(); i++)
            {
                JSONArray jArray2 = jArray.getJSONArray(i);
                for (int j = 0; j < jArray2.length(); j++) {
                    String firstVal = jArray2.getString(0);
                    Log.i("INFO", firstVal);
                    Double secondVal = jArray2.getDouble(1);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                    Date convertedDate = new Date();
                    convertedDate = dateFormat.parse(firstVal);
                    dp[i] = new DataPoint(convertedDate, secondVal);
                }

            }
            textView10 = getActivity().findViewById(R.id.textView10);
            textView10.setText(title);
            GraphView graph = (GraphView) getView().findViewById(R.id.graph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dp);
            series.setColor(Color.GREEN);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(10);
            series.setThickness(8);
            graph.addSeries(series);

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 1996);
            cal.set(Calendar.MONTH, Calendar.APRIL);
            cal.set(Calendar.DAY_OF_MONTH, 30);
            Date dateRepresentation = cal.getTime();

            Calendar cal2 = Calendar.getInstance();
            cal2.add(Calendar.DATE, 1);
            Date d3 = cal2.getTime();

            graph.getViewport().setMinX(dateRepresentation.getTime());
            graph.getViewport().setMaxX(d3.getTime());
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            graph.getGridLabelRenderer().setNumHorizontalLabels(5); // space issue
        } catch (JSONException e) {
            Log.e("Error", "exception", e);
        } catch (ParseException e) {
            Log.e("Error", "exception", e);
        }

        Log.i("INFO", response);
    }
}
}
