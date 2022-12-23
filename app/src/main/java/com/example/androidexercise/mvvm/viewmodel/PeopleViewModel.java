package com.example.androidexercise.mvvm.viewmodel;
import android.content.Context;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.View;

import com.example.androidexercise.mvvm.model.CatItem;
import com.example.androidexercise.R;
import com.example.androidexercise.mvvm.data.CountryService;
import com.example.androidexercise.mvvm.model.CountryItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;

public class PeopleViewModel implements PeopleViewModelContract.ViewModel {

    public ObservableInt mPeopleProgress;
    public ObservableInt mPeopleList;
    public ObservableInt mPeopleLabel;
    public ObservableField<String> mMessageLabel;

    private PeopleViewModelContract.MainView mMainView;
    private Context mContext;
    private Subscription mSubscription;

    public PeopleViewModel(@NonNull PeopleViewModelContract.MainView mainView, @NonNull Context context) {
        this.mMainView = mainView;
        this.mContext = context;
        this.mPeopleProgress = new ObservableInt(View.GONE);
        this.mPeopleList = new ObservableInt(View.GONE);
        this.mPeopleLabel = new ObservableInt(View.VISIBLE);

        mMessageLabel = new ObservableField<>(mContext.getResources().getString(R.string.default_loading_people));
    }


    public void onClickFabLoad(View view) {
        initializeViews();
        fetchPeopleList();
    }
    public void FirstLoad() {
        initializeViews();
        fetchPeopleList();
    }

    public void initializeViews() {
        mPeopleProgress.set(View.VISIBLE);
        mPeopleList.set(View.GONE);
        mPeopleLabel.set(View.VISIBLE);
    }

    private void fetchPeopleList() {
        final String URL = "http://api.randomuser.me/?results=10&nat=en";
        unSubscribeFromObservable();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nationalize.io/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CountryService fact = retrofit.create(CountryService.class);

        Observable<CountryItem>  mSubscriptionOne = fact.fetchCountry("jame");

        Observable<CountryItem>  mSubscriptionTwo = fact.fetchCountry("zhang");

        Observable.merge(mSubscriptionOne, mSubscriptionTwo)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleMergeResults, this::handleError);


    }
    private void handleMergeResults(CountryItem respond) {
        if (respond != null && respond.name.length() > 0) {
            Log.d("MVVM", "success name: " + respond.name);
            mPeopleProgress.set(View.GONE);
            mPeopleList.set(View.VISIBLE);
            mPeopleLabel.set(View.GONE);
            if (mMainView != null) {
                mMainView.loadData(respond.getListCountry());
            }
        } else {
            Log.d("MVVM", "ErrorsumResult");
        }
    }
    private void handleError(Throwable t) {
        mMessageLabel.set(mContext.getString(R.string.error_loading_people));
        Log.d("MVVM", "ErrorsumResult");
    }

    @Override
    public void destroy() {
        reset();
    }

    private void reset() {
        unSubscribeFromObservable();
        mSubscription = null;
        mContext = null;
        mMainView = null;
    }

    private void unSubscribeFromObservable() {
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
