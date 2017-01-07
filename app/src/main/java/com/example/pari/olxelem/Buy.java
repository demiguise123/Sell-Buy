package com.example.pari.olxelem;

/**
 * Created by pari on 05-12-2016.
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
import java.util.ArrayList;

public class Buy extends Activity {
    int id;
    String desc="",seller;
    DBHelper mydb;
    LoginDBHelper logindb;
    String username;
    static ArrayList<Item> items = new ArrayList<Item>();
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Intent i = getIntent();
        if(i.hasExtra("username"))
            username = i.getStringExtra("username");
        else
            username = "";
        mydb = new DBHelper(this);
        logindb = new LoginDBHelper(this);
        Bundle extras = i.getExtras();
        int pos= extras.getInt("id");
        mydb = new DBHelper(this);
        items = mydb.getAllProducts();
        Item item = items.get(pos);
        id = item.getId();
        String name = item.getName();
        String type = item.getType();
        String price = item.getPrice();
        seller = item.getSeller();
        desc = item.getDesc();
        Bitmap bitmap = item.getImage();
        String msg = "Product ID: "+id;
        TextView TextView_id = (TextView)findViewById(R.id.textView);
        TextView_id.setText(msg);
        TextView TextView_name = (TextView)findViewById(R.id.textView_name);
        TextView TextView_type = (TextView)findViewById(R.id.textView_type);
        TextView TextView_price = (TextView)findViewById(R.id.textView_price);
        TextView TextView_seller = (TextView)findViewById(R.id.textView_seller);
        ImageView ImageView_image = (ImageView)findViewById(R.id.image);
        TextView_name.setText(name);
        TextView_price.setText(price);
        TextView_type.setText(type);
        TextView_seller.setText(seller);
        BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
        ob = resize(ob);
        ImageView_image.setImageDrawable(ob);
    }
    public void onButtonClicked(View v)
    {
        if(username!="") {

            String sellerEmail = logindb.getEmail(seller);
            String buyerEmail = logindb.getEmail(username);
            SendMail sm = new SendMail(this, buyerEmail, "Details of the Seller", "The E-Mail Id of the seller of the product you are interested in is : " + sellerEmail + " . Please Contact the seller to buy it!");
            sm.execute();
        }
        else {
            Toast myToast = Toast.makeText(getApplicationContext(), "You are not logged in!", Toast.LENGTH_LONG);
            myToast.show();
        }
    }
    private BitmapDrawable resize(Drawable image)
    {
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap,
                (int) (bitmap.getWidth() * 0.5), (int) (bitmap.getHeight() * 0.5), false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
    public void onButtonPushed(View v)
    {
        Button bn;
        bn = (Button)findViewById(R.id.button6);
        String b = (String)bn.getText();
        FragmentManager fragmentManager = getFragmentManager() ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(b.equals("Show Details"))
        {
            DetailsFragment dfr = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("item", desc);
            dfr.setArguments(bundle);
            fragmentTransaction.add(R.id.fragment_container,dfr);
            fragmentTransaction.commit();
            bn.setText("Hide Details");
        }
        else
        {
            DetailsFragment dfr = (DetailsFragment)fragmentManager.findFragmentById(R.id.fragment_container);
            fragmentTransaction.hide(dfr);
            fragmentTransaction.commit();
            bn.setText("Show Details");
        }
    }
}