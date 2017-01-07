package com.example.pari.olxelem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "products.db";
    public static final String TABLE_NAME = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_SELLER = "seller";
    public static final String COLUMN_IMAGE = "image";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table products " +
                        "(id integer primary key , name text , type text , price text , description text , seller text , image BLOB)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    public boolean insertProduct (Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", item.getName());
        contentValues.put("type", item.getType());
        contentValues.put("price", item.getPrice());
        contentValues.put("description", item.getDesc());
        contentValues.put("seller", item.getSeller());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        item.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        contentValues.put("image",byteArray);
        db.insert("products", null, contentValues);
        db.close();
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from products where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateProduct (Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", item.getName());
        contentValues.put("type", item.getType());
        contentValues.put("price", item.getPrice());
        contentValues.put("desc", item.getDesc());
        contentValues.put("seller", item.getSeller());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        item.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        contentValues.put("image",byteArray);
        db.update("products", contentValues, "id = ? ", new String[] { Integer.toString(item.getId()) } );
        return true;
    }

    public Integer deleteProduct (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("products",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Item> getAllProducts() {
        ArrayList<Item> array_list = new ArrayList<Item>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from products", null );
        if(res.getCount()>0&& res!=null &&res.moveToFirst()) {
            while (res.isAfterLast() == false) {
                Item item = new Item();
                item.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
                item.setName(res.getString(res.getColumnIndex(COLUMN_NAME)));
                item.setType(res.getString(res.getColumnIndex(COLUMN_TYPE)));
                item.setPrice(res.getString(res.getColumnIndex(COLUMN_PRICE)));
                item.setDesc(res.getString(res.getColumnIndex(COLUMN_DESC)));
                item.setSeller(res.getString(res.getColumnIndex(COLUMN_SELLER)));
                byte[] byteArray = res.getBlob(res.getColumnIndex(COLUMN_IMAGE));
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                item.setImage(bitmap);
                array_list.add(item);
                res.moveToNext();
            }
        }
        return array_list;
    }
    public ArrayList<Item> executeSQL(String type,String order)
    {
        ArrayList<Item> array_list = new ArrayList<Item>();

        //hp = new HashMap();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        if(type == "" && order!="")
        {
            res = db.rawQuery( "select * from products order by "+order,null);
        }
        else if(order == "" && type!="")
        {
            res = db.rawQuery( "select * from products where "+COLUMN_TYPE+" = '"+type+"'",null);
        }
        else
        {
            res = db.rawQuery( "select * from products where "+COLUMN_TYPE+" = '"+type+"' order by "+order,null);
        }
        if(res.getCount()>0&& res!=null &&res.moveToFirst()) {
            while (res.isAfterLast() == false) {
                Item item = new Item();
                item.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
                item.setName(res.getString(res.getColumnIndex(COLUMN_NAME)));
                item.setType(res.getString(res.getColumnIndex(COLUMN_TYPE)));
                item.setPrice(res.getString(res.getColumnIndex(COLUMN_PRICE)));
                item.setDesc(res.getString(res.getColumnIndex(COLUMN_DESC)));
                item.setSeller(res.getString(res.getColumnIndex(COLUMN_SELLER)));
                byte[] byteArray = res.getBlob(res.getColumnIndex(COLUMN_IMAGE));
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                item.setImage(bitmap);
                array_list.add(item);
                res.moveToNext();
            }
        }
        return array_list;
    }
    public ArrayList<Item> executeSQL(String seller)
    {
        ArrayList<Item> array_list = new ArrayList<Item>();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery( "select * from products where "+COLUMN_SELLER+" = '"+seller+"'",null);
        if(res.getCount()>0&& res!=null &&res.moveToFirst()) {
            while (res.isAfterLast() == false) {
                Item item = new Item();
                item.setId(res.getInt(res.getColumnIndex(COLUMN_ID)));
                item.setName(res.getString(res.getColumnIndex(COLUMN_NAME)));
                item.setType(res.getString(res.getColumnIndex(COLUMN_TYPE)));
                item.setPrice(res.getString(res.getColumnIndex(COLUMN_PRICE)));
                item.setDesc(res.getString(res.getColumnIndex(COLUMN_DESC)));
                item.setSeller(res.getString(res.getColumnIndex(COLUMN_SELLER)));
                byte[] byteArray = res.getBlob(res.getColumnIndex(COLUMN_IMAGE));
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                item.setImage(bitmap);
                array_list.add(item);
                res.moveToNext();
            }
        }
        return array_list;
    }
}
