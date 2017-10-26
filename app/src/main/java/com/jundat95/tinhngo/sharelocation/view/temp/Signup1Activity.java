package com.jundat95.tinhngo.sharelocation.view.temp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jundat95.tinhngo.sharelocation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Signup1Activity extends AppCompatActivity {

    private static final String TAG = "SignActivity";

    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.phone_number)
    EditText inputPhoneNumber;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_re_password)
    EditText inputRePassword;
    @BindView(R.id.btn_signup)
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_1);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signup)
    public void signupClick() {
        if (!validate()) {
            signFailed();
            return;
        }

        btnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Signup1Activity.this,
                R.style.AppTheme_Blue_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        // Implements Signup

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onSignSuccess();
                progressDialog.dismiss();
            }
        }, 3000);

    }

    @OnClick(R.id.link_login)
    public void linkLogin(){
        // Finish the registration screen and return to the Login activity
        Intent intent = new Intent(getApplicationContext(),Login1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    private void onSignSuccess() {
        btnSignup.setEnabled(true);
        //setResult(RESULT_OK, null);
        finish();
    }

    private void signFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btnSignup.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String phoneNumber = inputPhoneNumber.getText().toString();
        String password = inputPassword.getText().toString();
        String rePassword = inputRePassword.getText().toString();

        if(name.isEmpty() || name.length() < 4){
            inputName.setError("at least 4 characters");
            valid = false;
        }else {
            inputName.setError(null);
        }

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError("enter a valid email address");
            valid = false;
        }else{
            inputEmail.setError(null);
        }

        if (phoneNumber.isEmpty() || phoneNumber.length()!=10) {
            inputPhoneNumber.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            inputPhoneNumber.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            inputPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 4 || rePassword.length() > 10 || !(rePassword.equals(password))) {
            inputRePassword.setError("Password Do not match");
            valid = false;
        } else {
            inputRePassword.setError(null);
        }

        return valid;
    }
}
