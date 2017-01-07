package com.example.pari.olxelem;

/**
 * Created by pari on 13-12-2016.
 */
import android.graphics.Bitmap;
public class Item {
    private int id;
    private String name="";
    private String type="";
    private String price="";
    private String seller="";
    private String desc="";
    private Bitmap image=null;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSeller() {
        return seller;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
