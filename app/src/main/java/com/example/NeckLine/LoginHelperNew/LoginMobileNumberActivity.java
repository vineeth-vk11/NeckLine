package com.example.NeckLine.LoginHelperNew;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.NeckLine.MainActivity;
import com.example.NeckLine.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginMobileNumberActivity extends AppCompatActivity {

    EditText mobileNumber;
    Button login;

    String codeSent;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mobile_number);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
        FirebaseApp.initializeApp(getApplicationContext());

        mobileNumber = findViewById(R.id.login_phone_edit);
        login = findViewById(R.id.login_button);

        progressBar = findViewById(R.id.progressBar5);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                login.setEnabled(false);
                if(TextUtils.isEmpty(mobileNumber.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    login.setEnabled(true);
                }

                else if(mobileNumber.getText().toString().length() != 10){
                    Toast.makeText(LoginMobileNumberActivity.this,"Please enter correct phone number", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    login.setEnabled(true);
                }
                else {
                    sendOTP();

                }
            }
        });

    }

    private void sendOTP(){

        String phoneNumber = mobileNumber.getText().toString();

        String number = "+91" + phoneNumber;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks
        );
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(LoginMobileNumberActivity.this,"Please enter correct Number", Toast.LENGTH_SHORT).show();

            progressBar.setVisibility(View.INVISIBLE);
            login.setEnabled(true);
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;

            Intent intent = new Intent(LoginMobileNumberActivity.this,LoginOtpActivity.class);
            intent.putExtra("otp",s);
            intent.putExtra("phone", mobileNumber.getText().toString());
            startActivity(intent);
            finish();
        }

    };

}