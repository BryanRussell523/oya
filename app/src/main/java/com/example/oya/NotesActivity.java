package com.example.oya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.oya.Adapter.NotesAdapter;
import com.example.oya.Object.NotesObject;
import com.example.oya.Object.UserObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements NotesAdapter.OnNoteItemClickListener {
    private Toolbar toolbar;
    private RecyclerView notesRecyclerView;
    private NotesAdapter notesAdapter;
    private List<NotesObject> notesList = new ArrayList<>();
    ImageView fab, calculator, calendar, notes, newNote;
    //Edit note request to get result from edit note activity
    private static final int EDIT_NOTE_REQUEST_CODE = 1001;

    //Request add note to refresh the Notes Activity and load new note added
    private static final int REQUEST_ADD_NOTE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        setStatusBarColor(getResources().getColor(android.R.color.white));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        newNote = findViewById(R.id.newNote);
        // Initialize RecyclerView
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesList = new ArrayList<>();
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesAdapter = new NotesAdapter(this, notesList, this);
        notesRecyclerView.setAdapter(notesAdapter);


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        notesRecyclerView.setLayoutManager(layoutManager);

        // Retrieve notes data from Realtime Database
        retrieveNotes();

        fab = findViewById(R.id.fab);
        calculator = findViewById(R.id.calculator);
        calendar = findViewById(R.id.calendar);
        notes = findViewById(R.id.notes);

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesActivity.this, NotePad.class);
                startActivityForResult(intent, REQUEST_ADD_NOTE);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the visibility of the calendar, notes, and calculator buttons
                if (calendar.getVisibility() == View.VISIBLE) {
                    calendar.setVisibility(View.GONE);
                    notes.setVisibility(View.GONE);
                    calculator.setVisibility(View.GONE);
                    fab.setImageResource(R.drawable.tools); // Change to the open icon
                } else {
                    calendar.setVisibility(View.VISIBLE);
                    notes.setVisibility(View.VISIBLE);
                    calculator.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.drawable.close); // Change to the close icon
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }
            }
        });
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the image resource of the FAB to the calculator image
                fab.setImageResource(R.drawable.filledcalculator);

                // Hide other buttons if they are visible
                if (calendar.getVisibility() == View.VISIBLE) {
                    calendar.setVisibility(View.GONE);
                    notes.setVisibility(View.GONE);
                    calculator.setVisibility(View.GONE);
                } else {
                    // Show other buttons
                    calendar.setVisibility(View.VISIBLE);
                    notes.setVisibility(View.VISIBLE);
                    calculator.setVisibility(View.VISIBLE);
                }
                // Start the Notes activity
                Intent intent = new Intent(NotesActivity.this, CalculatorActivity.class);
                startActivity(intent);
                finish();

            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the image resource of the FAB to the calculator image
                fab.setImageResource(R.drawable.filledcalendar);

                // Hide other buttons if they are visible
                if (calendar.getVisibility() == View.VISIBLE) {
                    calendar.setVisibility(View.GONE);
                    notes.setVisibility(View.GONE);
                    calculator.setVisibility(View.GONE);
                } else {
                    // Show other buttons
                    calendar.setVisibility(View.VISIBLE);
                    notes.setVisibility(View.VISIBLE);
                    calculator.setVisibility(View.VISIBLE);
                }
                // Start the Calendar activity
                Intent intent = new Intent(NotesActivity.this, CalendarActivity.class);
                startActivity(intent);
                finish();
            }
        });
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the image resource of the FAB to the calculator image
                fab.setImageResource(R.drawable.fillednote);
                // Hide other buttons if they are visible
                if (calendar.getVisibility() == View.VISIBLE) {
                    calendar.setVisibility(View.GONE);
                    notes.setVisibility(View.GONE);
                    calculator.setVisibility(View.GONE);
                } else {
                    // Show other buttons
                    calendar.setVisibility(View.VISIBLE);
                    notes.setVisibility(View.VISIBLE);
                    calculator.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void retrieveNotes() {
        // Get the current user's ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userNoteRef = FirebaseDatabase.getInstance().getReference().child("notes").child(userId);
        // Add a listener to retrieve the notes data
        userNoteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notesList.clear();

                // Iterate through the dataSnapshot to retrieve each note
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    // Get the note data from the snapshot
                    String noteId = noteSnapshot.getKey();
                    String title = noteSnapshot.child("title").getValue(String.class); // Ensure correct field is retrieved for title
                    String content = noteSnapshot.child("content").getValue(String.class);
                    String imageUrl = noteSnapshot.child("image").getValue(String.class);
                    int titleBackgroundColor = -1;
                    int contentBackgroundColor = -1; // Default value for contentBackgroundColor
                    long timeStamp = -1; // Default value for timeStamp
                    // Check if the contentBackgroundColor and timeStamp exist in the snapshot
                    if (noteSnapshot.hasChild("contentBackgroundColor")) {
                        contentBackgroundColor = noteSnapshot.child("contentBackgroundColor").getValue(Integer.class);
                    }
                    if (noteSnapshot.hasChild("timeStamp")) {
                        timeStamp = noteSnapshot.child("timeStamp").getValue(long.class);
                    }
                    // Create a NotesObject instance only if all required data is not null
                    if (noteId != null && title != null && content != null && timeStamp != -1) {
                        NotesObject notesObject = new NotesObject(noteId, userId, title, content, imageUrl, titleBackgroundColor, contentBackgroundColor, timeStamp);
                        // Add the note to the beginning of the list
                        notesList.add(0, notesObject);
                    }
                }
                // Notify the adapter about the data change
                notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur during the data retrieval
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Restore status bar color to the default color
        setStatusBarColor(getResources().getColor(R.color.lightblue));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Set activity title
        getSupportActionBar().setTitle("Notepad");
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onItemClick(NotesObject notesObject) {
        Intent intent = new Intent(this, EditNote.class);
        intent.putExtra("noteId", notesObject.getNoteId());
        intent.putExtra("title", notesObject.getTitle());
        intent.putExtra("content", notesObject.getContent());
        intent.putExtra("imageUrl", notesObject.getimageUrl());
        intent.putExtra("titleBgColor", notesObject.getTitleBackgroundColor());
        intent.putExtra("contentBgColor", notesObject.getContentBackgroundColor());

        //send edit request through intent
        startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);
    }

    //Receive requested edited data if available after coming back from the EditNote activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                // Update the note object with edited data
                String editedNoteId = data.getStringExtra("noteId");
                String editedTitle = data.getStringExtra("title");
                String editedContent = data.getStringExtra("content");
                String editedImage = data.getStringExtra("image");

                //Fetch the updated content background color from the database
                DatabaseReference editedNoteRef = FirebaseDatabase.getInstance().getReference().child("notes")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child(editedNoteId)
                        .child("contentBackgroundColor");
                editedNoteRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            int editedContentBackgroundColor = dataSnapshot.getValue(Integer.class);
                            //Find the corresponding NotesObject in the List
                            for (NotesObject note : notesList) {
                                if (note.getNoteId().equals(editedNoteId)) {
                                    note.setTitle(editedTitle);
                                    note.setContent(editedContent);
                                    note.setimageUrl(editedImage);
                                    note.setContentBackgroundColor(editedContentBackgroundColor);
                                    //Notify the adapter about the data change
                                    notesAdapter.notifyDataSetChanged();
                                    retrieveNotes();
                                    break; //Exit the loop once the note is found
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("NotesActivity", "Error fetching content background color: " + databaseError.getMessage());
                    }
                });
            }
        } else if (requestCode == REQUEST_ADD_NOTE && resultCode == RESULT_OK) {
            if (data != null) {
                // Retrieve the new note data from the intent
                String newNoteId = data.getStringExtra("noteId");
                String newNoteTitle = data.getStringExtra("title");
                String newNoteContent = data.getStringExtra("content");

                // Retrieve the image URL and content background color from the database
                DatabaseReference newNoteRef = FirebaseDatabase.getInstance().getReference()
                        .child("notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(newNoteId);
                newNoteRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String newNoteImage = dataSnapshot.child("image").getValue(String.class);
                            int contentBackgroundColor = dataSnapshot.child("contentBackgroundColor").getValue(Integer.class);
                            // Add the new note to the list
                            NotesObject newNote = new NotesObject(newNoteId, FirebaseAuth.getInstance().getCurrentUser().getUid(), newNoteTitle, newNoteContent, newNoteImage, -1, contentBackgroundColor, System.currentTimeMillis());
                            notesList.add(newNote);
                            // Notify the adapter about the data change
                            notesAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors
                    }
                });
            }
        }
    }
}
