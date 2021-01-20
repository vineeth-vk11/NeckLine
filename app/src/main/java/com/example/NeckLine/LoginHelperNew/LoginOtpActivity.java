package com.example.NeckLine.LoginHelperNew;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.NeckLine.MainActivity;
import com.example.NeckLine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class LoginOtpActivity extends AppCompatActivity {

    EditText otp;
    String sentOTP;
    String mobile;

    Button validate;

    FirebaseAuth firebaseAuth;

    ProgressBar progressBar;
    TextView time;

    Button resend;

    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        FirebaseApp.initializeApp(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        sentOTP = intent.getStringExtra("otp");
        mobile = intent.getStringExtra("phone");

        otp = findViewById(R.id.login_otp_edit);
        validate = findViewById(R.id.validate_button);
        time = findViewById(R.id.textView24);
        resend = findViewById(R.id.resend);

        progressBar = findViewById(R.id.progressBar7);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                validate.setEnabled(false);
                if(TextUtils.isEmpty(otp.getText().toString())){
                    Toast.makeText(LoginOtpActivity.this,"Please enter OTP", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    validate.setEnabled(true);
                }
                else {
                    verifyOTPSign();
                }
            }
        });

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText("Please wait: " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                resend.setVisibility(View.VISIBLE);
                time.setText("done!");
            }
        }.start();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             sendOTP();
             resend.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void sendOTP(){

        String number = "+91" + mobile;
        sentOTP = "";
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
            Toast.makeText(LoginOtpActivity.this,"OTP sent", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(LoginOtpActivity.this,"Please enter correct Number", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            sentOTP = s;
            Toast.makeText(getApplicationContext(), "OTP resend successful", Toast.LENGTH_SHORT).show();
        }

    };

    private void verifyOTPSign(){
        code = otp.getText().toString();
        Log.i("code",code);
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(sentOTP,code);
        signInWithPhoneAuthCredential(phoneAuthCredential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if(firebaseUser != null) {

                        FirebaseFirestore db;
                        db = FirebaseFirestore.getInstance();

                        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot documentSnapshot = task.getResult();

                                if(documentSnapshot.exists()){
                                    Intent intent = new Intent(LoginOtpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Intent intent = new Intent(LoginOtpActivity.this, LoginEnterDetailsActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(LoginOtpActivity.this, "Please enter correct otp", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    validate.setEnabled(true);
                    return;
                }
            }
        });
    }
}