package com.example.foodapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CategoryAddActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editDescription;
    private ShapeableImageView imagePhoto;
    private FloatingActionButton buttonSave;
    private final int SELECT_PHOTO = 0;
    private Uri pathFromDevice;
    private FirebaseDatabase mDatabase;
    private FirebaseStorage mStorage;
    private StorageReference refImagesCategory;
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
        mStorage = FirebaseStorage.getInstance();
        refImagesCategory = mStorage.getReference()
                .child("images")
                .child(Constant.Firebase.Category.KEY);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference refCategoryId = refCategory.push();

                refImagesCategory
                        .child(refCategoryId.getKey())
                        .putFile(pathFromDevice)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                categoryModel = new CategoryModel(
                                        refCategoryId.getKey(),
                                        editName.getText().toString(),
                                        editDescription.getText().toString(),
                                        "images/"+Constant.Firebase.Category.KEY+"/"+refCategoryId.getKey()
                                );
                                refCategoryId.setValue(categoryModel);

                                // Hiển thị thông báo "Thêm thành công"
                                Toast.makeText(CategoryAddActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                                // Chuyển về CategoryActivity sau khi lưu thành công
                                Intent intent = new Intent(CategoryAddActivity.this, CategoryActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });

        imagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "select a photo"),
                        SELECT_PHOTO
                );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO
                && resultCode == RESULT_OK && data != null) {
            pathFromDevice = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(),
                        pathFromDevice
                );
                imagePhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
