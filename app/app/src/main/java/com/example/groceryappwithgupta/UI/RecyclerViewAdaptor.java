package com.example.groceryappwithgupta.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.groceryappwithgupta.Activities.DetailsActivity;
import com.example.groceryappwithgupta.Activities.ListActivity;
import com.example.groceryappwithgupta.Activities.MainActivity;
import com.example.groceryappwithgupta.Data.DatabaseHandler;
import com.example.groceryappwithgupta.Model.Grocery;
import com.example.groceryappwithgupta.R;

import java.util.List;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {

    private Context context;
    private List<Grocery> groceriesItems;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdaptor(Context ctx, List<Grocery> groceriesItems) {
        this.context = ctx;
        this.groceriesItems = groceriesItems;
    }

    @NonNull
    @Override
    public RecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdaptor.ViewHolder viewHolder, int position) {
        // this is where everything is bind together.
        Grocery grocery = groceriesItems.get(position);

        viewHolder.groceryItemName.setText(grocery.getName());
        viewHolder.quantity.setText("Qty:" + grocery.getQuantity());
        viewHolder.dateAdded.setText(grocery.getDateItemAdded());
    }

    @Override
    public int getItemCount() {
        return groceriesItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView groceryItemName;
        TextView quantity; // This should be int 'type'
        TextView dateAdded;
        Button editButton;
        Button deleteButton;

        ViewHolder(View view, Context ctx) {
            super(view);
            context = ctx;
            // TODO: modify text later
            groceryItemName = view.findViewById(R.id.list_row_cardView_name);
            quantity = view.findViewById(R.id.list_row_cardView_quantity); // this should be @int type
            dateAdded = view.findViewById(R.id.list_row_date_added);

            editButton = view.findViewById(R.id.list_row_edit_btn);
            deleteButton = view.findViewById(R.id.list_row_delete_btn);

            deleteButton.setOnClickListener(this);
            editButton.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // go to next screen
                    int position = getAdapterPosition();

                    Grocery grocery = groceriesItems.get(position);

                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("name", grocery.getName());
                    intent.putExtra("quantity", "Qty:" + grocery.getQuantity());
                    intent.putExtra("id", grocery.getId());
                    intent.putExtra("date", grocery.getDateItemAdded());

                    // context has activity methods! just chill
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            Grocery grocery = groceriesItems.get(position);

            switch (view.getId()) {
                case R.id.list_row_edit_btn:
                    editItem(grocery, position);
                    break;
                case R.id.list_row_delete_btn:
                    deleteItem(grocery.getId(), position);
                    break;
            }
        }
    }

    private void editItem(final Grocery grocery, final int adaptorPosition) {
        alertDialogBuilder = new AlertDialog.Builder(context);

        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.popup, null);

        final EditText groceryItem = view.findViewById(R.id.popup_grocery_item_edit_text);
        final EditText quantity = view.findViewById(R.id.popup_grocery_qty_edit_text);
        final Button saveButton = view.findViewById(R.id.popup_save_btn);
        final TextView title = view.findViewById(R.id.popup_title);
        title.setText("Edit Grocery");

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(context);

                // Update an Item
                grocery.setName(groceryItem.getText().toString());
                grocery.setQuantity(Integer.valueOf(quantity.getText().toString()));

                if (!TextUtils.isEmpty(groceryItem.getText().toString()) && !TextUtils.isEmpty(quantity.getText().toString())) {
                   db.updateGrocery(grocery);
                   notifyItemChanged(adaptorPosition, grocery);
                } else {
                    Snackbar.make(view, "Add Grocery and Quantity", Snackbar.LENGTH_LONG).show();
                }

                dialog.dismiss();
            }
        });
    }

    private void deleteItem(final int id, final int adaptorPosition) {
        // Create AlertDialog
        alertDialogBuilder = new AlertDialog.Builder(context);
        inflater = LayoutInflater.from(context);
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
                DatabaseHandler db = new DatabaseHandler(context);
                db.deleteGrocery(id);

                groceriesItems.remove(adaptorPosition);
                notifyItemRemoved(adaptorPosition);

                dialog.dismiss();
                bypassActivity(context);
            }
        });
    }

    private void bypassActivity (Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        if (db.getGroceriesCount() <= 0) {
            context.startActivity(new Intent(context, MainActivity.class));
            ((Activity)context).finish();
        }
    }
}
