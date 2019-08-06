package com.example.groceryappwithgupta.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.groceryappwithgupta.Data.DatabaseHandler;
import com.example.groceryappwithgupta.Model.Grocery;
import com.example.groceryappwithgupta.R;
import com.example.groceryappwithgupta.UI.RecyclerViewAdaptor;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private RecyclerView recyclerView;
    private List<Grocery> groceryList;
    private List<Grocery> listItems;
    private RecyclerViewAdaptor recyclerViewAdaptor;

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
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
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

}
