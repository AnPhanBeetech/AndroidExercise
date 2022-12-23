package com.example.androidexercise.mvvm.viewmodel;

import android.content.Context;

import com.example.androidexercise.mvvm.model.CatItem;
import com.example.androidexercise.mvvm.model.CountryItem;

import java.util.List;

public class PeopleViewModelContract {
    public interface MainView {
        void loadData(List<CountryItem.Country> peoples);

        Context getContext();
    }

    interface ViewModel {
        void destroy();
    }
}
