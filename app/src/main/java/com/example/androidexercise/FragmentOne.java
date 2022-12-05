package com.example.androidexercise;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

public class FragmentOne extends Fragment {

    OnFragmentManager listener;
    EditText editText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentManager ){
            listener= (OnFragmentManager ) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement onViewSelected");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        editText = (EditText) v.findViewById(R.id.text_edit_fragment);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.onDataSelected(s.toString());
            }
        });
        return v;
    }
}