package com.jundat95.tinhngo.sharelocation.view.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jundat95.tinhngo.sharelocation.R;
import com.jundat95.tinhngo.sharelocation.repository.local.fakeData.AttendFake;
import com.jundat95.tinhngo.sharelocation.repository.local.fakeData.UserFake;
import com.jundat95.tinhngo.sharelocation.repository.local.model.AttendModel;
import com.jundat95.tinhngo.sharelocation.repository.local.model.UserModel;
import com.jundat95.tinhngo.sharelocation.until.TiSharedPreferences;
import com.jundat95.tinhngo.sharelocation.until.Until;
import com.vansuita.pickimage.PickImageDialog;
import com.vansuita.pickimage.PickSetup;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostSubjectActivity extends AppCompatActivity {

    @BindView(R.id.edtTitle) EditText edtTitle;
    @BindView(R.id.edtDescription) EditText edtDescription;
    @BindView(R.id.edtLocation) EditText edtLocation;
    @BindView(R.id.edtPhoneNumber) EditText edtPhoneNumber;
    @BindView(R.id.layout_images) LinearLayout layoutImages;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvCodeEmployee) TextView tvCodeEmployee;
    @BindView(R.id.spType) Spinner spinner;

    private int type = 0;
    private ProgressDialog progressDialog;


    private List<Bitmap> listImages = new ArrayList<>();
    private JsonObject postSubject = new JsonObject();
    private LatLng latLng = null;
    private int i;
    private JsonObject imgJson;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_subject);
        ButterKnife.bind(this);
        i = 0;
        getIntentMainActivity();

    }

    private void getIntentMainActivity(){

        Bundle bundle = getIntent().getExtras();
        String position = bundle.getString("POSITION");
        Gson gson = new Gson();
        latLng = gson.fromJson(position,LatLng.class);

        userModel = UserFake.newInstance().userModel;
        tvName.setText("Tên Nhân Viên: "+userModel.getFullName());
        tvCodeEmployee.setText("Mã Nhân Viên: "+userModel.getEmployeeCode());

        List<String> categories = new ArrayList<String>();
        categories.add("Bắt đầu làm");
        categories.add("Làm xong");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @OnClick(R.id.btnAddImage) public void addImagesClick(){
        PickSetup setup = new PickSetup();
        setup.setImageSize(200);
        setup.setFlip(true);

        PickImageDialog.on(PostSubjectActivity.this,setup).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {
                if(r.getError() == null){
                   if(i <= 2){
                       i++;
                       //Toast.makeText(PostSubjectActivity.this,"I: "+i,Toast.LENGTH_LONG).show();
                       Bitmap bm = r.getBitmap();
                       listImages.add(bm);
                       ImageView imgView = new ImageView(getApplicationContext());
                       imgView.setImageBitmap(bm);
                       imgView.setPadding(10,10,10,10);
                       layoutImages.addView(imgView,500,500);
                   }else{
                       Toast.makeText(PostSubjectActivity.this,"Error: Max images is 3 ",Toast.LENGTH_LONG).show();
                   }
                }else {
                    Toast.makeText(PostSubjectActivity.this,"Error: "+r.getError(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @OnClick(R.id.btnPost) public void postSubjectClick(){

        if(!edtTitle.getText().equals("")){
            if(!edtDescription.getText().equals("")) {
                if(!edtLocation.getText().equals("")) {
                    if(!edtPhoneNumber.getText().equals("")) {
                        if(!listImages.isEmpty()) {
                            if(latLng != null) {
                                long id = new Date().getTime();
                                String time = new Date().toString();
                                AttendModel attendModel = new AttendModel(
                                        id + "",
                                        edtTitle.getText().toString(),
                                        edtDescription.getText().toString(),
                                        edtLocation.getText().toString(),
                                        time,
                                        edtPhoneNumber.getText().toString(),
                                        userModel.getId(),
                                        type+"",
                                        latLng,
                                        listImages

                                );
                                AttendFake.newInstance().setAttendModels(attendModel);
                                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                }
            }
        }

        //Toast.makeText(this, "Thêm thành công.", Toast.LENGTH_SHORT).show();

    }

    private void loadProcessBar(){
        //load Processbar
        progressDialog = new ProgressDialog(PostSubjectActivity.this,R.style.AppTheme_Blue_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Waiting...");
        progressDialog.show();
    }

}
