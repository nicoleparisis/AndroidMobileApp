package edu.usf.nmtierneymail.futureself;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.Arrays;

import org.jetbrains.annotations.Nullable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link nav5.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link nav5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav5 extends Fragment implements OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String[] items = new String[]{"Technology", "Healthcare", "Agriculture", "Engineering", "Construction", "Casinos/Gaminig",
            "Hospitality", "Education", "Entertainment", "Beauty/Hair", "Finance", "Communications", "Marketing", "Sales", "Sports", "Writer", "Journalist/News Anchor"};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public nav5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav5.
     */
    // TODO: Rename and change types and number of parameters
    public static nav5 newInstance(String param1, String param2) {
        nav5 fragment = new nav5();
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
        return inflater.inflate(R.layout.fragment_nav5, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        Spinner dropdown = (Spinner)getView().findViewById(R.id.spinner);

        Arrays.sort(items);//alpha sort
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        ImageView image = (ImageView) getView().findViewById(R.id.cityImg);

        String selected = items[position];

        switch(selected){
            case  "Technology":
                image.setImageResource(R.drawable.us_nyc);
                break;
            case  "Healthcare":
                image.setImageResource(R.drawable.denver);
                break;
            case  "Agriculture":
                image.setImageResource(R.drawable.dallas);
                break;
            case  "Engineering":
                image.setImageResource(R.drawable.chicago);
                break;
            case  "Construction":
                image.setImageResource(R.drawable.atlanta);
                break;
            case  "Casinos/Gaminig":
                image.setImageResource(R.drawable.boston);
                break;
            case  "Hospitality":
                image.setImageResource(R.drawable.miami);
                break;
            case  "Entertainment":
                image.setImageResource(R.drawable.hollywood);
                break;
            case  "Education":
                image.setImageResource(R.drawable.us_nyc);
                break;
            case  "Beauty/Hair":
                image.setImageResource(R.drawable.hollywood);
                break;
            case  "Finance":
                image.setImageResource(R.drawable.boston);
                break;
            case  "Communications":
                image.setImageResource(R.drawable.chicago);
                break;
            case  "Marketing":
                image.setImageResource(R.drawable.dallas);
                break;
            default:
                image.setImageResource(R.drawable.denver);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
}
