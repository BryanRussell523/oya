package com.example.oya;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oya.Adapter.CalendarGridAdapter;
import com.example.oya.Adapter.TaskAdapter;
import com.example.oya.Object.CalendarObject;
import com.example.oya.Object.TaskObject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class CalendarActivity extends AppCompatActivity implements CalendarGridAdapter.OnDateClickListener {
    private Toolbar toolbar;
    private GridView calendarGridView;
    private CalendarGridAdapter calendarGridAdapter;

    private TextView monthTextView;
    private Calendar currentCalendar;

    private FirebaseAuth mAuth;
    private String userId;
    private RelativeLayout taskContainer;

    private EditText eventInput;
    private EditText descInput;

    private TextView dateView;

    private ImageView dateButton;


    private TextView startTimeTextView;
    private ImageView startTimePicker;

    private TextView endTimeTextView;
    private ImageView endTimePicker;

    private CheckBox remindMe;

    private Spinner selectCategory;
    private ImageView categoryPicker;
    private TextView newCatergory;
    private RelativeLayout createTask;


    private RecyclerView taskRecyclerView;
    private CardView cardView;
    private ImageView fab, calculator, calendar, notes;
    private TextView textBelowFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setStatusBarColor(getResources().getColor(android.R.color.white));
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Calendar");
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        }
        mAuth = FirebaseAuth.getInstance();

        //Get the Current User
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //User is signed in, get the userID
            userId = currentUser.getUid();
        } else {

            Intent intent = new Intent(CalendarActivity.this, SignUp.class);
            startActivity(intent);
            finish();
        }



        //Initialize views from the custom dialog layout

        eventInput = findViewById(R.id.eventInput);
        descInput = findViewById(R.id.descInput);
        dateView = findViewById(R.id.dateView);
        dateButton = findViewById(R.id.dateButton);
        startTimeTextView = findViewById(R.id.startTimeTextView);
        startTimePicker = findViewById(R.id.startTimePicker);
        endTimeTextView = findViewById(R.id.endTimeTextView);
        endTimePicker = findViewById(R.id.endTimePicker);
        remindMe = findViewById(R.id.remindMe);
        selectCategory = findViewById(R.id.selectCategory);
        categoryPicker = findViewById(R.id.categoryPicker);
        newCatergory = findViewById(R.id.newCatergory);
        createTask = findViewById(R.id.createTask);

        //EventListeners and task logic
        //Task Start Time Logic
        startTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Current Time
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                //Create a Start TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Update the textView with the selected time
                        startTimeTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                    }
                }, hour, minute, true); //Use 24-hour format
                timePickerDialog.show();
            }
        });
        //Event End Time Logic
        endTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Current time
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                //Create End Time Picking Logic
                TimePickerDialog timePickerDialog = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Update the textView with the selected time
                        endTimeTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });



        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get information from fields
                String eventName = eventInput.getText().toString().trim();
                String eventDescription = descInput.getText().toString().trim();
                String date = dateView.getText().toString().trim();
                String startTime = startTimeTextView.getText().toString().trim();
                String endTime = endTimeTextView.getText().toString().trim();
                boolean reminder = remindMe.isChecked();
                String category = null;
                if (selectCategory.getSelectedItem() != null) {
                    category = selectCategory.getSelectedItem().toString();
                }

                //Check If any required field is empty
                if (eventName.isEmpty() || eventDescription.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                    //Show a message if any of the fields are empty
                    Snackbar.make(findViewById(R.id.layout), "All fields are required", Snackbar.LENGTH_SHORT).show();
                } else {
                    //If all the fields are filled, save event to firebase
                    saveTaskToFireStore(eventName, eventDescription, date, startTime, endTime, reminder, category);
                    hideRelativeLayout();
                }
            }
        });
        monthTextView = findViewById(R.id.monthTextView);
        calendarGridView = findViewById(R.id.calendarGridView);

        currentCalendar = Calendar.getInstance();

        // Setting up the initial calendar view
        updateCalendar(currentCalendar);

        // Setting previous month button click listener
        findViewById(R.id.monthBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.MONTH, -1);
                updateCalendar(currentCalendar);
            }
        });

        // Setting up the next month button click listener
        findViewById(R.id.monthForward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.MONTH, 1);
                updateCalendar(currentCalendar);
            }
        });

        // Set item click listener to handle date selection
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalendarObject selectedDate = (CalendarObject) calendarGridAdapter.getItem(position);
                if (selectedDate != null) {
                    //Extract date number and day of week from selected date

                    int dateNumber = selectedDate.getDay();
                    String dayOfWeek = getDayOfWeek(selectedDate.getDate());

                }
            }
        });
        fab = findViewById(R.id.fab);
        calculator = findViewById(R.id.calculator);
        calendar = findViewById(R.id.calendar);
        notes = findViewById(R.id.notes);

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
                // Start the Calendar activity
                Intent intent = new Intent(CalendarActivity.this, CalculatorActivity.class);
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
                // Start the Notes activity
                Intent intent = new Intent(CalendarActivity.this, NotesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Display Tasks in RecyclerView
        fetchTasksAndSetUpRecyclerView();

    }
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setStatusBarColor(getResources().getColor(R.color.lightblue));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Calendar");
        }
    }

    private String getDayOfWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(date);
    }

    private void updateCalendar(Calendar calendar) {
        updateMonthName(calendar);

        // Generate dates for the specified month
        ArrayList<CalendarObject> dates = generateDates(calendar);

        // Update calendar grid
        calendarGridAdapter = new CalendarGridAdapter(this, dates);
        calendarGridAdapter.setOnDateClickListener(this);
        calendarGridView.setAdapter(calendarGridAdapter);
    }

    private void updateMonthName(Calendar calendar) {
        SimpleDateFormat monthDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        String monthName = monthDateFormat.format(calendar.getTime());
        monthTextView.setText(monthName);
    }

    private ArrayList<CalendarObject> generateDates(Calendar calendar) {
        ArrayList<CalendarObject> dates = new ArrayList<>();
        Calendar tempCalendar = (Calendar) calendar.clone();
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1); // Set to first day of the month
        // Get the first day of the week
        int firstDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);

        // Move calendar to the beginning of the week
        tempCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek + 1);

        // Generate dates for the calendar grid
        while (dates.size() < 42) { // 6 rows * 7 columns
            dates.add(new CalendarObject(tempCalendar.getTime()));
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    @Override
    public void onDateClick(int dateNumber, String dayOfWeek) {
        // Show the AlertDialog to add a task
        //showAddTaskDialog(dateNumber, dayOfWeek);
        taskContainer = findViewById(R.id.taskContainer);
        taskContainer.setVisibility(View.VISIBLE);
        cardView = findViewById(R.id.cardView);
        cardView.setVisibility(View.GONE);
        textBelowFab = findViewById(R.id.textBelowFab);
        textBelowFab.setVisibility(View.GONE);
        dateView.setText(dayOfWeek + ", " + dateNumber);

    }
    //Task/Event logic
    private void saveTaskToFireStore(String eventName, String eventDescription, String date, String startTime, String endTime, boolean reminder, String category) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        // Convert the start time and end time to Date objects
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(startTime);
            endDate = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Generate a unique task ID
        String taskId = UUID.randomUUID().toString();

        // Create a new TaskObject with the generated task ID
        TaskObject task = new TaskObject(taskId, userId, eventName, eventDescription, currentDate, startDate, endDate, reminder, category);

        // Get the reference to the Firebase Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Add the task (Event) to FireStore
        db.collection("tasks")
                .document(taskId) // Use the task ID as the document ID
                .set(task) // Set the task object as the document data
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.layout), "Event added successfully", Snackbar.LENGTH_SHORT).show();

                        //After successfully adding the Event, fetch tasks again and update the recyclerView
                        fetchTasksAndSetUpRecyclerView();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Show error message in Snack bar
                        Snackbar.make(findViewById(R.id.layout), "Error: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        //Check if relativeLayout is Visible
        taskContainer = findViewById(R.id.taskContainer);
        if (taskContainer.getVisibility() == View.VISIBLE) {
            hideRelativeLayout();

        } else {

            super.onBackPressed();
        }
    }

    private void hideRelativeLayout() {
        taskContainer = findViewById(R.id.taskContainer);
        taskContainer.setVisibility(View.GONE);
        cardView = findViewById(R.id.cardView);
        cardView.setVisibility(View.VISIBLE);
        textBelowFab = findViewById(R.id.textBelowFab);
        textBelowFab.setVisibility(View.VISIBLE);
    }
    private void fetchTasksAndSetUpRecyclerView() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .whereEqualTo("userId",userId)//Filter Tasks by UserID
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<TaskObject> tasks = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                            TaskObject task = document.toObject(TaskObject.class);
                            tasks.add(task);
                        }
                        //Pass the tasks to the adapter and set it to the ReyclerView
                        TaskAdapter adapter = new TaskAdapter(CalendarActivity.this, tasks);
                        taskRecyclerView = findViewById(R.id.taskRecyclerView);
                        taskRecyclerView.setAdapter(adapter);
                        taskRecyclerView.setLayoutManager(new LinearLayoutManager(CalendarActivity.this));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors
                        Log.e("Firestore", "Error getting tasks", e);

                    }
                });
    }
}
