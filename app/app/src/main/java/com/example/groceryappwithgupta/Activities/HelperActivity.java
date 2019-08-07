package com.example.groceryappwithgupta.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.groceryappwithgupta.Data.DatabaseHandler;
import com.example.groceryappwithgupta.R;

public class HelperActivity extends AppCompatActivity {
    protected DatabaseHandler db;

    protected AlertDialog.Builder dialogBuilder;
    protected AlertDialog dialog;
    protected EditText groceryItem;
    protected EditText quantity;
    protected Button saveBtn;
    protected TextView popupTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHandler(this);
    }

    public void byPassMainActivity() {
        // Checks if database has some item

        if (db.getGroceriesCount() > 0) {
            startActivity(new Intent(this, ListActivity.class));
            finish(); // removes from stack! as simple as that
        }
    }

    public void ModifyFont(TextView tv) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        tv.setTypeface(typeface);
    }

    public Button DialogForSave() {
        dialogBuilder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.popup, null);

        groceryItem = view.findViewById(R.id.popup_grocery_item_edit_text);
        ModifyFont(groceryItem);
        quantity = view.findViewById(R.id.popup_grocery_qty_edit_text);
        ModifyFont(quantity);
        saveBtn = view.findViewById(R.id.popup_save_btn);
        ModifyFont(saveBtn);
        popupTitle = view.findViewById(R.id.popup_title);
        ModifyFont(popupTitle);


        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        // actions to be perform after clicking save button
        return saveBtn;
    }
}
