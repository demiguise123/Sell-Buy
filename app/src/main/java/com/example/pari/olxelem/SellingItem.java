package com.example.pari.olxelem;

/**
 * Created by pari on 22-12-2016.
 */

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.database.Cursor;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SellingItem extends Activity {
    int id;
    String desc="",seller;
    DBHelper mydb;
    String username;
    static ArrayList<Item> items = new ArrayList<Item>();
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_item);
        Intent i = getIntent();
        if(i.hasExtra("username"))
            username = i.getStringExtra("username");
        else
            username = "";
        mydb = new DBHelper(this);
        Bundle extras = i.getExtras();
        int pos= extras.getInt("id");
        mydb = new DBHelper(this);
        items = mydb.executeSQL(username);
        Item item = items.get(pos);
        id = item.getId();
        String name = item.getName();
        String type = item.getType();
        String price = item.getPrice();
        seller = item.getSeller();
        desc = item.getDesc();
        Bitmap bitmap = item.getImage();
        String msg = "Product ID: "+id;
        TextView TextView_id = (TextView)findViewById(R.id.textView16);
        TextView_id.setText(msg);
        TextView TextView_name = (TextView)findViewById(R.id.text_name);
        TextView TextView_type = (TextView)findViewById(R.id.text_type);
        TextView TextView_price = (TextView)findViewById(R.id.text_price);
        TextView TextView_seller = (TextView)findViewById(R.id.text_seller);
        ImageView ImageView_image = (ImageView)findViewById(R.id.imageView3);
        TextView_name.setText(name);
        TextView_price.setText(price);
        TextView_type.setText(type);
        TextView_seller.setText(seller);
        BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
        ob = resize(ob);
        ImageView_image.setImageDrawable(ob);
    }

    private BitmapDrawable resize(Drawable image)
    {
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap,
                (int) (bitmap.getWidth() * 0.5), (int) (bitmap.getHeight() * 0.5), false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
}
