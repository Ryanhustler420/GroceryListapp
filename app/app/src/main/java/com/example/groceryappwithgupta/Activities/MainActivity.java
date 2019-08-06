package com.example.groceryappwithgupta.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.groceryappwithgupta.Data.DatabaseHandler;
import com.example.groceryappwithgupta.Model.Grocery;
import com.example.groceryappwithgupta.R;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText groceryItem;
    private EditText quantity;
    private Button saveBtn;
    private TextView addFirstText;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHandler(this);

        addFirstText = findViewById(R.id.firstAddText);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        addFirstText.setTypeface(typeface);


       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
