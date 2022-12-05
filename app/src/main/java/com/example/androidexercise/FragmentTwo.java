package com.example.androidexercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentTwo extends Fragment {

    EditText receiveText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        receiveText = v.findViewById(R.id.receive_text);
        return v;
    }
    public void changeTextFrag2(String s)
    {
        receiveText.setText(s);
    }
}