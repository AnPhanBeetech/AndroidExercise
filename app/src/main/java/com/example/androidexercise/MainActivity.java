package com.example.androidexercise;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button examButton = findViewById(R.id.actigo_button);
        examButton.setOnClickListener(goActivityListener);

        Button fragmentButton = findViewById(R.id.fragmentgo_button);
        fragmentButton.setOnClickListener(goFragmentListener);
    }





    final private View.OnClickListener goActivityListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(getBaseContext(), ExampleActivity.class);
            startActivityForResult(myIntent, 1);

        }
    };
    final private View.OnClickListener goFragmentListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(getBaseContext(), FragmentTabActivity.class);
            startActivityForResult(myIntent, 2);

        }
    };
}