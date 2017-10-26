package com.jundat95.tinhngo.sharelocation.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jundat95.tinhngo.sharelocation.R;
import com.jundat95.tinhngo.sharelocation.until.TiSharedPreferences;
import com.jundat95.tinhngo.sharelocation.view.adapter.SubjectPostedListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectPostedListActivity extends AppCompatActivity implements SubjectPostedListAdapter.AdapterCallback {

    @BindView(R.id.recycler_subject_posted) RecyclerView recyclerViewSubjectPosted;

    private static final String SUBJECT_POSTED_LIST = "SUBJECT_POSTED_LIST";

    private SubjectPostedListAdapter subjectPostedListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_posted_list);
        ButterKnife.bind(this);

        subjectPostedListAdapter = new SubjectPostedListAdapter(this);
        recyclerViewSubjectPosted.setAdapter(subjectPostedListAdapter);

        recyclerViewSubjectPosted.setLayoutManager(new LinearLayoutManager(this));


    }

    // Click in item RyceclerView
    @Override
    public void sendData(int i) {
        //Toast.makeText(SubjectPostedListActivity.this,"Items: "+i,Toast.LENGTH_LONG).show();
        Gson gson = new Gson();

        Intent intent = new Intent(SubjectPostedListActivity.this, UpdatePostSubjectActivity.class);

        startActivity(intent);

    }



    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(SubjectPostedListActivity.this,"Resum Postsubject",Toast.LENGTH_LONG).show();

    }
}
