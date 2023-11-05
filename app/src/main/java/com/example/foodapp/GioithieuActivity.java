package com.example.foodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GioithieuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gioithieu);

        Button menuButtonBottom = findViewById(R.id.menuButtonBottom);
        menuButtonBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to CategoryActivity when the button is clicked
                Intent intent = new Intent(GioithieuActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
