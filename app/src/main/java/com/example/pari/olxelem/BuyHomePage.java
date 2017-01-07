package com.example.pari.olxelem;

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

public class BuyHomePage extends Activity implements AdapterView.OnItemSelectedListener{

    DBHelper mydb;
    ListView listView;
    Spinner sp_type1,sp_type2;
    String type="",order="";
    String username;
    public static ListAdapter listAdapter;
    static ArrayList<Item> items = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_home_page);
        Intent i = getIntent();
        if(i.hasExtra("username"))
            username = i.getStringExtra("username");
        else
            username = "";
        mydb = new DBHelper(this);
        sp_type1 = (android.widget.Spinner)findViewById(R.id.spinner2);
        sp_type2 = (android.widget.Spinner)findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sp_type1.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.order_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sp_type2.setAdapter(adapter2);
        sp_type1.setOnItemSelectedListener(this);
        sp_type2.setOnItemSelectedListener(this);
        //items to be selected on basis of spinners


    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String t;
        t = parent.getItemAtPosition(pos).toString();
        if(parent.getId() == R.id.spinner2)
        {
            if(pos!=0)
                type = t;
            else
                type="";
        }
        else {
            if(pos!=0)
                order = t;
            else
                order="";
        }
        if(type == "" && order == "")
            items = mydb.getAllProducts();
        else
        {
            items = mydb.executeSQL(type,order);
        }
        listAdapter = new ListAdapter(this,items);

        listView = (ListView)findViewById(R.id.listViewItems);
        try {
            listView.setAdapter(listAdapter);
        }catch(NullPointerException e){e.printStackTrace();}
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {

                Intent myIntent = new Intent(BuyHomePage.this,Buy.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",position);
                myIntent.putExtras(bundle);
                if(username!="")
                    myIntent.putExtra("username",username);
                startActivity(myIntent);

            }
        });
    }
    public void onNothingSelected(AdapterView<?> parent) {
        type="";
        order="";
    }

}
