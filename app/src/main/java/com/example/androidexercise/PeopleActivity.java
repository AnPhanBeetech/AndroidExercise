package com.example.androidexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.androidexercise.databinding.ActivityPeopleBinding;
import com.example.androidexercise.mvvm.model.CatItem;
import com.example.androidexercise.mvvm.model.CountryItem;
import com.example.androidexercise.mvvm.view.PeopleAdapter;
import com.example.androidexercise.mvvm.viewmodel.PeopleViewModel;
import com.example.androidexercise.mvvm.viewmodel.PeopleViewModelContract;

import java.util.List;

public class PeopleActivity extends AppCompatActivity implements PeopleViewModelContract.MainView {

    private ActivityPeopleBinding mActivityPeopleBinding;
    private PeopleViewModel mPeopleViewModel;
    private PeopleViewModelContract.MainView mMainView = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataBinding();
        //setSupportActionBar(mActivityPeopleBinding.toolbar);
        setupListPeopleView(mActivityPeopleBinding.listPeople);
        mPeopleViewModel.FirstLoad();
    }

    private void initDataBinding() {
        mActivityPeopleBinding = DataBindingUtil.setContentView(this, R.layout.activity_people);
        mPeopleViewModel = new PeopleViewModel(mMainView, getContext());
        mActivityPeopleBinding.setMainViewModel(mPeopleViewModel);
    }

    private void setupListPeopleView(RecyclerView listPeople) {
        PeopleAdapter adapter = new PeopleAdapter();
        listPeople.setAdapter(adapter);
        listPeople.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void loadData(List<CountryItem.Country> countries) {
        PeopleAdapter peopleAdapter = (PeopleAdapter) mActivityPeopleBinding.listPeople.getAdapter();
        peopleAdapter.setPeopleList(countries);
    }

    @Override
    public Context getContext() {
        return PeopleActivity.this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPeopleViewModel.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}