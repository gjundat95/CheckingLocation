package com.jundat95.tinhngo.sharelocation.view.temp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jundat95.tinhngo.sharelocation.R;
import com.jundat95.tinhngo.sharelocation.repository.local.fakeData.UserFake;
import com.jundat95.tinhngo.sharelocation.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login1Activity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private  static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email) EditText inputEmail;
    @BindView(R.id.input_password) EditText inputPassword;
    @BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.link_signup) TextView linkSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1);
        ButterKnife.bind(this);
        //fakeData();

    }

    @OnClick(R.id.btn_login)
    public void loginClick(View view) {
        Log.d(TAG,"Login");
        if(!validate()){
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(Login1Activity.this,R.style.AppTheme_Blue_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        // Authenticating here
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(UserFake.newInstance().Login(email, password)) {
                    onLoginSuccess();
                } else {
                    Toast.makeText(Login1Activity.this,"Tai khoan dang nhap khong dung",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                btnLogin.setEnabled(true);
            }
        },3000);

    }


    @OnClick(R.id.link_signup)
    public void linkSignupClick() {
        //Start the Signup activity
        Intent intent = new Intent(Login1Activity.this, Signup1Activity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    private void onLoginSuccess() {

        Intent intent = new Intent(Login1Activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (email.isEmpty()) {
            inputEmail.setError("enter a username");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            inputPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        return valid;
    }

    private void fakeData() {
        inputEmail.setText("tinhngo");
        inputPassword.setText("123456");
    }
}
