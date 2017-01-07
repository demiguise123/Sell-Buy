package com.example.pari.olxelem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by pari on 19-12-2016.
 */

public class LoginPage extends Activity {
    Bitmap bitmap;
    ImageView targetImage;
    LoginDBHelper mydb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        mydb = new LoginDBHelper(this);
        targetImage = (ImageView)findViewById(R.id.imageView);
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
        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                targetImage.setImageBitmap(bitmap);
            } catch (java.io.FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public void onButtonPressed(View v)
    {
        switch (v.getId())
        {
            case R.id.browse:
                try{
                    String pack = getPackageName()+".BuyHomePage";
                    Class cls = Class.forName(pack);
                    Intent i = new Intent(LoginPage.this,cls);
                    startActivity(i);
                }
                catch(ClassNotFoundException e){e.printStackTrace();}
                break;
            case R.id.signup:
                EditText userT,passT,emailT,confirmpassT,nameT,descT;
                String user,pass,email,confirmpass,name,desc;
                nameT = (EditText)findViewById(R.id.editText7);
                userT = (EditText)findViewById(R.id.editText8);
                passT = (EditText)findViewById(R.id.editText9);
                confirmpassT = (EditText)findViewById(R.id.editText10);
                emailT = (EditText)findViewById(R.id.editText11);
                descT = (EditText)findViewById(R.id.editText12);
                name = nameT.getText().toString();
                user = userT.getText().toString();
                pass = passT.getText().toString();
                confirmpass = confirmpassT.getText().toString();
                email = emailT.getText().toString();
                desc = descT.getText().toString();
                if(name==""||user==""||pass==""||confirmpass==""||email=="")
                {
                    Toast myToast = Toast.makeText(getApplicationContext(),"Fill All Details!", Toast.LENGTH_LONG);
                    myToast.show();
                }
                else
                {
                    if(!pass.equals(confirmpass))
                    {
                        Toast myToast = Toast.makeText(getApplicationContext(),"Passwords don't match!", Toast.LENGTH_LONG);
                        myToast.show();
                    }
                    else
                    {
                        boolean allow=mydb.isAllowedUsername(user);
                        if(allow)
                        {
                            Account account = new Account();
                            account.setFullName(name);
                            account.setPassword(pass);
                            account.setDesc(desc);
                            account.setEmail(email);
                            account.setUsername(user);
                            account.setImage(bitmap);
                            mydb.insertAccount(account);

                            try{
                                String pack = getPackageName()+".FirstPage";
                                Class cls = Class.forName(pack);
                                Intent i = new Intent(LoginPage.this,cls);
                                i.putExtra("username",user);
                                startActivity(i);
                            }
                            catch(ClassNotFoundException e){e.printStackTrace();}
                        }
                        else
                        {
                            Toast myToast = Toast.makeText(getApplicationContext(),"Username Taken! Choose Another!", Toast.LENGTH_LONG);
                            myToast.show();
                        }
                    }
                }
                break;
            case R.id.login:
                EditText username = (EditText)findViewById(R.id.editText4);
                EditText password = (EditText)findViewById(R.id.editText6);
                String un = username.getText().toString();
                boolean correct = mydb.isCorrectLogin(un,password.getText().toString());
                if(correct){
                    try{
                        String pack = getPackageName()+".FirstPage";
                        Class cls = Class.forName(pack);
                        Intent i = new Intent(LoginPage.this,cls);
                        i.putExtra("username",un);
                        startActivity(i);
                    }
                    catch(ClassNotFoundException e){e.printStackTrace();}
                }
                else
                {
                    Toast myToast = Toast.makeText(getApplicationContext(),"Incorrect Username or Password. Try Again!", Toast.LENGTH_LONG);
                    myToast.show();
                }
                break;
        }
    }
}
