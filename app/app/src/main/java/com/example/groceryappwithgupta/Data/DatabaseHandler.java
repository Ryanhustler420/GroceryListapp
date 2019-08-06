package com.example.groceryappwithgupta.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.groceryappwithgupta.Model.Grocery;
import com.example.groceryappwithgupta.Util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context ctx;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME +
                "(" + Constants.KEY_ID +  " INTEGER PRIMARY KEY," + Constants.KEY_GROCERY_ITEM +
                " TEXT," + Constants.KEY_QTY_NUMBER + " TEXT," + Constants.KEY_DATE_NAME + " LONG)";

        sqLiteDatabase.execSQL(CREATE_GROCERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    /*
    *   CRUD OPERATIONS: Create, Read, Update, Delete Methods....
    * */

    // Add Grocery
    public void addGrocery (Grocery grocery) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis()); // get current system time

        // Insert the row
        db.insert(Constants.TABLE_NAME, null, values);

        Log.d("Saved", "AddGrocery: Saved to DB");

    }

    // Get a Grocery
    public Grocery getGrocery(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER, Constants.KEY_DATE_NAME
        }, Constants.KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();

            Grocery grocery = new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
            grocery.setQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER))));

            // convert timestamp to something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formatDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
            grocery.setDateItemAdded(formatDate);
        }

        return null;
    }

    // Get all Groceries
    public List<Grocery> getAllGroceries() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Grocery> groceriesList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER, Constants.KEY_DATE_NAME
        }, null, null, null, null, Constants.KEY_DATE_NAME + " DESC");

        if (cursor.moveToFirst()) {
           do {

               Grocery grocery = new Grocery();
               grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
               grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
               grocery.setQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER))));

               // convert timestamp to something readable
               DateFormat dateFormat = DateFormat.getDateInstance();
               String formatDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
               grocery.setDateItemAdded(formatDate);

               // add to the groceryList
               groceriesList.add(grocery);

           } while (cursor.moveToNext());
        }

        return groceriesList;
    }

    // Update Grocery
    public int updateGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis()); // get current system time

        // updated row
        return db.update(Constants.TABLE_NAME, values,Constants.KEY_ID + "=?", new String[]{String.valueOf(grocery.getId())});
    }

    // Delete Grocery
    public void deleteGrocery(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?", new String[] { String.valueOf(id)});

        db.close();
    }

    // Get Count
    public int getGroceriesCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}
