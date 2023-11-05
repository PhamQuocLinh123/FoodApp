package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerCategory;
    private FloatingActionButton buttonAdd;
    //
    private FirebaseDatabase mDatabase;
    private DatabaseReference refCategory;
    //
    private ArrayList<CategoryModel> mCategories;
    private CategoryRvAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerCategory = findViewById(R.id.recyclerCategory);
        buttonAdd = findViewById(R.id.buttonAdd);

        mDatabase = FirebaseDatabase.getInstance();
        refCategory = mDatabase.getReference()
                .child(Constant.Firebase.Category.KEY);
        mCategories = new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CategoryAddActivity.class);
                startActivity(intent);
            }
        });

        getCategories();
    }

    private void getCategories() {
        refCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mCategories.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    CategoryModel category = data.getValue(CategoryModel.class);
                    mCategories.add(category);
                }
                if (mCategories != null) {
                    mAdapter = new CategoryRvAdapter(
                            R.layout.layout_category_rv_item,
                            mCategories
                    );
                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                            getApplicationContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                    );
                    recyclerCategory.setLayoutManager(layoutManager);
                    recyclerCategory.setAdapter(mAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        CategoryModel category = mCategories.get(mAdapter.getPosition());
        DatabaseReference refCategoryId = mDatabase.getReference()
                .child(Constant.Firebase.Category.KEY)
                .child(category.getId());

        if (item.getItemId() == R.id.menuEdit) {

            return true;
        } else if (item.getItemId() == R.id.menuDelete) {
            AlertDialog dialog = new AlertDialog.Builder(CategoryActivity.this)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            refCategoryId.removeValue();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setTitle("Warning")
                    .setMessage("Do you want delete ...?")
                    .show();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
}