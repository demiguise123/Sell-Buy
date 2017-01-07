package com.example.pari.olxelem;

/**
 * Created by pari on 19-12-2016.
 */
import android.graphics.Bitmap;
public class Account {
    private String FullName="";
    private String Username="";
    private String Password="";
    private String Email="";
    private String Desc="";
    private Bitmap image=null;

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPassword() {
        return Password;
    }

    public String getUsername() {
        return Username;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getEmail() {
        return Email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDesc() {
        return Desc;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
