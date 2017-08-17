package com.example.nachox.faunasegura;

import org.json.JSONObject;

public class Categories {

    private int id;
    private String name;

    public Categories(){}

    public Categories( String name){
        this.id = id;
        this.name = name;
    }



    public void setName(String name){
        this.name = name;
    }


    public String getName(){
        return this.name;
    }

}
