package com.jundat95.tinhngo.sharelocation.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.jundat95.tinhngo.sharelocation.R;
import com.vansuita.pickimage.PickImageDialog;
import com.vansuita.pickimage.PickSetup;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends AppCompatActivity  {

    @BindView(R.id.txtBase64) TextView txtBase64;
    @BindView(R.id.imageBase64) ImageView imageBase64;
    private final int REQUEST_CODE_PICKER = 2000;
    private final int REQUEST_CODE_CAMERA = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        ButterKnife.bind(this);
        //base64Init();
    }

    @OnClick(R.id.btnPikerImage) public void PickerImageClick(){
        PickImageDialog.on(DemoActivity.this,new PickSetup()).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {
                 if(r.getError() == null){
                     imageBase64.setImageBitmap(r.getBitmap());
                     base64Init(r.getBitmap());
                 }else {
                     Toast.makeText(DemoActivity.this,"Hinh Anh Qua Lon",Toast.LENGTH_LONG);
                 }
            }
        });
    }

    public void base64Init(Bitmap bm){

      // Encode image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap =  bm;
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes,Base64.DEFAULT);

        txtBase64.setText(imageString);

       // Decode base64 to image
//        imageBytes = Base64.decode(imageString,Base64.DEFAULT);
//        Bitmap decodeImage  = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
//        imageBase64.setImageBitmap(decodeImage);

    }

}

