package com.example.androidexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InfoActivity extends AppCompatActivity {

    EditText usernameTxt;
    Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        usernameTxt = (EditText) findViewById(R.id.edit_username);
        usernameTxt.setText(getIntent().getStringExtra("username"));

        backBtn = (Button) findViewById(R.id.btn_back);
        backBtn.setOnClickListener(backListener);

    }
    private View.OnClickListener backListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
            myIntent.putExtra("username", usernameTxt.getText().toString());
            InfoActivity.this.startActivity(myIntent);
        }
    };
}