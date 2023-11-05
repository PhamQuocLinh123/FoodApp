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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CategoryEditActivity extends AppCompatActivity {

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
    private String categoryId;
    private CategoryModel categoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        editName = findViewById(R.id.editName);
        editDescription = findViewById(R.id.editDescription);
        imagePhoto = findViewById(R.id.imagePhoto);
        buttonSave = findViewById(R.id.buttonSave);

        mDatabase = FirebaseDatabase.getInstance();
        refCategory = mDatabase.getReference().child(Constant.Firebase.Category.KEY);
        mStorage = FirebaseStorage.getInstance();
        refImagesCategory = mStorage.getReference()
                .child("images")
                .child(Constant.Firebase.Category.KEY);

        categoryId = getIntent().getStringExtra("category_id");

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCategory();
            }
        });

        imagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tương tự như trong CategoryAddActivity, bạn có thể thêm mã để chọn hình ảnh từ thiết bị.
                // Điều này có thể giúp bạn cập nhật hình ảnh của danh mục.
                // Chú ý rằng bạn cần xử lý việc tải hình ảnh và lưu đường dẫn trong cơ sở dữ liệu tại đây.
            }
        });

        // Gọi phương thức để hiển thị thông tin danh mục hiện tại và hiển thị hình ảnh (nếu có).
        populateCategoryDetails();
    }

    private void populateCategoryDetails() {
        refCategory.child(categoryId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    categoryModel = snapshot.getValue(CategoryModel.class);
                    if (categoryModel != null) {
                        editName.setText(categoryModel.getName());
                        editDescription.setText(categoryModel.getDescription());

                        // Tùy chỉnh để hiển thị hình ảnh (nếu có) trong ImageView.
                        // Sử dụng thư viện ảnh như Picasso hoặc Glide để tải và hiển thị hình ảnh từ đường dẫn
                        // categoryModel.getImageUrl() trong ImageView (imagePhoto).
                        // Ví dụ: Picasso.get().load(categoryModel.getImageUrl()).into(imagePhoto);
                    }
                }
            } else {
                Toast.makeText(this, "Failed to retrieve category details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCategory() {
        String name = editName.getText().toString();
        String description = editDescription.getText().toString();

        if (categoryModel != null) {
            // Cập nhật thông tin danh mục trong đây.
            categoryModel.setName(name);
            categoryModel.setDescription(description);

            // Sau đó, bạn cần cập nhật thông tin danh mục vào cơ sở dữ liệu.
            refCategory.child(categoryId).setValue(categoryModel);

            // Hiển thị thông báo "Danh mục đã được cập nhật thành công".
            Toast.makeText(this, "Danh mục đã được cập nhật thành công", Toast.LENGTH_SHORT).show();

            // Đóng Activity sau khi cập nhật.
            finish();
        }
    }
}
