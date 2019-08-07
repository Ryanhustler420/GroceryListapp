package com.example.groceryappwithgupta.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.groceryappwithgupta.Model.Grocery;
import com.example.groceryappwithgupta.R;

public class MainActivity extends HelperActivity {

    private TextView addFirstText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // check db count and bypass activity
        byPassMainActivity();

        addFirstText = findViewById(R.id.firstAddText);
        ModifyFont(addFirstText);


       FloatingActionButton fab = findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // saving to database
                DialogForSave().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(groceryItem.getText()) && !TextUtils.isEmpty(quantity.getText())) {
                            saveGroceryToDB();
                        }
                    }
                });

            }
        });
    }

    private void saveGroceryToDB() {
        // create an object
        Grocery grocery = new Grocery();

        String newGrocery = groceryItem.getText().toString();
        int newGroceryQuantity = Integer.parseInt(quantity.getText().toString());

        grocery.setName(newGrocery);
        grocery.setQuantity(newGroceryQuantity);

        // Save to db
        db.addGrocery(grocery);
        dialog.dismiss();

        // next activity
        startActivity(new Intent(MainActivity.this, ListActivity.class));
    }
}
