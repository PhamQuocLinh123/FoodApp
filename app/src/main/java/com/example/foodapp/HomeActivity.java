package com.example.foodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.cardview.widget.CardView;
import android.widget.ImageView;

public class HomeActivity extends Activity {
    CardView clothingCard;
    ImageView clothingImage; // Declare ImageView
    ImageView homeImage; // Declare ImageView for "Giới Thiệu"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        clothingCard = findViewById(R.id.clothingCard);
        clothingImage = findViewById(R.id.clothingImage); // Initialize the ImageView for "Menu"
        homeImage = findViewById(R.id.homeImage); // Initialize the ImageView for "Giới Thiệu"

        // Set an OnClickListener for the clothingImage
        clothingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the CategoryActivity when "Menu" image is clicked
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        // Set an OnClickListener for the homeImage ("Giới Thiệu")
        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Gioithieuactivity when "Giới Thiệu" image is clicked
                Intent intent = new Intent(HomeActivity.this, GioithieuActivity.class);
                startActivity(intent);
            }
        });

        clothingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click on clothingCard here if needed
            }
        });
    }
}
