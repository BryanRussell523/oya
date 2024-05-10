package com.example.oya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShareNote extends AppCompatActivity {
    Toolbar toolbar;
    TextView NoteTitle, NoteContent;
    ImageView selectedImage, shareNote, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_note);
        setStatusBarColor(getResources().getColor(android.R.color.white));
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shareNote = findViewById(R.id.shareNote);
        NoteTitle = findViewById(R.id.NoteTitle);
        NoteContent = findViewById(R.id.NoteContent);
        selectedImage = findViewById(R.id.selectedImage);

        Intent intent = getIntent();
        if (intent != null) {
            String noteId = intent.getStringExtra("noteId");
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");
            String imageUrl = intent.getStringExtra("imageUrl");

            NoteTitle = findViewById(R.id.NoteTitle);
            NoteContent = findViewById(R.id.NoteContent);


            //Set the text of the editTexts
            NoteTitle.setText(title);
            NoteContent.setText(content);


            selectedImage = findViewById(R.id.selectedImage);

            if (imageUrl != null && selectedImage != null) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(com.hbb20.R.drawable.flag_transparent)
                        .error(com.hbb20.R.drawable.flag_transparent);
                Glide.with(this)
                        .load(imageUrl)
                        .apply(requestOptions)
                        .into(selectedImage);
                selectedImage.setVisibility(View.VISIBLE);
            }
            // Retrieve background colors from the database
            DatabaseReference noteRef = FirebaseDatabase.getInstance().getReference()
                    .child("notes")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(noteId);
            noteRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        if (dataSnapshot.hasChild("titleBackgroundColor")) {
                            int titleBgColor = dataSnapshot.child("titleBackgroundColor").getValue(Integer.class);
                            //set the title background color if it exists
                            NoteTitle.setBackgroundColor(titleBgColor);
                        }
                        if (dataSnapshot.hasChild("contentBackgroundColor")) {
                            int contentBgColor = dataSnapshot.child("contentBackgroundColor").getValue(Integer.class);
                            NoteContent.setBackgroundColor(contentBgColor);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            shareNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareNote();
                }
            });
        }

    }
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(color);
        }
    }
    private void shareNote(){
        String title = NoteTitle.getText().toString().trim();
        String content = NoteContent.getText().toString().trim();

        //Create an Intent with ACTION_SEND
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        StringBuilder message = new StringBuilder();
        message.append(title).append("\n\n").append(content);

        //Include the text message in the Intent
        shareIntent.putExtra(Intent.EXTRA_TEXT, message.toString());

        //Get the Uri of the image associated with the ImageView
        Uri imageUri = getimageUri(selectedImage);

        if (imageUri != null) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType("image/*");
        }else {
            shareIntent.setType("text/plain");
        }
        startActivity(Intent.createChooser(shareIntent, "Share Note"));
    }
    private Uri getimageUri(ImageView imageView){
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            return getimageUriFromBitmap(bitmap);
        }
        return null;
    }
    private Uri getimageUriFromBitmap(Bitmap bitmap){
        try {
            File cachePath = new File(getCacheDir(), "images");
            cachePath.mkdirs();
            File imagePath = new File(cachePath, "image.png");
            FileOutputStream outputStream = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG,100, outputStream);
            outputStream.close();
            return FileProvider.getUriForFile(this,"com.example.oya.fileprovider",imagePath);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
