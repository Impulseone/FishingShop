package com.skynet.fishingshop.view.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.skynet.fishingshop.R;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        findViewById(R.id.get_code_button).setOnClickListener((v) -> openSmsCodeActivity());
    }

    private void openSmsCodeActivity() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }
}