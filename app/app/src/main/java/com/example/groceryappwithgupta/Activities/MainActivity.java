package com.example.groceryappwithgupta.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.groceryappwithgupta.Data.DatabaseHandler;
import com.example.groceryappwithgupta.Model.Grocery;
import com.example.groceryappwithgupta.R;

public class MainActivity extends HelperActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText groceryItem;
    private EditText quantity;
    private Button saveBtn;
    private TextView addFirstText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHandler(this);

        // check db count and bypass activity
        byPassMainActivity();

        addFirstText = findViewById(R.id.firstAddText);
        ModityFont(addFirstText);


       FloatingActionButton fab = findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialog();
            }
        });
    }

    private void createPopUpDialog() {
        dialogBuilder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.popup, null);

        groceryItem = view.findViewById(R.id.popup_grocery_item_edit_text);
        quantity = view.findViewById(R.id.popup_grocery_qty_edit_text);
        saveBtn = view.findViewById(R.id.popup_save_btn);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        // actions to be perform after clicking save button

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save to db
                if (!TextUtils.isEmpty(groceryItem.getText()) && !TextUtils.isEmpty(quantity.getText())) {
                    saveGroceryToDB(view);
                }
                // TODO: Go to next screen
            }
        });
    }

    private void saveGroceryToDB(View v) {

        // create an object
        Grocery grocery = new Grocery();

        String newGrocery = groceryItem.getText().toString();
        int newGroceryQuantity = Integer.parseInt(quantity.getText().toString());

        grocery.setName(newGrocery);
        grocery.setQuantity(newGroceryQuantity);

        // Save to db
        db.addGrocery(grocery);
        Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();
        Log.d("Item Added ID: ", String.valueOf(db.getGroceriesCount()));
        dialog.dismiss();

        // next activity

        startActivity(new Intent(MainActivity.this, ListActivity.class));
    }
}
