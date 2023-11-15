package com.example.oya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oya.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;


public class SignUp extends AppCompatActivity {
    EditText username,email,password;
    Button registerbutton;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    TextView login;
    FirebaseUser firebaseUser;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //check if user is null
        if (firebaseUser != null){
            Intent intent = new Intent(SignUp.this, chatViewActivity.class);
            startActivity(intent);
            finish();
        }
        //Monitor network change
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
       registerbutton = findViewById(R.id.registerbutton);
        login = findViewById(R.id.login);
       firebaseAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SignUp.this,LoginActivity.class);
                startActivity(intent2);
                finish();
            }
        });

       registerbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String txt_username = username.getText().toString();
               String txt_email = email.getText().toString();
               String txt_password = password.getText().toString();
               if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                   Toast.makeText(SignUp.this, "Fill all required information!", Toast.LENGTH_SHORT).show();
               } else if (txt_password.length()<6) {
                   Toast.makeText(SignUp.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
               }else{
                   register(txt_username,txt_email,txt_password);
               }
           }
       });
//        //CHECK NETWORK STATE AND DISPLAY MESSAGE
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
//            //DISPLAY CONNECTED INFO ON SNACKBAR
//            Snackbar.make(findViewById(R.id.layout),"Connected to the server",Snackbar.LENGTH_SHORT).show();
//        }else{
//            Snackbar.make(findViewById(R.id.layout),"No internet connection",Snackbar.LENGTH_SHORT).show();
//        }
    }
    private void register(String username, String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL","default");
                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent1 = new Intent(SignUp.this, chatViewActivity.class);
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent1);
                                        finish();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(SignUp.this,"Email or Password not valid",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    //Monitor network change
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}