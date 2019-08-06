package com.example.groceryappwithgupta.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.groceryappwithgupta.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView itemName;
    private TextView quantity;
    private TextView dateAdded;
    private int groceryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        itemName = findViewById(R.id.detailActivity_ItemName);
        quantity = findViewById(R.id.detailActivity_quantity);
        dateAdded = findViewById(R.id.detailActivity_dateAdded);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            itemName.setText(bundle.getString("name"));
            quantity.setText(bundle.getString("quantity"));
            dateAdded.setText(bundle.getString("date"));
            groceryId = bundle.getInt("id");
        }
    }
}
