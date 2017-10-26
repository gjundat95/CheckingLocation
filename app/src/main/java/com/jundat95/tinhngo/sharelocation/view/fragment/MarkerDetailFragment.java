package com.jundat95.tinhngo.sharelocation.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jundat95.tinhngo.sharelocation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarkerDetailFragment extends BottomSheetDialogFragment {

    private static final String FILTER = "FILTER";

    @BindView(R.id.title) TextView txtTitle;
    @BindView(R.id.description) TextView txtDescription;

    private String title,description;


    public static MarkerDetailFragment newInstance(){
        return new MarkerDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_marker_detail, container, false);
        ButterKnife.bind(this,v);
        txtTitle.setText(title.toString());
        txtDescription.setText(description.toString());
        return v;

    }

}
