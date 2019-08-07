package com.example.groceryappwithgupta.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.example.groceryappwithgupta.Model.Grocery;
import com.example.groceryappwithgupta.R;
import com.example.groceryappwithgupta.UI.RecyclerViewAdaptor;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends HelperActivity {

    RecyclerView recyclerView;
    List<Grocery> groceryList;
    List<Grocery> listItems;
    RecyclerViewAdaptor recyclerViewAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList = new ArrayList<>();
        listItems = new ArrayList<>();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        initViewList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (db.getGroceriesCount() <= 0) {
            startActivity(new Intent(ListActivity.this, MainActivity.class));
            finish();
        } else {
            initViewList();
        }
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

        // reassigning listItems To recycleView
        initViewList();
    }

    private void initViewList() {

        groceryList.clear();
        listItems.clear();

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

        recyclerViewAdaptor = new RecyclerViewAdaptor(ListActivity.this, listItems);
        recyclerView.setAdapter(recyclerViewAdaptor);
        recyclerViewAdaptor.notifyDataSetChanged(); // something has change. which will handle by recycleView we don't have to do anything
    }

}
