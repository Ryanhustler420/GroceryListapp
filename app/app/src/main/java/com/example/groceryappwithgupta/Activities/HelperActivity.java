package com.example.groceryappwithgupta.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.groceryappwithgupta.Data.DatabaseHandler;

public class HelperActivity extends AppCompatActivity {
    public DatabaseHandler db;


    public void byPassMainActivity() {
        // Checks if database has some item

        if (db.getGroceriesCount() > 0) {
            startActivity(new Intent(this, ListActivity.class));
            finish(); // removes from stack! as simple as that
        }
    }

    public void ModityFont(TextView tv) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        tv.setTypeface(typeface);
    }
}
