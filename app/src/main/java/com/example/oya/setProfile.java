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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;

import java.util.HashMap;
import java.util.Map;

public class setProfile extends AppCompatActivity {
    private EditText usernameInput, descInput;
    private ImageView userImage;
    private RelativeLayout saveprofile;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDB;
    private StorageReference mStorageRef;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        mAuth = FirebaseAuth.getInstance();
        mUserDB = FirebaseDatabase.getInstance().getReference().child("user");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        usernameInput = findViewById(R.id.usernameInput);
        descInput = findViewById(R.id.descInput);
        userImage = findViewById(R.id.userImage);
        saveprofile = findViewById(R.id.saveprofile);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to pick an image from the device's gallery
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            imageUri = data.getData();

            // Set the selected image to the ImageView
            userImage.setImageURI(imageUri);
        }
    }

    private void saveUserProfile() {
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            final String userId = currentUser.getUid();
            final String userName = usernameInput.getText().toString().trim();
            final String name = "";
            final String description = descInput.getText().toString().trim();

            if (!TextUtils.isEmpty(userName) && imageUri != null) {
                final StorageReference imageRef = mStorageRef.child("profile_images/" + userId + ".jpg");

                imageRef.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();

                            // Store user profile data in Firebase Realtime Database
                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("name", name);
                            userMap.put("username", userName);
                            userMap.put("phone", currentUser.getPhoneNumber());
                            userMap.put("image", downloadUri.toString());
                            userMap.put("description", description);

                            mUserDB.child(userId).setValue(userMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Profile saved successfully, navigate to main activity
                                                Intent intent = new Intent(setProfile.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(setProfile.this, "Failed to save profile", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // Handle failures
                            Toast.makeText(setProfile.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(setProfile.this, "Please enter your name and select an image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(setProfile.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
