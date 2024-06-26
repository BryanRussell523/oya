package com.example.oya.Utility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;

import com.example.oya.R;
import com.google.android.material.snackbar.Snackbar;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Common.isConnectedToInternet(context)){ //If internet is not connected
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialog,null);
            builder.setView(layout_dialog);
            //Declaring the button
            androidx.appcompat.widget.AppCompatButton retry = layout_dialog.findViewById(R.id.retry);
            //Show dialog
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(true);
            dialog.getWindow().setGravity(Gravity.BOTTOM);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onReceive(context, intent);
                }
            });
        }


    }
}
