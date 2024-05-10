package com.example.oya.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oya.Object.TaskObject;
import com.example.oya.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private ArrayList<TaskObject> tasks;
    private LayoutInflater inflater;

    private FirebaseFirestore db;

    public TaskAdapter(Context context, ArrayList<TaskObject> tasks) {
        this.context = context;
        this.tasks = tasks;
        inflater = LayoutInflater.from(context);
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_event_item_layout, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskObject task = tasks.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.StartTime.setText(sdf.format(task.getStartTime()));
        holder.EndTime.setText(sdf.format(task.getEndTime()));
        holder.eventName.setText(task.getTitle());
        holder.eventDescription.setText(task.getDescription());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.deleteTask.setVisibility(View.VISIBLE);
                return true;
            }
        });
        holder.deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the ID of the Task to be deleted
                String taskId = task.getTaskId();
                // Log the task ID for debugging
                Log.d("TaskAdapter222", "Task ID: " + taskId);

                //Get reference to the FireStore collection
                if (taskId != null && !taskId.isEmpty()) {


                    CollectionReference tasksRef = db.collection("tasks");

                    //Get reference to the document to be deleted
                    DocumentReference taskDocRef = tasksRef.document(taskId);
                    //Delete the document from FireStore
                    taskDocRef.delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    // Check if position is valid before removing the task
                                    if (position >= 0 && position < tasks.size()) {
                                        // Remove the task from the list
                                        tasks.remove(position);
                                        // Notify the adapter about the task deletion
                                        notifyItemRemoved(position);
                                        // Notify user about the successful deletion
                                        Toast.makeText(context, "Event deleted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Notify the user about the failure
                                    Toast.makeText(context, "Failed to delete event:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    Toast.makeText(context, "Task ID is null or empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView StartTime;
        TextView EndTime;
        TextView eventName;
        TextView eventDescription;
        ImageView deleteTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            StartTime = itemView.findViewById(R.id.StartTime);
            EndTime = itemView.findViewById(R.id.EndTime);
            eventName = itemView.findViewById(R.id.eventName);
            eventDescription = itemView.findViewById(R.id.eventDescription);
            deleteTask = itemView.findViewById(R.id.deleteTask);
        }
    }
}
