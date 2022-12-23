package com.example.androidexercise.mvvm.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.List;

public class CatItem {
    @SerializedName("country")
    public List<Country>country;
    @SerializedName("name")
    public String name;

    public class Country {
        public String country_id;
        public float probability;

        public String fullName;

    }

    public String getRespond()
    {
        return "Respond";//country[0].country_id + " vs " + country[0].probability +"  && " + name;
    }
    public List<Country> getListCountry()
    {
        return country;
    }

}
