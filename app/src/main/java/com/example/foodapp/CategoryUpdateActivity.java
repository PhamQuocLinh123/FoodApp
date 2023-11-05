package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CategoryUpdateActivity extends AppCompatActivity {
    private EditText edtCategoryName;
    private EditText edtCategoryDescription;
    private Button btnUpdateCategory;

    private FirebaseDatabase mDatabase;
    private DatabaseReference refCategory;
    private String categoryId; // Lấy ID của danh mục cần cập nhật.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_update);

        edtCategoryName = findViewById(R.id.edtCategoryName);
        edtCategoryDescription = findViewById(R.id.edtCategoryDescription);
        btnUpdateCategory = findViewById(R.id.btnUpdateCategory);

        mDatabase = FirebaseDatabase.getInstance();
        refCategory = mDatabase.getReference().child(Constant.Firebase.Category.KEY);

        // Nhận ID của danh mục cần cập nhật từ intent.
        categoryId = getIntent().getStringExtra("categoryId");

        // Sử dụng ID để truy vấn dữ liệu của danh mục cần cập nhật và hiển thị lên giao diện.
        refCategory.child(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    CategoryModel category = snapshot.getValue(CategoryModel.class);
                    if (category != null) {
                        edtCategoryName.setText(category.getName());
                        edtCategoryDescription.setText(category.getDescription());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi truy vấn dữ liệu.
            }
        });

        btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCategory();
            }
        });
    }

    private void updateCategory() {
        String name = edtCategoryName.getText().toString().trim();
        String description = edtCategoryDescription.getText().toString().trim();

        if (name.isEmpty()) {
            edtCategoryName.setError("Tên danh mục không được trống");
            edtCategoryName.requestFocus();
            return;
        }

        // Tạo một đối tượng CategoryModel mới với thông tin cập nhật.
        CategoryModel updatedCategory = new CategoryModel(categoryId, name, description, "photo_url_here");

        // Sử dụng ID của danh mục để cập nhật thông tin trong cơ sở dữ liệu.
        refCategory.child(categoryId).setValue(updatedCategory, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(CategoryUpdateActivity.this, "Cập nhật danh mục thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Xử lý lỗi khi cập nhật không thành công.
                    Toast.makeText(CategoryUpdateActivity.this, "Lỗi khi cập nhật danh mục: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
