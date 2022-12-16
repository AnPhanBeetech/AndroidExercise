package com.example.androidexercise;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ExampleActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    EditText emailTxt;
    Button firebaseBtn;
    private static final int REQUEST_CODE_EXAMPLE = 10;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;


    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        emailTxt.setText(result.getData().getStringExtra(InfoActivity.USER_DATA));

                    }
                }
            });

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        emailTxt = (EditText) findViewById(R.id.email_text);

//Login Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signinIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signinIntent, 1);
            }
          });


        Button loginButton = (Button)findViewById(R.id.firebase_login_btn);
        Button fbButton = (Button)findViewById(R.id.fb_login_button);
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                myIntent.putExtra("username", emailTxt.getText().toString());
//                mStartForResult.launch(myIntent);
            }
        });

        firebaseBtn = findViewById(R.id.firebase_login_btn);
        firebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG","Firebase Click");
                FirebaseLogin();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        //CreateUser();
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("TAG","Failed");
    }

    public void FirebaseLogin()
    {
        mAuth.signInWithEmailAndPassword("email", "password")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(ExampleActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

    }
    public void CreateUser()
    {
        mAuth.createUserWithEmailAndPassword("emailing", "passwording")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(ExampleActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });


    }
}