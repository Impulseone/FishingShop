package com.skynet.fishingshop.view.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.skynet.fishingshop.MainScreenActivity;
import com.skynet.fishingshop.R;

public class SmsCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_code);

        Button button = findViewById(R.id.apply_button);
        button.setOnClickListener((v) -> openMainScreenActivity());
    }

    private void openMainScreenActivity() {
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
    }
}