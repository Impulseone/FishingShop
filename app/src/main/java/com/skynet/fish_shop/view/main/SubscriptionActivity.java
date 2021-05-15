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

import org.jetbrains.annotations.NotNull;

public class SubscriptionActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private BillingProcessor bp;
    private View subscriptionButton;
    private boolean isSubscribed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        bp = new BillingProcessor(this, null, this);
        bp.initialize();

        subscriptionButton = findViewById(R.id.subscription_button);
        subscriptionButton.setOnClickListener((view) -> {
            if (!isSubscribed) {
                bp.subscribe(this, SubscriptionName.subName);
            }
            else startMainActivity();
        });
    }

    private void checkIfUserIsSubscribed() {
        boolean purchaseResult = bp.loadOwnedPurchasesFromGoogle();
        if (purchaseResult) {
            TransactionDetails subscriptionTransactionDetails = bp.getSubscriptionTransactionDetails(SubscriptionName.subName);
            if (subscriptionTransactionDetails != null) {
                isSubscribed = true;
                startMainActivity();
            } else {
                isSubscribed = false;
                findViewById(R.id.sub_message).setVisibility(View.VISIBLE);
                findViewById(R.id.progress_indicator).setVisibility(View.GONE);
                subscriptionButton.setVisibility(View.VISIBLE);
            }
        } else {
            isSubscribed = false;
        }
    }

    @Override
    public void onProductPurchased(@NotNull String productId, TransactionDetails details) {
        isSubscribed = true;
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
        checkIfUserIsSubscribed();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}