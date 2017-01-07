package com.example.pari.olxelem;

/**
 * Created by pari on 22-12-2016.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.content.Context;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


public class SellingListAdapter extends ArrayAdapter<Item> {
    private Context context;
    private ArrayList<Item> sellingItems;
    DBHelper mydb;
    public SellingListAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.selling_list_item, items);
        this.context = context;
        sellingItems=items;
        mydb = new DBHelper(context);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        if(v == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=vi.inflate(R.layout.selling_list_item,null);
        }
        Button button = (Button)v.findViewById(R.id.button11);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Item i = sellingItems.get(position);
                if(i!=null) {
                    mydb.deleteProduct(i.getId());
                    Toast myToast = Toast.makeText(context,"Item Removed!", Toast.LENGTH_LONG);
                    myToast.show();
                }
            }
        });
        final Item item = sellingItems.get(position);
        if(item!=null)
        {
            TextView label = (TextView)v.findViewById(R.id.textView15);
            if(label!=null)
                label.setText(item.getName());
        }
        return v;
    }
}

