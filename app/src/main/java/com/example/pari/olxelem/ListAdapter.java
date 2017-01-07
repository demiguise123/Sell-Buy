package com.example.pari.olxelem;
/**
 * Created by pari on 13-12-2016.
 */
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.content.Context;
import android.widget.TextView;
import android.widget.ImageView;



public class ListAdapter extends ArrayAdapter<Item> {
    private Context context;
    private ArrayList<Item> items;
    public ListAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.list_item, items);
        this.context = context;
        this.items=items;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        if(v == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=vi.inflate(R.layout.list_item,null);
        }
        final Item item = items.get(position);
        if(item!=null)
        {
            TextView label = (TextView)v.findViewById(R.id.label);
            if(label!=null)
                label.setText(item.getName());
            ImageView iv = (ImageView)v.findViewById(R.id.imageView4);
            Bitmap bitmap = item.getImage();
            BitmapDrawable ob = new BitmapDrawable(context.getResources(), bitmap);
            ob = resize(ob);
            iv.setImageDrawable(ob);

        }
        return v;
    }
    private BitmapDrawable resize(Drawable image)
    {
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap,
                (int) (bitmap.getWidth() * 0.5), (int) (bitmap.getHeight() * 0.5), false);
        return new BitmapDrawable(context.getResources(), bitmapResized);
    }
}
