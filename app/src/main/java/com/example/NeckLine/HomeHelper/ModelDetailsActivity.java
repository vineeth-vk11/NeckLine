package com.example.NeckLine.HomeHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.NeckLine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ModelDetailsActivity extends AppCompatActivity {

    ImageSlider imageSlider;
    List<SlideModel> slideModels = new ArrayList<>();
    TextView itemName;

    String id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_details);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");

        imageSlider = findViewById(R.id.imageSlider2);
        itemName = findViewById(R.id.itemNameDetails);

        itemName.setText(name);

        FirebaseApp.initializeApp(getApplicationContext());

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        db.collection("models").document(id).collection("photos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                slideModels.clear();

                for(DocumentSnapshot documentSnapshot: task.getResult()){

                    String imageUrl = documentSnapshot.getString("image");
                    String imageTitle = "";
                    SlideModel slideModel = new SlideModel(imageUrl, imageTitle, ScaleTypes.CENTER_CROP);
                    slideModels.add(slideModel);

                }

                imageSlider.setImageList(slideModels,ScaleTypes.CENTER_CROP);

            }
        });
    }
}