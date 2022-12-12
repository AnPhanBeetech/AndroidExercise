package com.example.androidexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_GALLERY = 100;
    public static final int REQUEST_CAMERA = 200;
    public static final int REQUEST_CAMERA_PERMISSION = 500;

    protected String[] mDataset;
    RecyclerView myRecyclerView;
    RecyclerAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    Button sendChatBtn;
    ImageButton openGalleryBtn;
    ImageButton openCameraBtn;
    EditText textChat;

    List<ItemChat> listchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initDataset();

        sendChatBtn = findViewById(R.id.send_chat_btn);
        sendChatBtn.setOnClickListener(this);
        textChat = findViewById(R.id.text_chat);
        openGalleryBtn = findViewById(R.id.galley_button);
        openGalleryBtn.setOnClickListener(this);
        openCameraBtn = findViewById(R.id.camera_btn);
        openCameraBtn.setOnClickListener(this);

        myRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(mLayoutManager);

        listchat = new ArrayList<>();
        listchat.add(new ItemChat(RecyclerAdapter.LayoutOppText,"Hi man"));

        //listchat.add(new ItemChat(RecyclerAdapter.LayoutMainImg,"IMG"));
        //listchat.add(new ItemChat(RecyclerAdapter.LayoutOppType,"Typing"));

        mAdapter = new RecyclerAdapter(listchat);
        myRecyclerView.setAdapter(mAdapter);


    }

    private void initDataset() {
        mDataset = new String[5];
        for (int i = 0; i < 5; i++) {
            mDataset[i] = "This is element #" + i;
        }
    }
    public void SendChat()
    {
        if (textChat.getText().length() > 0) {
            mAdapter.listChatItem.add(new ItemChat(RecyclerAdapter.LayoutMainText, textChat.getText().toString()));
            mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
            mAdapter.notifyDataSetChanged();
            textChat.setText("");
            final Handler handlertyping = new Handler(Looper.getMainLooper());
            handlertyping.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.listChatItem.add(new ItemChat(RecyclerAdapter.LayoutOppType, "Typing ..."));
                    mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
                    mAdapter.notifyDataSetChanged();
                }
            }, 300);

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.listChatItem.remove(mAdapter.listChatItem.size() - 1);
                    mAdapter.listChatItem.add(new ItemChat(RecyclerAdapter.LayoutOppText, "Tin nhắn trả lời tự động"));
                    mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
                    mAdapter.notifyDataSetChanged();
                }
            }, 2000);
        }
    }

    @Override
    public void onClick(View v) {
        //Log.d("DAF", "Send");
        if (v.getId() == R.id.send_chat_btn)
            SendChat();
        else if (v.getId() == R.id.galley_button)
            OpenGallery();
        else if (v.getId() == R.id.camera_btn)
            OpenCamera();
    }
    public void OpenGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == REQUEST_GALLERY) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    Log.d("DAF", selectedImageUri.toString());
                    mAdapter.listChatItem.add(new ItemChat(RecyclerAdapter.LayoutMainImg, selectedImageUri));
                    mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
                    mAdapter.notifyDataSetChanged();
                    //IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
            if (requestCode == REQUEST_CAMERA)
            {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                mAdapter.listChatItem.add(new ItemChat(RecyclerAdapter.LayoutMainImg, photo));
                mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void OpenCamera() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        try {
//            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
//        } catch (ActivityNotFoundException e) {
//            // display error state to the user
//        }
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }
        }
    }


}