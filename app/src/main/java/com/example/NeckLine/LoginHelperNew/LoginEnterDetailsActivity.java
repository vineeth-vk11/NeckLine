package com.example.NeckLine.LoginHelperNew;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.NeckLine.MainActivity;
import com.example.NeckLine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LoginEnterDetailsActivity extends AppCompatActivity {

    EditText fullName, email, age, handLength, handRadius, shoulderLength, chestLength, frontDepth, backDepth;
    Button save;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_enter_details);

        FirebaseApp.initializeApp(getApplicationContext());

        db = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.name_edit_text);
        email = findViewById(R.id.email_edit);
        age = findViewById(R.id.age_edit_text);
        handLength = findViewById(R.id.handLength_edit_text);
        handRadius = findViewById(R.id.handRadius_edit_text);
        shoulderLength = findViewById(R.id.shoulderLength_edit_text);
        chestLength = findViewById(R.id.chestLength_edit_text);
        frontDepth = findViewById(R.id.frontDepth_edit_text);
        backDepth = findViewById(R.id.backDepth_edit_text);
        save = findViewById(R.id.signup_button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enteredName = fullName.getText().toString();
                String enteredEmail = email.getText().toString();
                String enteredAge = age.getText().toString();
                String enteredHandLength = handLength.getText().toString();
                String enteredHandRadius = handRadius.getText().toString();
                String enteredShoulderLength = shoulderLength.getText().toString();
                String enteredChestLength = chestLength.getText().toString();
                String enteredFrontDepth = frontDepth.getText().toString();
                String enteredBackDepth = backDepth.getText().toString();

                if(TextUtils.isEmpty(enteredName)){

                }
                else if(TextUtils.isEmpty(enteredEmail)){

                }
                else if(TextUtils.isEmpty(enteredAge)){

                }
                else if(TextUtils.isEmpty(enteredHandLength)){

                }
                else if(TextUtils.isEmpty(enteredHandRadius)){

                }
                else if(TextUtils.isEmpty(enteredShoulderLength)){

                }
                else if(TextUtils.isEmpty(enteredChestLength)){

                }
                else if(TextUtils.isEmpty(enteredFrontDepth)){

                }
                else if(TextUtils.isEmpty(enteredBackDepth)){

                }
                else{

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("name",enteredName);
                    data.put("email",enteredEmail);
                    data.put("age",enteredAge);
                    data.put("handLength",enteredHandLength);
                    data.put("handRadius",enteredHandRadius);
                    data.put("shoulderLength",enteredShoulderLength);
                    data.put("chestLength", enteredChestLength);
                    data.put("frontDepth", enteredFrontDepth);
                    data.put("backDepth", enteredBackDepth);

                    db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                            .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

            }
        });

    }
}