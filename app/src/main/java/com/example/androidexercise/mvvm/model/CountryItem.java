package com.example.androidexercise.mvvm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryItem {
    @SerializedName("country")
    public List<CountryItem.Country> country;
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
    public List<CountryItem.Country> getListCountry()
    {
        return country;
    }

}
