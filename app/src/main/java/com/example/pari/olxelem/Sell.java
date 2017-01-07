package com.example.pari.olxelem;

/**
 * Created by pari on 05-12-2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.widget.TextView;

public class Sell extends  Activity implements OnItemSelectedListener{
    EditText et_name,et_price,et_desc;
    Spinner sp_type;
    String type;
    ImageView targetImage;
    Bitmap bitmap;
    DBHelper mydb;
    LoginDBHelper logindb;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        Intent i = getIntent();
        if(i.hasExtra("username"))
            username = i.getStringExtra("username");
        else
            username = "";
        mydb = new DBHelper(this);
        logindb = new LoginDBHelper(this);
        et_name = (EditText)findViewById(R.id.editText);
        et_price = (EditText)findViewById(R.id.editText2);
        et_desc = (EditText)findViewById(R.id.editText3);
        sp_type = (Spinner)findViewById(R.id.spinner);
        targetImage = (ImageView)findViewById(R.id.targetimage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sp_type.setAdapter(adapter);
        sp_type.setOnItemSelectedListener(this);
        targetImage.setOnClickListener(new ImageView.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }});
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
                targetImage.setImageBitmap(bitmap);
            } catch (java.io.FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public void onButtonTap(View v)
    {
        String name = et_name.getText().toString();
        String price = et_price.getText().toString();
        String desc = et_desc.getText().toString();
        String seller=username;
        if(name == "" || name.trim().length() == 0 || name == null || price == "" || price.trim().length() == 0 || type == null|| type == "" || type.trim().length() == 0 || type == null || bitmap==null)
        {
            Toast myToast = Toast.makeText(getApplicationContext(),"Kindly Enter All Necessary Information", Toast.LENGTH_LONG);
            myToast.show();
        }
        else
        {
            try{
                Item item = new Item();
                item.setDesc(desc);
                item.setName(name);
                item.setPrice(price);
                item.setType(type);
                item.setSeller(seller);
                item.setImage(bitmap);
                mydb.insertProduct(item);
                String pack = getPackageName()+".FirstPage";
                Class cls = Class.forName(pack);
                Intent i = new Intent(Sell.this,cls);
                i.putExtra("username",username);
                startActivity(i);
            }
            catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if(pos!=0)
            type = parent.getItemAtPosition(pos).toString();
        else
            type="";
    }

    public void onNothingSelected(AdapterView<?> parent) {
        type = "";
    }

}
