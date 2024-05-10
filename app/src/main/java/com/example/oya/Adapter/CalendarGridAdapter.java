package com.example.oya.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.oya.Object.CalendarObject;
import com.example.oya.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalendarGridAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<CalendarObject> dates;
    private final SimpleDateFormat dateFormatter;
    private OnDateClickListener onDateClickListener;

    public interface OnDateClickListener{
        void onDateClick(int dateNumber,String dayOfWeek);
    }
    public void setOnDateClickListener(OnDateClickListener listener){
        this.onDateClickListener = listener;
    }
    public CalendarGridAdapter(Context context, ArrayList<CalendarObject> dates){

        this.context = context;
        this.dates = dates;
        dateFormatter = new SimpleDateFormat("dd", Locale.getDefault());

    }
    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.calendar_day_layout, parent, false);
        }
        TextView dayNumber = view.findViewById(R.id.dayNumber);
        TextView dayOfWeek = view.findViewById(R.id.dayOfWeek);
        CalendarObject date = dates.get(position);


        // Set day of the week only for the first row
        if (position < 7) {
            dayOfWeek.setVisibility(View.VISIBLE);
            dayOfWeek.setText(getDayOfWeekString(position + 1)); // Adjust position by adding 1 to start from Sunday
        } else {
            dayOfWeek.setVisibility(View.GONE);
        }
        // Set day number
        if (date != null) {
            dayNumber.setText(String.valueOf(date.getDay()));
            //Set margin space for day number
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) dayNumber.getLayoutParams();
            int topMargin = 16;
            int bottomMargin =16;
            layoutParams.topMargin = topMargin;
            layoutParams.bottomMargin = bottomMargin;
            dayNumber.setLayoutParams(layoutParams);


            // Check if the date belongs to the current month and Highlight current day
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.setTime(date.getDate());
            int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
            int selectedMonth = selectedCalendar.get(Calendar.MONTH);

            if (currentMonth == selectedMonth) {
                //Highlight the current day if it belongs to the current month

                int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                int selectedDay = selectedCalendar.get(Calendar.DAY_OF_MONTH);
                if (selectedDay == currentDay) {
                    //Highlight the current day with blue color
                    dayNumber.setTextColor(context.getResources().getColor(R.color.white));
                    dayNumber.setBackgroundResource(R.drawable.button_normal);
                } else {
                    //Rest color for the other days
                    dayNumber.setTextColor(context.getResources().getColor(R.color.black));
                }
            } else {
                //Reset color for other months days
                dayNumber.setTextColor(context.getResources().getColor(R.color.black));
            }
            //Set OnclickListener to handle click events on the dates
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Pass the clicked date number and day of the week to the activity
                    if (onDateClickListener != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date.getDate());
                        int dateNumber = calendar.get(Calendar.DAY_OF_MONTH);
                        String dayOfWeek = getDayOfWeekString(calendar.get(Calendar.DAY_OF_WEEK));

                        onDateClickListener.onDateClick(dateNumber, dayOfWeek);
                    }
                }
            });
        } else {
            dayNumber.setText("");
        }
        return view;
    }
        private String getDayOfWeekString(int dayOfWeek){
            if (dayOfWeek == Calendar.SUNDAY) {
                return "S";
            } else if (dayOfWeek == Calendar.MONDAY) {
                return "M";
            } else if (dayOfWeek == Calendar.TUESDAY) {
                return "T";
            } else if (dayOfWeek == Calendar.WEDNESDAY) {
                return "W";
            } else if (dayOfWeek == Calendar.THURSDAY) {
                return "T";
            } else if (dayOfWeek == Calendar.FRIDAY) {
                return "F";
            } else if (dayOfWeek == Calendar.SATURDAY) {
                return "S";
            }else{
                return "";
            }
        }
}