package com.example.androidexercise;

import android.graphics.Bitmap;
import android.net.Uri;

public class ItemChat {
    private String text;
    private Uri imgUri;
    private Bitmap bitmapData;
    private int viewType;

    public ItemChat(int viewType, String text)
    {
        this.text = text;
        this.viewType = viewType;
    }

    public ItemChat(int viewType, Uri uri) {
        imgUri = uri;
        this.viewType = viewType;
    }
    public ItemChat(int viewType, Bitmap bitmap) {
        bitmapData = bitmap;
        this.viewType = viewType;
    }

    public String getText() { return text; }
    public Uri getUri() { return imgUri; }
    public Bitmap getBitmap() { return bitmapData; }
    public void setText(String text) { this.text = text; }
    public int getViewType() { return viewType; }

    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }
}
