package com.example.androidexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class RecyclerActivity extends AppCompatActivity {
    protected String[] mDataset;
    RecyclerView myRecyclerView;
    RecyclerAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initDataset();

        myRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(mDataset, mDataset);
        myRecyclerView.setAdapter(mAdapter);


    }

    private void initDataset() {
        mDataset = new String[5];
        for (int i = 0; i < 5; i++) {
            mDataset[i] = "This is element #" + i;
        }
    }

}