package com.example.oya;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
                openImagePicker();
            }
        });

        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String description = descInput.getText().toString().trim();
                saveProfile(username, description);
            }
        });

        checkProfileAndNavigate();
    }

    private void openImagePicker() {
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


    private void saveProfile(String username, String description) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            if (selectedImageUri != null) {
                ContentResolver contentResolver = getContentResolver();
                String mimeType = contentResolver.getType(selectedImageUri);
                String fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                String fileName = userId + "." + fileExtension;

                StorageReference imageRef = storageReference.child(fileName);
                imageRef.putFile(selectedImageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            // Image uploaded successfully
                            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();

                                // Create a UserObject with the obtained details
                                UserObject user = new UserObject(userId, currentUser.getPhoneNumber(), username, imageUrl, description);
                                user.setProfileSet(true);

                                // Save the user profile to the Firebase Realtime Database
                                saveUserToDatabase(user);
                            });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        });
            } else {
                // No image selected
                UserObject user = new UserObject(userId, currentUser.getPhoneNumber(), username, null, description);
                user.setProfileSet(true);
                saveUserToDatabase(user);
            }
        }
    }

    private void saveUserToDatabase(UserObject user) {
        DatabaseReference userRef = databaseReference.child(user.getUid());
        userRef.setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Snackbar.make(findViewById(R.id.setProfile), "Profile saved successfully", Snackbar.LENGTH_SHORT).show();
                    navigateToDestination();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(setProfile.this, "Failed to save profile...", Toast.LENGTH_SHORT).show();
                });
    }

    private void navigateToDestination() {
        Intent intent = new Intent(setProfile.this, homeActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }

    private void checkProfileAndNavigate() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Boolean isProfileSet = dataSnapshot.child("profileSet").getValue(Boolean.class);
                        if (isProfileSet != null && isProfileSet) {
                            // User has set the profile, navigate to homeActivity
                            navigateToDestination();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                }
            });
        }
    }
}
