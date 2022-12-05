package com.example.androidexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

public class FragmentTabActivity extends AppCompatActivity implements OnFragmentManager, View.OnClickListener{

    FragmentTwo fragment2;
    FragmentOne fragment1;
    LinearLayout linear;
    Button addFragment;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab);
        addFragment = findViewById(R.id.btn_add);
        addFragment.setOnClickListener(this);
        linear = findViewById(R.id.linear_frag);

    }

    @Override
    public void onClick (View v) {
        if (fragment1 == null) {
            fragment1 = new FragmentOne();
            ft = getSupportFragmentManager().beginTransaction();
            ft.setReorderingAllowed(true);
            ft.add(linear.getId(), fragment1, null);
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (fragment2 == null)
        {
            fragment2 = new FragmentTwo();
            ft= getSupportFragmentManager().beginTransaction();
            ft.add(linear.getId(), fragment2, null);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void onDataSelected(String data) {
        Log.d("MyActivity","Value: " + data);
        fragment2.changeTextFrag2(data);
    }
}