package com.skynet.fishingshop.view.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.skynet.fishingshop.view.main.MainScreenActivity;
import com.skynet.fishingshop.R;

public class FishingShopConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishing_shop_confirmation);

        findViewById(R.id.get_code_button).setOnClickListener((v) -> openMainScreenActivity());
    }

    private void openMainScreenActivity() {
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
    }
}