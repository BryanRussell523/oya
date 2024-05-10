package com.example.oya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {
    private TextView display;
    private String currentInput = "";
    private double result = 0.0;
    private String currentOperator = "";
    ImageView fab,calculator,calendar,notes;
    Button btn_clear;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        setStatusBarColor(getResources().getColor(android.R.color.white));

         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the text color of the toolbar to black
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Calculator");
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        }
        display = findViewById(R.id.display);
        btn_clear = findViewById(R.id.btn_clear);
        fab = findViewById(R.id.fab);

        calculator = findViewById(R.id.calculator);
        calendar = findViewById(R.id.calendar);
        notes = findViewById(R.id.notes);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the visibility of the calendar, notes, and calculator buttons
                if(calendar.getVisibility() == View.VISIBLE) {
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
                Intent intent = new Intent(CalculatorActivity.this, CalendarActivity.class);
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
                // Start the Notes activity
                Intent intent = new Intent(CalculatorActivity.this, NotesActivity.class);
                startActivity(intent);
                finish();
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
        getSupportActionBar().setTitle("Calculator");
        display = findViewById(R.id.display);
    }
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(color);
        }
    }
    public void onDigitClick(View view) {
        Button button = (Button) view;
        currentInput += button.getText().toString();
        updateDisplay();
    }

    public void onOperatorClick(View view) {
        // Check if currentInput is not empty
        if (!currentInput.isEmpty()) {
            // Perform the operation
            currentOperator = ((Button) view).getText().toString();
            result = Double.parseDouble(currentInput);
            currentInput = "";
        }
    }
    public void onEqualsClick(View view) {
        double secondOperand = Double.parseDouble(currentInput);
        switch (currentOperator) {
            case "+":
                result += secondOperand;
                break;
            case "-":
                result -= secondOperand;
                break;
            case "*":
                result *= secondOperand;
                break;
            case "/":
                if (secondOperand != 0) {
                    result /= secondOperand;
                } else {
                    result = 0.0; // Handle division by zero
                }
                break;
        }
        currentInput = String.valueOf(result);
        updateDisplay();
    }

    public void clearDisplay(View view) {
        currentInput = "";
        result = 0.0;
        currentOperator = "";
        updateDisplay();
    }

    private void updateDisplay() {
        display.setText(currentInput);
    }

    @Override
    public void onBackPressed() {
       finish();
        super.onBackPressed();
    }
}