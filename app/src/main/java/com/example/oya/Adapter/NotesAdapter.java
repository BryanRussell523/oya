package com.example.oya.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.oya.NotesActivity;
import com.example.oya.Object.NotesObject;
import com.example.oya.R;
import com.example.oya.ShareNote;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private Context context;
    private List<NotesObject> notesList;
    private OnNoteItemClickListener onNoteItemClickListener;
    public NotesAdapter(Context context, List<NotesObject> notesList, OnNoteItemClickListener onNoteItemClickListener) {
        this.context = context;
        this.notesList = notesList;
        this.onNoteItemClickListener = onNoteItemClickListener;

    }

    public interface OnNoteItemClickListener {
        void onItemClick(NotesObject notesObject);
    }
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NotesObject notesObject = notesList.get(position);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNoteItemClickListener != null) {
                    onNoteItemClickListener.onItemClick(notesObject);
                }
            }
        });
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Toggle visibility of the delete and share buttons
                holder.deleteNote.setVisibility(View.VISIBLE);
                holder.shareNote.setVisibility(View.VISIBLE);
                holder.cancel.setVisibility(View.VISIBLE);

                holder.deleteNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to delete this note?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Remove the note from the list and notify adapter
                                notesList.remove(position);
                                notifyDataSetChanged();

                                //Delete the note from the database
                                DatabaseReference noteRef = FirebaseDatabase.getInstance().getReference()
                                        .child("notes")
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .child(notesObject.getNoteId());
                                noteRef.removeValue();
                                Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
                                // Hide the delete and share buttons
                                holder.deleteNote.setVisibility(View.GONE);
                                holder.shareNote.setVisibility(View.GONE);
                                holder.cancel.setVisibility(View.GONE);

                            }
                        });
                        builder.setNegativeButton("No,", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                });
                holder.shareNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareNote(position);
                        // Hide the delete and share buttons
                        holder.deleteNote.setVisibility(View.GONE);
                        holder.shareNote.setVisibility(View.GONE);
                        holder.cancel.setVisibility(View.GONE);

                    }
                });
                return true;
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the delete and share buttons
                holder.deleteNote.setVisibility(View.GONE);
                holder.shareNote.setVisibility(View.GONE);
                holder.cancel.setVisibility(View.GONE);

            }
        });

        int backgroundColor = notesObject.getContentBackgroundColor();
        holder.layout.setBackgroundColor(backgroundColor);
        holder.note_title.setText(notesObject.getTitle());
        holder.note_content.setText(notesObject.getContent());

        String imageUrl = notesObject.getimageUrl();

        int radius = 30;
        Drawable backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.note_item_background);
        if (backgroundDrawable instanceof GradientDrawable && holder.layout != null) {
            GradientDrawable gradientDrawable = (GradientDrawable) backgroundDrawable;
            gradientDrawable.setColor(notesObject.getContentBackgroundColor());
            gradientDrawable.setCornerRadius(radius);
            holder.layout.setBackground(gradientDrawable);
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(com.hbb20.R.drawable.flag_transparent)
                .error(com.hbb20.R.drawable.flag_transparent)
                .transform(new RoundedCorners(radius));
        if (holder.note_image != null) {
            Glide.with(context)
                    .load(notesObject.getimageUrl())
                    .apply(requestOptions)
                    .into(holder.note_image);
        } else {
            holder.note_image.setImageDrawable(ContextCompat.getDrawable(context, com.hbb20.R.drawable.flag_transparent));
        }
        holder.note_timestamp.setText(getFormattedTimestamp(notesObject.getTimeStamp()));
    }
    public void shareNote(int position) {
        if (position != -1 && position < notesList.size()) {
            NotesObject note = notesList.get(position);
            Intent intent = new Intent(context, ShareNote.class);
            intent.putExtra("noteId",note.getNoteId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getContent());
            intent.putExtra("imageUrl", note.getimageUrl());
            context.startActivity(intent);
        } else {
            Log.e("NotesAdapter3333", "Invalid position: " + position);
        }
    }
    private String getFormattedTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long difference = currentTime - timestamp;
        long differenceSeconds = difference / 1000;

        if (differenceSeconds < 60) {
            return "Just now";
        } else if (differenceSeconds < 3600) {
            long minutes = differenceSeconds / 60;
            return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        } else if (differenceSeconds < 86400) {
            long hours = differenceSeconds / 3600;
            return hours + (hours == 1 ? " hour ago" : " hours ago");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            Date date = new Date(timestamp);
            return sdf.format(date);
        }
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView note_title, note_content, note_timestamp, cancel;
        ImageView deleteNote, shareNote, note_image;


        LinearLayout layout;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            deleteNote = itemView.findViewById(R.id.deleteNote);
            shareNote = itemView.findViewById(R.id.shareNote);
            cancel = itemView.findViewById(R.id.cancel);

            note_title = itemView.findViewById(R.id.note_title);
            note_content = itemView.findViewById(R.id.note_content);
            note_timestamp = itemView.findViewById(R.id.note_timestamp);
            note_image = itemView.findViewById(R.id.note_image);
        }
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
