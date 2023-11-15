package com.example.oya;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class walletFragment extends Fragment {
    RelativeLayout sendmoney;
    RelativeLayout buyairtime;
    RelativeLayout paybills;
    RelativeLayout history;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.walletfragment, container, false);
        sendmoney = view.findViewById(R.id.sendmoney);
        buyairtime = view.findViewById(R.id.buyairtime);
        paybills = view.findViewById(R.id.paybills);
        history = view.findViewById(R.id.history);

        sendmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent0 = new Intent(getActivity(),sendMoney.class);
                startActivity(intent0);

            }
        });
        buyairtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(),buyAirtime.class);
                startActivity(intent1);


            }
        });
        paybills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(),payBills.class);
                startActivity(intent2);


            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getActivity(),History.class);
                startActivity(intent3);


            }
        });






        return view;
    }
}
