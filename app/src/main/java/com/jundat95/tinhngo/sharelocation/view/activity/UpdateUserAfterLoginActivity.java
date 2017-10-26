package com.jundat95.tinhngo.sharelocation.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jundat95.tinhngo.sharelocation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserAfterLoginActivity extends AppCompatActivity {

    private JsonObject updateInfoUser = new JsonObject();
    private String idUser = null;
    private String  idUserType = null;
    @BindView(R.id.spinner_origin) Spinner spinnerOrigin;

    @OnClick(R.id.btnUpdateInfoUser) public void UpdateInfoUserClick(){

        if(idUserType != null && idUser != null){

            // add IdUsertype to JSon
            updateInfoUser.addProperty("IdUserType",idUserType);
            finish();

        }else {
            Toast.makeText(UpdateUserAfterLoginActivity.this,"Please select origin",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_after_login);
        ButterKnife.bind(this);
        getUserId();
        addSpinner();
        Toast.makeText(UpdateUserAfterLoginActivity.this,"IdUserType: "+idUserType+"--IdUser: "+idUser,Toast.LENGTH_LONG).show();
    }

    private void addSpinner(){
        spinnerOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("personal")) {
                       idUserType = "0";
                       //Toast.makeText(UpdateUserAfterLoginActivity.this,"Update Login 0",Toast.LENGTH_LONG).show();
                   }else{
                       if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("company")) {
                           idUserType = "1";
                           //Toast.makeText(UpdateUserAfterLoginActivity.this,"Update Login 1",Toast.LENGTH_LONG).show();
                       }
                   }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    idUserType = null;
            }
        });

        List<String> origins = new ArrayList<>();
        origins.add("personal");
        origins.add("company");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,origins);
        spinnerOrigin.setAdapter(adapter);
    }

    private void getUserId(){
        this.idUser = getIntent().getStringExtra("IdUser");
        Toast.makeText(UpdateUserAfterLoginActivity.this,idUser,Toast.LENGTH_LONG).show();
        updateInfoUser.addProperty("IdUser",idUser);
    }


}
