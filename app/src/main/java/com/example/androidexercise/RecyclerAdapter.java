package com.example.androidexercise;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    String[] typeOneList;
    String[] typeTwoList;
    public static final int LayoutOne = 0;
    public static final int LayoutTwo = 1;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = view.findViewById(R.id.tvContact);
        }

        public TextView getTextView() {
            return textView;
        }
    }
    public RecyclerAdapter(String[] dataSet, String[] dataSet2) {
        typeOneList = dataSet;
        typeTwoList = dataSet2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Log.d("ADF", "viewtype " + viewType);
        switch (viewType) {
            case LayoutOne:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_one, parent, false);
                return new TypeOneViewHodel(view);
            case LayoutTwo:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_two, parent, false);
                return new TypeTwoViewHodel(view);
            default:
                return null;
        }
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //if (viewHolder instanceof TypeOneViewHodel )
        if (position < typeOneList.length)
            viewHolder.getTextView().setText(typeOneList[position]);
        else
        viewHolder.getTextView().setText(typeTwoList[position - typeOneList.length]);
//        else
//            viewHolder.getTextView().setText(typeOneList[position]);
    }
    @Override
    public int getItemCount() {
        return typeOneList.length + typeTwoList.length;
    }
    @Override
    public int getItemViewType(int position)
    {
        if (position < typeOneList.length) {
            return LayoutOne;
        }
        return LayoutTwo;
    }





    public class TypeOneViewHodel extends RecyclerAdapter.ViewHolder {
        public TypeOneViewHodel(View view) {
            super(view);
            textView = view.findViewById(R.id.tvContact);
        }
    }
    public class TypeTwoViewHodel extends RecyclerAdapter.ViewHolder {
        public TypeTwoViewHodel(View view) {
            super(view);
            textView = view.findViewById(R.id.tvAccount);
        }
    }
}
