package com.skynet.fish_shop.view.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.extension.SubscriptionName;

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
            if (!bp.isSubscribed(SubscriptionName.subName))
                bp.subscribe(this, SubscriptionName.subName);
            else startMainActivity();
        });
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Snackbar.make(subscriptionButton, productId + " purchased", BaseTransientBottomBar.LENGTH_LONG).show();
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
        if (bp.isSubscribed(SubscriptionName.subName)) startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}