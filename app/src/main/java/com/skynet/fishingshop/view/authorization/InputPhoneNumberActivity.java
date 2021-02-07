package com.skynet.fishingshop.view.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.skynet.fishingshop.R;

public class InputPhoneNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_number);

        Button button = findViewById(R.id.get_code_button);
        button.setOnClickListener((v) -> openSmsCodeActivity());
    }

    private void openSmsCodeActivity() {
        Intent intent = new Intent(this, SmsCodeActivity.class);
        startActivity(intent);
    }
}