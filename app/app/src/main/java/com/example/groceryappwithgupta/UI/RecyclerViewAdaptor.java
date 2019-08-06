package com.example.groceryappwithgupta.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.groceryappwithgupta.Model.Grocery;
import com.example.groceryappwithgupta.R;

import java.util.List;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {

    private Context context;
    private List<Grocery> groceriesItems;

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

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdaptor.ViewHolder viewHolder, int position) {
        // this is where everything is bind together.
        Grocery grocery = groceriesItems.get(position);

        viewHolder.groceryItemName.setText(grocery.getName());
        viewHolder.quantity.setText(String.valueOf(grocery.getQuantity()));
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
        int each_Item_ID;

        ViewHolder(View view, Context ctx) {
            super(view);
            context = ctx;

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
                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.list_row_edit_btn:

                    break;
                case R.id.list_row_delete_btn:

                    break;
            }
        }
    }
}
