package com.example.groceryappwithgupta.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.groceryappwithgupta.Data.DatabaseHandler;
import com.example.groceryappwithgupta.Model.Grocery;
import com.example.groceryappwithgupta.R;
import com.example.groceryappwithgupta.UI.RecyclerViewAdaptor;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private DatabaseHandler db;
    RecyclerView recyclerView;
    List<Grocery> groceryList;
    List<Grocery> listItems;
    RecyclerViewAdaptor recyclerViewAdaptor;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;

    EditText groceryItem;
    EditText quantity;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialog();
            }
        });

        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList = new ArrayList<>();
        listItems = new ArrayList<>();

        // Get items from database
        groceryList = db.getAllGroceries();

        for (Grocery c: groceryList) {
            Grocery grocery = new Grocery();
            grocery.setName(c.getName());
            grocery.setQuantity(c.getQuantity());
            grocery.setId(c.getId());
            grocery.setDateItemAdded(c.getDateItemAdded());

            listItems.add(grocery);
        }

        recyclerViewAdaptor = new RecyclerViewAdaptor(this, listItems);
        recyclerView.setAdapter(recyclerViewAdaptor);
        recyclerViewAdaptor.notifyDataSetChanged(); // something has change. which will handle by recycleView we don't have to do anything

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

        // reassigning listItems To recycleView
        listItems.add(grocery);
        recyclerViewAdaptor = new RecyclerViewAdaptor(this, listItems);
        recyclerView.setAdapter(recyclerViewAdaptor);
        recyclerViewAdaptor.notifyDataSetChanged();

        dialog.dismiss();
    }

}
