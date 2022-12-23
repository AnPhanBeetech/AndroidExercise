package com.example.androidexercise.mvvm.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidexercise.databinding.ItemPeopleBinding;
import com.example.androidexercise.mvvm.model.CatItem;
import com.example.androidexercise.R;
import com.example.androidexercise.mvvm.model.CountryItem;
import com.example.androidexercise.mvvm.viewmodel.ItemPeopleViewModel;

import java.util.Collections;
import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleAdapterViewHolder> {

    private List<CountryItem.Country> mPeopleList;

    public PeopleAdapter() {
        mPeopleList = Collections.emptyList();
    }


    @Override
    public PeopleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPeopleBinding itemPeopleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_people, parent, false);

        return new PeopleAdapterViewHolder(itemPeopleBinding);
    }

    @Override
    public void onBindViewHolder(PeopleAdapterViewHolder holder, int position) {
        holder.bindCountry(mPeopleList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }

    public void setPeopleList(List<CountryItem.Country> countryList) {
        mPeopleList = countryList;
        notifyDataSetChanged();
    }

    public static class PeopleAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemPeopleBinding mItemPeopleBinding;

        public PeopleAdapterViewHolder(ItemPeopleBinding itemPeopleBinding) {
            super(itemPeopleBinding.itemPeople);
            this.mItemPeopleBinding = itemPeopleBinding;
        }

        public void bindCountry(CountryItem.Country country) {
            if (mItemPeopleBinding.getMPeopleViewModel() == null) {
                mItemPeopleBinding.setMPeopleViewModel(new ItemPeopleViewModel(itemView.getContext(), country));
            } else {
                mItemPeopleBinding.getMPeopleViewModel().setCountry(country);
            }
            mItemPeopleBinding.executePendingBindings();
        }
    }
}
