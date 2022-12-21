package com.example.androidexercise;

import com.example.androidexercise.ItemChat;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApiEndpointInterface {
    @GET("users/{username}")
    Call<ItemChat> getUser(@Path("username") String username);

    @GET("group/{id}/users")
    Call<List<ItemChat>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<ItemChat> createUser(@Body ItemChat user);

    @GET(".")
    Call<CatItem> loadChanges(@Query("name") String name);




    //String BASE_URL = "https://api.cryptonator.com/api/full/";
    @GET(".")
    Observable<CatItem> getCoinData(@Query("name") String name);
    @GET(".")
    Call<CatItem> getCoinDataItem(@Query("name") String name);

}
