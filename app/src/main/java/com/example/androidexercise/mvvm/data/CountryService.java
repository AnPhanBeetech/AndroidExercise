package com.example.androidexercise.mvvm.data;

import com.example.androidexercise.mvvm.model.CatItem;
import com.example.androidexercise.mvvm.model.CountryItem;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountryService {
    @GET("/")
    io.reactivex.Observable<CountryItem> fetchCountry(@Query("name") String position);

}
