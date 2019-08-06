package com.example.groceryappwithgupta.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.groceryappwithgupta.Model.Grocery;
import com.example.groceryappwithgupta.Util.Constants;

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
    public void AddGrocery (Grocery grocery) { }

    // Get a Grocery
    private Grocery getGrocery(int id) { return null; }

    // Get all Groceries
    public List<Grocery> getAllGroceries() { return null; }

    // Update Grocery
    public int updateGrocery(Grocery grocery) { return 0; }

    // Delete Grocery
    public void deleteGrocery(int id) { }

    // Get Count
    public int getGroceriesCount() { return 0; }
}
