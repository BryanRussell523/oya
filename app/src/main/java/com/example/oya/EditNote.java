package com.example.oya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.protobuf.StringValueOrBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.grpc.Server;

public class EditNote extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    Toolbar toolbar;
    ImageView back;
    EditText NoteTitle, NoteContent;
    ImageView selectedImage, notePadattach, notePadmenu;
    TextView done;
    DatabaseReference userNotesRef;
    FirebaseAuth mAuth;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        setStatusBarColor(getResources().getColor(android.R.color.white));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit note");
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        }

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
            selectedImage = findViewById(R.id.selectedImage);

            selectedImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showDeleteImageButton();
                    return true;
                }
            });
        }

        notePadattach = findViewById(R.id.notePadattach);
        notePadmenu = findViewById(R.id.notePadmenu);
        done = findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
                Intent intent = new Intent(EditNote.this, NotesActivity.class);
                startActivity(intent);
            }
        });

        notePadattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermission();
            }
        });

        notePadmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomMenu(v);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        userNotesRef = FirebaseDatabase.getInstance().getReference().child("notes").child(userId);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Restore status bar color to the default color
        setStatusBarColor(getResources().getColor(R.color.lightblue));
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(color);
        }
    }

    private void showDeleteImageButton() {
        findViewById(R.id.deleteImage).setVisibility(View.VISIBLE);

        findViewById(R.id.deleteImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditNote.this);
                builder.setMessage("Delete image?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedImage.setImageURI(null);
                                selectedImage.setVisibility(View.GONE);
                                // Hide the delete image button
                                findViewById(R.id.deleteImage).setVisibility(View.GONE);
                                Snackbar.make(findViewById(R.id.layout), "Image deleted", Snackbar.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            // Permission has already been granted
            openImagePicker();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            // Check if the permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, proceed with image selection
                openImagePicker();
            } else {
                // Permission is denied, show a toast or handle accordingly
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the image URI from the result intent
            imageUri = data.getData();
            // Load the selected image into the ImageView or do any other operation
            selectedImage.setImageURI(imageUri);
            selectedImage.setVisibility(View.VISIBLE);
        }
    }

    private void showCustomMenu(View anchorView) {
        // Inflate the custom layout
        View customMenuView = getLayoutInflater().inflate(R.layout.notepad_custom_menu, null);
        PopupWindow popupWindow = new PopupWindow(customMenuView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(8f);
        }

        // Handle clicks on color options
        // Set onclick listener to Change the background color of EditTexts to purple
        ImageView purple = customMenuView.findViewById(R.id.purple);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteTitle.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.Opacity_purple));
                NoteContent.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.purple));
                popupWindow.dismiss();

            }
        });

        // Set onclick listener to Change the background color of EditTexts to pink
        ImageView pink = customMenuView.findViewById(R.id.pink);
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteTitle.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.Opacity_pink));
                NoteContent.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.pink));
                popupWindow.dismiss();
            }
        });

        // Set onclick listener to Change the background color of EditTexts to lavender
        ImageView lavender = customMenuView.findViewById(R.id.lavender);
        lavender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteTitle.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.Opacity_lavender));
                NoteContent.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.lavender));
                popupWindow.dismiss();
            }
        });

        // Set onclick listener to Change the background color of EditTexts to lavender
        ImageView green = customMenuView.findViewById(R.id.green);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteTitle.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.Opacity_green));
                NoteContent.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.green));
                popupWindow.dismiss();
            }
        });
        // Set onclick listener to Change the background color of EditTexts to lavender
        ImageView lemon = customMenuView.findViewById(R.id.lemon);
        lemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteTitle.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.Opacity_lemon));
                NoteContent.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.lemon));
                popupWindow.dismiss();
            }
        });
        // Set onclick listener to Change the background color of EditTexts to lavender
        ImageView yellow = customMenuView.findViewById(R.id.yellow);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteTitle.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.Opacity_yellow));
                NoteContent.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.yellow));
                popupWindow.dismiss();
            }
        });
        // Set onclick listener to Change the background color of EditTexts to lavender
        ImageView orange = customMenuView.findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteTitle.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.Opacity_orange));
                NoteContent.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.orange));
                popupWindow.dismiss();
            }
        });
        // Set onclick listener to Change the background color of EditTexts to lavender
        ImageView teal = customMenuView.findViewById(R.id.teal);
        teal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteTitle.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.Opacity_teal));
                NoteContent.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.teal));
                popupWindow.dismiss();
            }
        });
        // Set onclick listener to Change the background color of EditTexts to lavender
        ImageView grey = customMenuView.findViewById(R.id.grey);
        grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteTitle.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.Opacity_lightergrey));
                NoteContent.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.lightergrey));
                popupWindow.dismiss();
            }
        });
        // Set onclick listener to Change the background color of EditTexts to lavender
        ImageView white = customMenuView.findViewById(R.id.white);
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteTitle.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.Opacity_white));
                NoteContent.setBackgroundColor(ContextCompat.getColor(EditNote.this, R.color.white));
                popupWindow.dismiss();
            }
        });
        ImageView share = customMenuView.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareNote();

            }
        });
        ImageView delete = customMenuView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();

            }
        });

        popupWindow.showAsDropDown(anchorView);
    }

    private void saveNote() {
        String updatedTitle = NoteTitle.getText().toString().trim();
        String updatedContent = NoteContent.getText().toString().trim();

        //Check if both title and content re not empty
        if (!updatedTitle.isEmpty() && !updatedContent.isEmpty()) {
            Intent intent = getIntent();
            if (intent != null) {
                String noteId = intent.getStringExtra("noteId");
                //Update the note in the database
                DatabaseReference noteRef = FirebaseDatabase.getInstance().getReference()
                        .child("notes")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(noteId);
                noteRef.child("title").setValue(updatedTitle);
                noteRef.child("content").setValue(updatedContent);

                //If there is an image selected, upload it to Firebase Storage and save its URL
                if (imageUri != null) {
                    //Upload to firebase Storage
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                            .child("images")
                            .child(noteId + ".jpg");
                    storageReference.putFile(imageUri)
                            .addOnSuccessListener(taskSnapshot -> {
                                //Image uploaded successfully, get its download URL
                                storageReference.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            noteRef.child("image").setValue(uri.toString());
                                        })
                                        .addOnFailureListener(e -> {

                                        });
                            })
                            .addOnFailureListener(e -> {

                            });
                } else {
                    //Check if the image should be removed
                    if (selectedImage.getVisibility() == View.GONE) {
                        //Remove the image URL from the database
                        noteRef.child("image").removeValue();
                    }
                }
                //Save the background colors of the title and content
                noteRef.child("titleBackgroundColor").setValue(getEdittextBackgroundColor(NoteTitle));
                noteRef.child("contentBackgroundColor").setValue(getEdittextBackgroundColor(NoteContent));
                // Show a Snackbar message indicating success
                Snackbar.make(findViewById(R.id.layout), "Note saved successfully", Snackbar.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update note. Please try again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Title and content cannot be empty.", Toast.LENGTH_SHORT).show();
        }
    }

    private int getEdittextBackgroundColor(EditText editText) {
        int color = 0;
        Drawable background = editText.getBackground();
        if (background instanceof ColorDrawable) {
            color = ((ColorDrawable) background).getColor();
        } else if (background instanceof StateListDrawable) {
            // If the background is a StateListDrawable, get the color from the default state
            StateListDrawable stateListDrawable = (StateListDrawable) background;
            Drawable.ConstantState constantState = stateListDrawable.getConstantState();
            if (constantState != null) {
                Drawable drawable = constantState.newDrawable();
                if (drawable instanceof ColorDrawable) {
                    color = ((ColorDrawable) drawable).getColor();
                }
            }
        }
        return color;
    }
    private void shareNote() {
        String title = NoteTitle.getText().toString().trim();
        String content = NoteContent.getText().toString().trim();

        // Create an Intent with ACTION_SEND
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        // Construct the message
        StringBuilder message = new StringBuilder();
        message.append(title).append("\n\n").append(content);

        // Include the text message in the Intent
        shareIntent.putExtra(Intent.EXTRA_TEXT, message.toString());

        if (imageUri != null) {
            // Add the image URI to the Intent if available
            File tempFile = createTempFileFromUri(imageUri);
            if (tempFile != null) {
                Uri tempUri = FileProvider.getUriForFile(this, "com.example.oya.fileprovider", tempFile);
                shareIntent.putExtra(Intent.EXTRA_STREAM, tempUri);
                shareIntent.setType("image/*");
            } else {
            }
        } else {
            // Set the MIME type to indicate that we are sharing text only
            shareIntent.setType("text/plain");
        }
        // Start the activity with the Intent
        startActivity(Intent.createChooser(shareIntent, "Share Note"));
    }

    private File createTempFileFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File tempFile = new File(getCacheDir(), "temp_image.jpg");
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.close();
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void deleteNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete the note from the database
                        // First, check if there is a note to delete
                        String title = NoteTitle.getText().toString().trim();
                        String content = NoteContent.getText().toString().trim();
                        if (title.isEmpty() && content.isEmpty()) {
                            Snackbar.make(findViewById(R.id.layout), "Note is empty. Nothing to delete.", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if (userNotesRef != null && imageUri == null) {
                            // Delete the note from the database
                            userNotesRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Clear the EditTexts and ImageView
                                    NoteTitle.setText("");
                                    NoteContent.setText("");
                                    selectedImage.setImageURI(null);
                                    selectedImage.setVisibility(View.GONE);
                                    // Show Snackbar message indicating successful note deletion
                                    Snackbar.make(findViewById(R.id.layout), "Note deleted successfully", Snackbar.LENGTH_SHORT).show();
                                    // Finish the activity after deleting the note
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Show Snackbar message indicating failed note delete operation
                                    Snackbar.make(findViewById(R.id.layout), "Failed to delete note: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            // Show Snackbar message indicating that the note is not saved, nothing to delete
                            Snackbar.make(findViewById(R.id.layout), "Note is not saved. Nothing to delete.", Snackbar.LENGTH_SHORT).show();
                        }

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Save the note when the activity goes into the background
        // saveNote();
    }
}
