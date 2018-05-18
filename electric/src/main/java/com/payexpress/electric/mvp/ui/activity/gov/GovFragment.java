package com.payexpress.electric.mvp.ui.activity.gov;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payexpress.electric.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GovFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GovFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GovFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gov, container, false);
    }

  
}
