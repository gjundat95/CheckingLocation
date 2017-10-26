package com.jundat95.tinhngo.sharelocation.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jundat95.tinhngo.sharelocation.R;

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

public class UpdatePostSubjectActivity extends AppCompatActivity {

    @BindView(R.id.subject_name) EditText txtSubjectName;
    @BindView(R.id.subject_description) EditText txtDescription;
    @BindView(R.id.spinner_product) Spinner spinnerItem;
    @BindView(R.id.price) EditText txtPrice;
    @BindView(R.id.phone) EditText txtPhone;
    @BindView(R.id.facebook_contact) EditText txtFace;
    @BindView(R.id.end_time) EditText txtEndTime;
    @BindView(R.id.btn_time) Button btnTime;
    @BindView(R.id.end_date) EditText txtEndDate;
    @BindView(R.id.btn_date) Button btnDate;


    private List<String> categories = new ArrayList<>();
    private int idItemType = 0;

    private ArrayAdapter<String> adapterSpinner;

    private int mDate,mYear,mMonth,mHour,mMinute,mSecond;
    private String txtEDate,txtETime;

    private JsonObject postSubject = new JsonObject();

    private UpdateSubjectInterface updateSubjectInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post_subject);
        ButterKnife.bind(this);

        getDataFromSubjectPostList();
        dataBinding();
    }

    @OnClick(R.id.btn_time) public void getTimeClick(){

        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR);
        mMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog pickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txtETime = hourOfDay+":"+minute+":"+"00";
                txtEndTime.setText(txtETime);
            }
        },mHour,mMinute,false);
        pickerDialog.show();
    }

    @OnClick(R.id.btn_date) public void getDateClick(){

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDate = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtEDate  = year+"-"+(month+1)+"-"+dayOfMonth;
                txtEndDate.setText(txtEDate);
            }
        },mYear,mMonth,mDate);
        pickerDialog.show();
    }

    @OnClick(R.id.update_subject) public void updateSubjectClick(){
        addPostSubject();
    }





    private void initSpinner(){
        spinnerItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idItemType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idItemType = 0;
            }
        });
        adapterSpinner = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,categories);
        spinnerItem.setAdapter(adapterSpinner);


    }

    private void getDataFromSubjectPostList(){

        String subjectPost = getIntent().getStringExtra("SUBJECT_POSTED_LIST");
        Gson gson = new Gson();

    }

    private void dataBinding(){
        txtDescription.setEnabled(false);
        txtFace.setEnabled(false);

        txtEndDate.setText(txtEDate);
        txtEndTime.setText(txtETime);


    }

    // add properties, value to Json Object
    private void addPostSubject(){


        if(txtSubjectName.getText().length() > 0){
            postSubject.addProperty("SubjectName",txtSubjectName.getText().toString());

            if(!txtEDate.isEmpty() && !txtETime.isEmpty()){
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                postSubject.addProperty("TimeCreate",dateFormat.format(date).toString());
                String dateTime = txtEDate+" "+txtETime;
                try {

                    date = dateFormat.parse(dateTime);
                    postSubject.addProperty("ExpiryTime",dateFormat.format(date));

                    if(txtPhone.getText().length() > 0){
                        postSubject.addProperty("Contact",txtPhone.getText().toString());

                        if(txtPrice.getText().length() > 0){
                            postSubject.addProperty("Fee",txtPrice.getText().toString());

                            postSubject.addProperty("Key","NULL");
                            postSubject.addProperty("State","1");

                            // add UserShared
                            JsonObject userShare = new JsonObject();
                            userShare.addProperty("0","0");
                            userShare.addProperty("1","1");
                            postSubject.add("UserShare",userShare);

                            // add IdItemType
                            postSubject.addProperty("IdItemType",idItemType);
                            // add IdSubject



                        }else {
                            txtPrice.setError("Please input price");
                        }

                    }else {
                        txtPhone.setError("Please input phone");
                    }

                }catch (Exception ex){
                    Toast.makeText(UpdatePostSubjectActivity.this,"PostSubject: Date Time  Error",Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(UpdatePostSubjectActivity.this,"Please select Date and Time",Toast.LENGTH_LONG).show();
            }

        }else {
            txtSubjectName.setError("Please input subject name");
        }


        //txtDescription.setText(postSubject.toString());
        //Log.d("Json------",postSubject.toString());

    }

    public static interface UpdateSubjectInterface{
       public void reloadRecycleView();
    }

}
