package com.skynet.fish_shop.view.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.skynet.fish_shop.R;

public class SubscriptionActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private BillingProcessor bp;
    private View subscriptionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        bp = new BillingProcessor(this, null, this);
        bp.initialize();

        subscriptionButton = findViewById(R.id.subscription_button);
        subscriptionButton.setOnClickListener((view) -> {
            if (!bp.isSubscribed("sub_1"))
                bp.subscribe(this, "sub_1");
            else startMainActivity();
        });
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        startMainActivity();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}