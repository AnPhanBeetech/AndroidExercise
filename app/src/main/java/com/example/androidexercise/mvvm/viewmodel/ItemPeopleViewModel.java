package com.example.androidexercise.mvvm.viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.example.androidexercise.mvvm.model.CatItem;
import com.example.androidexercise.mvvm.model.CountryItem;

public class ItemPeopleViewModel extends BaseObservable {

    private CountryItem.Country mCountry;
    private Context mContext;

    public ItemPeopleViewModel(Context context, CountryItem.Country country) {
        mCountry = country;
        mContext = context;
    }

    public String getFullName() {
        switch (mCountry.country_id)
        {
            case "AU":mCountry.fullName = "Australia";
                    break;
            case "NZ":mCountry.fullName = "NewZealand";
                break;
            case "JP":mCountry.fullName = "Japan";
                break;
            case "CA":mCountry.fullName = "Australia";
                break;
            case "CN":mCountry.fullName = "Chinese";
                break;
            case "VN":mCountry.fullName = "VietNam";
                break;
            case "US":mCountry.fullName = "America";
                break;
            default:mCountry.fullName = "America";
                break;
        }
        return mCountry.fullName;
    }

    public String getCell() {
        return "Prob:" + mCountry.probability;
    }

    public String getEmail() {
        return "ID Location:" + mCountry.country_id;
    }

    public String getPictureProfile() {
        return mCountry.fullName;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        //Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public void onItemClick(View view) {

    }

    public void setCountry(CountryItem.Country country) {
        mCountry = country;
    }

}
