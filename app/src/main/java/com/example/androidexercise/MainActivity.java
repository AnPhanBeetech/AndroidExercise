package com.example.androidexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText emailTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = (Button)findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(loginListener);
        emailTxt = (EditText) findViewById(R.id.email_text);

        emailTxt = (EditText) findViewById(R.id.email_text);
        emailTxt.setText(getIntent().getStringExtra("username"));


    }

    private View.OnClickListener loginListener = new View.OnClickListener() {
        public void onClick(View v) {
            String toastMes = emailTxt.getText().length() > 0 ? "Success ! Valid username" : "Please input username";
            Toast toast=Toast.makeText(getApplicationContext(),toastMes,Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();

            if (emailTxt.getText().length() > 0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(getBaseContext(), InfoActivity.class);
                        myIntent.putExtra("username", emailTxt.getText().toString());
                        MainActivity.this.startActivity(myIntent);
                    }
                }, 2000);
            }

        }
    };
}