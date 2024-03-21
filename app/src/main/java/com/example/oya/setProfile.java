package com.example.oya;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oya.Object.UserObject;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;

public class setProfile extends AppCompatActivity {
    private ImageView userImage;
    private EditText usernameInput, descInput;
    private RelativeLayout saveprofile;
    private Uri selectedImageUri;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        storageReference = FirebaseStorage.getInstance().getReference().child("profile_images");



        userImage = findViewById(R.id.userImage);
        usernameInput = findViewById(R.id.usernameInput);
        descInput = findViewById(R.id.descInput);
        saveprofile = findViewById(R.id.saveprofile);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfileImage();
            }
        });
        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();

            }
        });
    }

    private void selectProfileImage() {
        // Use an intent to open the image picker activity
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // Start the activity for result, expecting a selected image
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            selectedImageUri = data.getData();

            // Display the selected image in the ImageView
            userImage.setImageURI(selectedImageUri);
        }
    }
    private void saveProfile() {
        String username = usernameInput.getText().toString().trim();
        String description = descInput.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("user");
            //Save the username and description
            DatabaseReference userRef = usersRef.child(userId);
            userRef.child("username").setValue(username);
            userRef.child("description").setValue(description);

            // Check if an image is selected
            if (selectedImageUri != null) {
                StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+ getFileExtension(selectedImageUri));
                fileReference.putFile(selectedImageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                           //image upload successful, get the download URL
                            fileReference.getDownloadUrl().addOnSuccessListener(uri ->{
                                userRef.child("profileImageUrl").setValue(uri.toString());
                            });
                        })
                        .addOnFailureListener(e->{
                            Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        });
            }
            Snackbar.make(findViewById(R.id.setProfile),"Profile saved", Snackbar.LENGTH_SHORT).show();
            startActivity(new Intent(setProfile.this, homeActivity.class));
            finish();
        }else{
            Snackbar.make(findViewById(R.id.setProfile),"User profile failed",Snackbar.LENGTH_SHORT).show();
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
