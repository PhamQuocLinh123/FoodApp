package com.example.foodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CategoryAddActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editDescription;
    private ShapeableImageView imagePhoto;
    private FloatingActionButton buttonSave;
    //
    private FirebaseDatabase mDatabase;
    private DatabaseReference refCategory;
    private CategoryModel categoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        editName = findViewById(R.id.editName);
        editDescription = findViewById(R.id.editDescription);
        imagePhoto = findViewById(R.id.imagePhoto);
        buttonSave = findViewById(R.id.buttonSave);

        mDatabase = FirebaseDatabase.getInstance();
        refCategory = mDatabase.getReference()
                        .child(Constant.Firebase.Category.KEY);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference refCategoryId = refCategory.push();
                categoryModel = new CategoryModel(
                        refCategoryId.getKey(),
                        editName.getText().toString(),
                        editDescription.getText().toString(),
                        ""
                );
                refCategoryId.setValue(categoryModel);
            }
        });
    }
}