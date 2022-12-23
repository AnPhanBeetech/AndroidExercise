package com.example.androidexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidexercise.mvvm.model.CatItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroActivity extends AppCompatActivity implements Callback<CatItem>, View.OnClickListener {
    //public static final String BASE_URL = "https://catfact.ninja/";
    //static final String BASE_URL = "https://api.genderize.io";
//    String BASE_URL = "https://api.cryptonator.com/api/full/";
    String BASE_URL = "https://api.nationalize.io/?name=nathaniel";


    public TextView txtZip;
    private Button btnZip;
    private Button btnMerge;
    private Button btnZipList;
    private Button btnMergeList;
    private TextView txtMerge;
    private TextView txtZipList;
    private TextView txtMergeList;


    Retrofit retrofit;
    Observable<List<CatItem.Country>> btcObservable;
    Observable<List<CatItem.Country>> ethObservable;

    private Observable<Integer> createObservable(int data) {
        return Observable.just(data);
    }

    private List<Observable<?>> createListObservable() {
        List<Observable<?>> result = new ArrayList<>();
        result.add(createObservable(1));
        result.add(createObservable(2));
        result.add(createObservable(3));
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro);
        btnZip = (Button) findViewById(R.id.btn_zip);
        btnMerge = (Button) findViewById(R.id.btn_merge);
//        btnZipList = (Button) findViewById(R.id.btn_zip_list);
//        btnMergeList = (Button) findViewById(R.id.btn_merge_list);

        btnMerge.setOnClickListener(this);
        btnZip.setOnClickListener(this);


        txtMerge = (TextView) findViewById(R.id.txt_merge);
        txtZip = (TextView) findViewById(R.id.txt_zip);
        txtMergeList = (TextView) findViewById(R.id.txt_merge_list);
        txtZipList = (TextView) findViewById(R.id.txt_zip_list);

        btnZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zip();
            }
        });
        btnMerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                merge();
            }
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        callEndpoints();
//
//        MyApiEndpointInterface gerritAPI = retrofit.create(MyApiEndpointInterface.class);
//        Call<CatItem> call = gerritAPI.getCoinDataItem("eth");
//        call.enqueue(this);


//        Observable<String> footballPlayersObservable = Observable.just("Messi", "Ronaldo", "Modric", "Salah", "Mbappe");
//        footballPlayersObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(footballPlayersObserver);


    }


    @Override
    public void onResponse(Call<CatItem> call, Response<CatItem> response) {
        if(response.isSuccessful()) {
            CatItem cat = response.body();
            System.out.println("Success  " + cat.getRespond());
            Log.d("TAG", cat.getRespond());
        } else {
            Log.d("TAG", response.errorBody().toString());

        }
    }

    @Override
    public void onFailure(Call<CatItem> call, Throwable t) {
        t.printStackTrace();
        Log.d("TAG","OnAppFailure  " + t.getMessage());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_zip:
                zip();
                break;
            case R.id.btn_merge:
                merge();
                break;
//            case R.id.btn_zip_list:
//                zipList();
//                break;
//            case R.id.btn_merge_list:
//                mergeList();
//                break;
        }
    }
    private void zip() {
        Observable.zip(btcObservable, ethObservable,new BiFunction<List<CatItem.Country>, List<CatItem.Country>, String>() {
                    @Override
                    public String apply(List<CatItem.Country> country1, List<CatItem.Country> country2) {
                        return country1.get(0).country_id.toString() + " and " + country2.get(0).country_id.toString();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleZipResults, this::handleError);
    }
    private void zipList(){

    }
    private void merge(){
        Observable.merge(btcObservable, ethObservable)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleMergeResults, this::handleError);
    }
    private void mergeList(){}

    class DataZip {
        int numberOne;
        int numberTwo;
        int numberThree;

        DataZip(int numberOne, int numberTwo, int numberThree) {
            this.numberOne = numberOne;
            this.numberTwo = numberTwo;
            this.numberThree = numberThree;
        }
    }


    private void callEndpoints() {

        MyApiEndpointInterface cryptocurrencyService = retrofit.create(MyApiEndpointInterface.class);

        btcObservable = cryptocurrencyService.getCoinData("btc")
                .map(result -> Observable.fromIterable(result.country))
                .flatMap(x -> x).filter(y -> {
                    //y.coinName = "btc";
                    Log.d("TAG",y.country_id);
                    return true;
                }).toList().toObservable();

        ethObservable = cryptocurrencyService.getCoinData("nathaniel")
                .map(result -> Observable.fromIterable(result.country))
                .flatMap(x -> x).filter(y -> {
                    //y.coinName = "eth";
                    Log.d("TAG",y.country_id);
                    return true;
                }).toList().toObservable();



    }

    private void handleZipResults(String sumResult) {
        if (sumResult != null && sumResult.length() != 0) {

            Toast.makeText(this, "Zip: " + sumResult, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "NO RESULTS FOUND", Toast.LENGTH_LONG).show();
        }
    }

    private void handleMergeResults(List<CatItem.Country> result) {
        if (result != null && result.size() != 0) {

            Toast.makeText(this, "Merge Get: " + result.get(0).country_id.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "NO RESULTS FOUND",
                    Toast.LENGTH_LONG).show();
        }
    }
//
//    private void handleResults(List<CatItem.Country> marketList) {
//        if (marketList != null && marketList.size() != 0) {
//            String s = "";
//            for (CatItem.Country cat:marketList) {
//                s += cat.country_id + cat.probability +"  ";
//            }
//            Toast.makeText(this, s,
//                    Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(this, "NO RESULTS FOUND",
//                    Toast.LENGTH_LONG).show();
//        }
//    }

    private void handleError(Throwable t) {

        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }


}