package com.skynet.fishingshop.view.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.skynet.fishingshop.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        findViewById(R.id.auth_button).setOnClickListener((v) -> openInputPhoneNumberActivity());
    }

    private void openInputPhoneNumberActivity() {
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }
}