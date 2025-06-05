package com.google.firebase.quickstart.fcm.java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.quickstart.fcm.R;

public class IncomingCallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);
        registerViewsEvent();
    }

    private void registerViewsEvent() {
        AppCompatButton btnRejectCallActivity = findViewById(R.id.btnRejectCall);
        btnRejectCallActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callingService = new Intent(IncomingCallActivity.this, CallingService.class);
                stopService(callingService);
            }
        });

        AppCompatButton btnAcceptCall = findViewById(R.id.btnAcceptCall);
        btnAcceptCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: start calling screen
            }
        });
    }
}
