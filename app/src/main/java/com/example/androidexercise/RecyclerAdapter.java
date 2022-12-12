package com.example.androidexercise;


import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<ItemChat> listChatItem;
    public static final int LayoutMainText = 0;
    public static final int LayoutOppText = 1;
    public static final int LayoutMainImg = 2;
    public static final int LayoutOppType = 3;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;
        protected ImageView imgView;

        public ViewHolder(View view) {
            super(view);
            //textView = view.findViewById(R.id.tvContact);
        }

        public TextView getTextView() {
            return textView;
        }
    }
    public RecyclerAdapter(List<ItemChat> listchat) {
        listChatItem = listchat;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Log.d("ADF", "viewtype " + viewType);
        switch (viewType) {
            case LayoutMainText:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_text_mainuser, parent, false);
                return new MainTextViewHodel(view);
            case LayoutOppText:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_text_opposite, parent, false);
                return new OppTextViewHodel(view);
            case LayoutMainImg:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_img_mainuser, parent, false);
                return new MainImgViewHodel(view);
            case LayoutOppType:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_typing_opposite, parent, false);
                return new OppTypeViewHodel(view);
            default:
                return null;
        }
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String text = "";
        switch (listChatItem.get(position).getViewType()) {
            case LayoutMainText:
                text = listChatItem.get(position).getText();
                ((MainTextViewHodel)viewHolder).setView(text);
                break;

            case LayoutOppText:
                text = listChatItem.get(position).getText();
                ((OppTextViewHodel)viewHolder).setView(text);
                break;
            case LayoutMainImg:
                if (listChatItem.get(position).getUri() != null) {
                    Uri uri = listChatItem.get(position).getUri();
                    ((MainImgViewHodel) viewHolder).setView(uri);
                }
                else
                {
                    Bitmap bitmap = listChatItem.get(position).getBitmap();
                    ((MainImgViewHodel)viewHolder).setView(bitmap);

                }
                break;
            case LayoutOppType:
                text = listChatItem.get(position).getText();
                ((OppTypeViewHodel)viewHolder).setView(text);
                break;
            default:
                return;
        }
//        if (position < typeOneList.length)
//            viewHolder.getTextView().setText(typeOneList[position]);
//        else
//        viewHolder.getTextView().setText(typeTwoList[position - typeOneList.length]);
    }
    @Override
    public int getItemCount() {
        return listChatItem.size();
    }
    @Override
    public int getItemViewType(int position)
    {
        switch (listChatItem.get(position).getViewType()) {
            case 0:
                return LayoutMainText;
            case 1:
                return LayoutOppText;
            case 2:
                return LayoutMainImg;
            case 3:
                return LayoutOppType;
            default:
                return -1;
        }
    }





    public class MainTextViewHodel extends RecyclerAdapter.ViewHolder {
        public MainTextViewHodel(View view) {
            super(view);
            textView = view.findViewById(R.id.mainuser_text);
        }
        private void setView(String text)
        {
            textView.setText(text);
        }
    }
    public class OppTextViewHodel extends RecyclerAdapter.ViewHolder {
        public OppTextViewHodel(View view) {
            super(view);
            textView = view.findViewById(R.id.opp_text);
        }
        private void setView(String text)
        {
            textView.setText(text);
        }
    }
    public class OppTypeViewHodel extends RecyclerAdapter.ViewHolder {
        public OppTypeViewHodel(View view) {
            super(view);
            textView = view.findViewById(R.id.opp_typing);
        }
        private void setView(String text)
        {
            textView.setText(text);
        }
    }
    public class MainImgViewHodel extends RecyclerAdapter.ViewHolder {
        public MainImgViewHodel(View view) {
            super(view);
            imgView = view.findViewById(R.id.main_img);
        }
        private void setView(Uri selectedImageUri)
        {
            imgView.setImageURI(selectedImageUri);
        }
        private void setView(Bitmap bitmap)
        {
            imgView.setImageBitmap(bitmap);
        }
    }
}
