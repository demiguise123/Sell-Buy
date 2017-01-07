package com.example.pari.olxelem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by pari on 19-12-2016.
 */
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAccount extends Activity {
    String username;
    LoginDBHelper mydb;
    TextView tv14;
    EditText et5,et13,et14,et15;
    ImageView iv;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);
        Intent i = getIntent();
        username= i.getStringExtra("username");
        mydb = new LoginDBHelper(this);
        Account account = mydb.getData(username);
        tv14 = ((TextView)findViewById(R.id.textView14));
        tv14.setText(username);
        et5 = ((EditText)findViewById(R.id.editText5));
        et5.setText(account.getPassword());
        et13= ((EditText)findViewById(R.id.editText13));
        et13.setText(account.getEmail());
        et14 = ((EditText)findViewById(R.id.editText14));
        et14.setText(account.getFullName());
        et15 = ((EditText)findViewById(R.id.editText15));
        et15.setText(account.getDesc());
        iv = ((ImageView) findViewById(R.id.imageView2));
        if(account.getImage()!=null) {
            BitmapDrawable ob = new BitmapDrawable(getResources(), account.getImage());
            ob = resize(ob);
            iv.setImageDrawable(ob);
        }
        iv.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        super.onActivityResult(requestCode, resultCode, data);
        //  TextView textTargetUri;
        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            //   textTargetUri.setText(targetUri.toString());

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                iv.setImageBitmap(bitmap);
            } catch (java.io.FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private BitmapDrawable resize(Drawable image)
    {
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap,
                (int) (bitmap.getWidth() * 0.5), (int) (bitmap.getHeight() * 0.5), false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
    public void onButtonTapped(View v)
    {
        if(v.getId() == R.id.button8) {
            Account account = new Account();
            account.setUsername(username);
            account.setFullName(et14.getText().toString());
            account.setPassword(et5.getText().toString());
            account.setEmail(et13.getText().toString());
            account.setDesc(et15.getText().toString());
            account.setImage(bitmap);
            mydb.updateAccount(account);
            try {
                String pack = getPackageName() + ".FirstPage";
                Class cls = Class.forName(pack);
                Intent i = new Intent(MyAccount.this, cls);
                i.putExtra("username", username);
                startActivity(i);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                String pack = getPackageName() + ".Selling";
                Class cls = Class.forName(pack);
                Intent i = new Intent(MyAccount.this, cls);
                i.putExtra("username", username);
                startActivity(i);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
