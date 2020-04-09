package com.example.myygroserilist.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myygroserilist.Model.Grocery;
import com.example.myygroserilist.Util.Constans;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context ctx;
    public DatabaseHandler(@Nullable Context context) {
        super(context, Constans.DB_Name, null, Constans.DB_VERSION);
        this.ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Constans.Table_Name +
                "(" + Constans.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constans.KEY_GROCERY_ITEM + " TEXT, "
                + Constans.KEY_QTY_NUMBER + " TEXT, "
                + Constans.KEY_DATE_TIME + " LONG)";

        db.execSQL(CREATE_GROCERY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constans.Table_Name);

        onCreate(db);
    }
    public void addGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constans.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constans.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constans.KEY_DATE_TIME, java.lang.System.currentTimeMillis());

        db.insert(Constans.Table_Name,null,values);
        Log.d("Saved..","Database saving....");

    }

    public Grocery getGrocery(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT " + Constans.KEY_ID + "," + Constans.KEY_GROCERY_ITEM +
                "," + Constans.KEY_QTY_NUMBER + "," + Constans.KEY_DATE_TIME +
                " FROM " + Constans.Table_Name + " WHERE " + Constans.KEY_ID + "=?";

        Cursor cursor = db.rawQuery(sql, new String[] {String.valueOf(id)});

        if(cursor!=null)
            cursor.moveToFirst();
            Grocery grocery = new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constans.KEY_ID))));
            grocery.setName(cursor.getString(cursor.getColumnIndex(Constans.KEY_GROCERY_ITEM)));
            grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constans.KEY_QTY_NUMBER)));

            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constans.KEY_DATE_TIME)))
                    .getTime());
            grocery.setDateItemAdd(formatedDate);

            return  grocery;

    }
    public List<Grocery> getAllGroceries() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Grocery> groceryList = new ArrayList<>();
        String sqlAll = "Select * FROM " + Constans.Table_Name;
        Cursor cursor = db.rawQuery(sqlAll, null);

        if(cursor.moveToFirst()){
            do {
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constans.KEY_ID))));
                grocery.setName(cursor.getString(cursor.getColumnIndex(Constans.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constans.KEY_QTY_NUMBER)));

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constans.KEY_DATE_TIME)))
                        .getTime());
                grocery.setDateItemAdd(formatedDate);

                groceryList.add(grocery);
            }while(cursor.moveToNext());
        }
        return groceryList;
    }

    public int updateGrocery(Grocery grocery){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constans.KEY_GROCERY_ITEM,grocery.getName());
        values.put(Constans.KEY_QTY_NUMBER,grocery.getQuantity());
        values.put(Constans.KEY_DATE_TIME,java.lang.System.currentTimeMillis());



        return db.update(Constans.Table_Name,values,Constans.KEY_ID + "=?" ,
                new String[] {String.valueOf(grocery.getId())});

    }
    public void deleteGrocery(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constans.Table_Name,Constans.KEY_ID + "=? ",
                new String[] {String.valueOf(id)});
        db.close();
    }
    public int getGroceriesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlcount = "SELECT * FROM " + Constans.Table_Name;
        Cursor cursor = db.rawQuery(sqlcount, null);
        return cursor.getCount();
    }
}
