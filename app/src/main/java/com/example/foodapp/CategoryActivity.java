package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
        refCategory = mDatabase.getReference().child(Constant.Firebase.Category.KEY);
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
                // Handle database error here.
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
            Intent intent = new Intent(CategoryActivity.this, CategoryUpdateActivity.class);
            startActivity(intent);
            // Handle the edit action.
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
                            // Handle "No" action.
                        }
                    })
                    .setTitle("Warning")
                    .setMessage("Do you want to delete ...?")
                    .show();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission if needed.
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Call the searchCategories method with the new text.
                searchCategories(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchCategories(String keyword) {
        // Convert the keyword to lowercase for case-insensitive search.
        String lowerKeyword = keyword.toLowerCase();
        refCategory.orderByChild(Constant.Firebase.Category.NAME)
                .startAt(lowerKeyword)
                .endAt(lowerKeyword + "\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mCategories.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            CategoryModel category = data.getValue(CategoryModel.class);
                            mCategories.add(category);
                        }
                        if (mCategories != null) {
                            mAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed.
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error here.
                    }
                });
    }
}