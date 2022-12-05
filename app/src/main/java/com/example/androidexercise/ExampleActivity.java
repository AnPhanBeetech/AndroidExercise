package com.example.androidexercise;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExampleActivity extends AppCompatActivity {

    EditText emailTxt;
    private static final int REQUEST_CODE_EXAMPLE = 10;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        emailTxt.setText(result.getData().getStringExtra(InfoActivity.USER_DATA));

                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        Button loginButton = (Button)findViewById(R.id.btnLogin);
        //loginButton.setOnClickListener(loginListener);

        Button fbButton = (Button)findViewById(R.id.fb_login_button);

        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getBaseContext(), InfoActivity.class);
                myIntent.putExtra("username", emailTxt.getText().toString());
                mStartForResult.launch(myIntent);
            }
        });

        emailTxt = (EditText) findViewById(R.id.email_text);

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
                        startActivityForResult(myIntent, REQUEST_CODE_EXAMPLE);

                    }
                }, 1000);
            }

        }
    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_EXAMPLE) {

            if(resultCode == Activity.RESULT_OK) {
                final String result = data.getStringExtra(InfoActivity.USER_DATA);
                emailTxt.setText(result);

            } else {
            }
        }
    }
}