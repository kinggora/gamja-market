package com.example.gamjamarket.Model;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String id;
    private String name;
    private String icon;

    public CategoryModel(){}

    public CategoryModel(String id, String name, String icon){
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getIcon(){
        return icon;
    }



}
