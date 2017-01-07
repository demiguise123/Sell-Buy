package com.example.pari.olxelem;

/**
 * Created by pari on 22-12-2016.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.ListActivity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.database.sqlite.SQLiteDatabase;

import static java.security.AccessController.getContext;

public class Selling extends Activity{

    DBHelper mydb;
    ListView listView;
    String username;
    public static SellingListAdapter sellingListAdapter;
    static ArrayList<Item> sellingItems = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling);
        Intent i = getIntent();
        if(i.hasExtra("username"))
            username = i.getStringExtra("username");
        else
            username = "";
        mydb = new DBHelper(this);
        sellingItems = mydb.executeSQL(username);
        sellingListAdapter = new SellingListAdapter(this,sellingItems);

        listView = (ListView)findViewById(R.id.listViewSellingItems);
        try {
            listView.setAdapter(sellingListAdapter);
        }catch(NullPointerException e){e.printStackTrace();}

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
                Intent myIntent = new Intent(Selling.this,SellingItem.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",position);
                myIntent.putExtras(bundle);
                myIntent.putExtra("username",username);
                startActivity(myIntent);

            }
        });
    }
    public void onButtonPress(View v)
    {
        Intent myIntent = new Intent(Selling.this,MyAccount.class);
        myIntent.putExtra("username",username);
        startActivity(myIntent);
    }
}

