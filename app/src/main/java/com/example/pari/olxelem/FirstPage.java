package com.example.pari.olxelem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

public class FirstPage extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        Intent i = getIntent();
        if(i.hasExtra("username")) {
            username = i.getStringExtra("username");
            Button b = (Button) findViewById(R.id.button);
            b.setVisibility(View.VISIBLE);
            b = (Button) findViewById(R.id.button7);
            b.setVisibility(View.VISIBLE);
        }
        else
        {
            username = "";
            Button b = (Button) findViewById(R.id.button);
            b.setVisibility(View.GONE);
            b = (Button) findViewById(R.id.button7);
            b.setVisibility(View.GONE);
        }
    }

    public void onButtonTap(View v)
    {
        switch(v.getId()) {
            case R.id.button:
                try {
                    String pack = getPackageName() + ".MyAccount";
                    Class cls = Class.forName(pack);
                    Intent i = new Intent(FirstPage.this, cls);
                    i.putExtra("username", username);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button2:
                if (username != "")
                {
                    try {
                        String pack = getPackageName() + ".Sell";
                        Class cls = Class.forName(pack);
                        Intent i = new Intent(FirstPage.this, cls);
                        i.putExtra("username", username);
                        startActivity(i);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast myToast = Toast.makeText(getApplicationContext(),"You are not logged in!", Toast.LENGTH_LONG);
                    myToast.show();
                }
                break;
            case R.id.button5:
                try{
                    String pack = getPackageName()+".BuyHomePage";
                    Class cls = Class.forName(pack);
                    Intent i = new Intent(FirstPage.this,cls);
                    i.putExtra("username",username);
                    startActivity(i);
                }
                catch(ClassNotFoundException e){e.printStackTrace();}
                break;
            case R.id.button7:
                username = "";
                try{
                    String pack = getPackageName()+".LoginPage";
                    Class cls = Class.forName(pack);
                    Intent i = new Intent(FirstPage.this,cls);
                    startActivity(i);
                }
                catch(ClassNotFoundException e){e.printStackTrace();}
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }
    }
}
