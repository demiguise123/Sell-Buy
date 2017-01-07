package com.example.pari.olxelem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by pari on 19-12-2016.
 */

public class LoginDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "login.db";
    public static final String TABLE_NAME = "login";
    public static final String COLUMN_FULL_NAME = "fullname";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_IMAGE = "image";

    public LoginDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table login " +
                        "(fullname text , username text primary key, password text , description text , email text ,image BLOB)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS login");
        onCreate(db);
    }
    public boolean insertAccount (Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", account.getFullName());
        contentValues.put("username", account.getUsername());
        contentValues.put("password", account.getPassword());
        contentValues.put("description", account.getDesc());
        contentValues.put("email", account.getEmail());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(account.getImage()!=null) {
            account.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            contentValues.put("image", byteArray);
        }
        db.insert("login", null, contentValues);
        db.close();
        return true;
    }
    public boolean updateAccount (Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", account.getFullName());
        contentValues.put("username", account.getUsername());
        contentValues.put("password", account.getPassword());
        contentValues.put("description", account.getDesc());
        contentValues.put("email", account.getEmail());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(account.getImage()!=null) {
            account.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            contentValues.put("image", byteArray);
        }
        else
        {
            byte[] byteArray=null;
            contentValues.put("image",byteArray);
        }
        db.update("login", contentValues, "username = ? ", new String[] {account.getUsername() } );
        db.close();
        return true;
    }
    public boolean isCorrectLogin(String username,String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from login where username = '"+username+"'", null );
        res.moveToFirst();
        if(res == null)
            return false;
        String pass = res.getString(res.getColumnIndex(COLUMN_PASSWORD));
        if(pass.equals(password))
            return true;
        return false;
    }
    public boolean isAllowedUsername(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from login where username = '"+username+"'", null );
        if(res.getCount()>0&& res!=null &&res.moveToFirst())
            return false;
        return true;
    }
    public Account getData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from login where username='" + username + "'", null);
        res.moveToFirst();
        Account account = new Account();
        account.setFullName(res.getString(res.getColumnIndex(COLUMN_FULL_NAME)));
        account.setUsername(res.getString(res.getColumnIndex(COLUMN_USERNAME)));
        account.setPassword(res.getString(res.getColumnIndex(COLUMN_PASSWORD)));
        account.setEmail(res.getString(res.getColumnIndex(COLUMN_EMAIL)));
        account.setDesc(res.getString(res.getColumnIndex(COLUMN_DESC)));
        byte[] byteArray = res.getBlob(res.getColumnIndex(COLUMN_IMAGE));
        if(byteArray!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            account.setImage(bitmap);
        }
        return account;
    }
    public String getEmail(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from login where username='" + username + "'", null);
        res.moveToFirst();
        String email = (res.getString(res.getColumnIndex(COLUMN_EMAIL)));
        return email;
    }
}
