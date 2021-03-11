package com.skynet.fishingshop.view.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        checkAuthUser();

        findViewById(R.id.auth_button).setOnClickListener((v) -> openInputPhoneNumberActivity());
    }

    private void openInputPhoneNumberActivity() {
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }

    private void checkAuthUser() {
        if ( FirebaseAuth.getInstance().getCurrentUser() != null) {
            startMainActivity();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}