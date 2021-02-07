package com.skynet.fishingshop.view.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.skynet.fishingshop.R;

public class AuthorizationInitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_init);

        Button authButton = findViewById(R.id.auth_button);
        authButton.setOnClickListener((v) -> openInputPhoneNumberActivity());
    }

    private void openInputPhoneNumberActivity() {
        Intent intent = new Intent(this, InputPhoneNumberActivity.class);
        startActivity(intent);
    }
}