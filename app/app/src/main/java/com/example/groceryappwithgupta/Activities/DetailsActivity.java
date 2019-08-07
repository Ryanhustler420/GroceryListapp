package com.example.groceryappwithgupta.Activities;

import android.app.AlertDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.groceryappwithgupta.Data.DatabaseHandler;
import com.example.groceryappwithgupta.Model.Grocery;
import com.example.groceryappwithgupta.R;

public class DetailsActivity extends AppCompatActivity {

    TextView itemName;
    TextView quantity;
    TextView dateAdded;
    Button editBtn;
    Button deleteBtn;
    private DatabaseHandler db;
    private int groceryId;

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        db = new DatabaseHandler(this);

        itemName = findViewById(R.id.detailActivity_ItemName);
        quantity = findViewById(R.id.detailActivity_quantity);
        dateAdded = findViewById(R.id.detailActivity_dateAdded);

        editBtn = findViewById(R.id.detailActivity_edit_btn);
        deleteBtn = findViewById(R.id.detailActivity_delete_btn);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            itemName.setText(bundle.getString("name"));
            quantity.setText(bundle.getString("quantity"));
            dateAdded.setText(bundle.getString("date"));
            groceryId = bundle.getInt("id");
        }

        final Grocery grocery = new Grocery();
        grocery.setName(itemName.getText().toString());
        grocery.setDateItemAdded(dateAdded.getText().toString());
        grocery.setId(groceryId);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editItem(grocery);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(grocery);
            }
        });
    }

    private void deleteItem(final Grocery grocery) {
        alertDialogBuilder = new AlertDialog.Builder(this);
        inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.confirmation_dialog, null);

        Button noButton = view.findViewById(R.id.confirmNoButton);
        Button yesButton = view.findViewById(R.id.confirmYesButton);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // close the dialog boc
                dialog.dismiss();
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete the item
                db.deleteGrocery(grocery.getId());
                dialog.dismiss();
                finish();
            }
        });
    }

    private void editItem(final Grocery grocery) {
        alertDialogBuilder = new AlertDialog.Builder(this);

        inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popup, null);

        final EditText groceryItem = view.findViewById(R.id.popup_grocery_item_edit_text);
        final EditText quantity = view.findViewById(R.id.popup_grocery_qty_edit_text);
        final Button saveButton = view.findViewById(R.id.popup_save_btn);
        final TextView title = view.findViewById(R.id.popup_title);

        title.setText("Edit: " + grocery.getName() + " to:-");

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(groceryItem.getText().toString()) && !TextUtils.isEmpty(quantity.getText().toString())) {
                    // Update an Item
                    grocery.setName(groceryItem.getText().toString());
                    grocery.setQuantity(Integer.valueOf(quantity.getText().toString()));

                    db.updateGrocery(grocery);
                    dialog.dismiss();
                    finish();
                } else {
                    Snackbar.make(view, "Add Grocery and Quantity", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
