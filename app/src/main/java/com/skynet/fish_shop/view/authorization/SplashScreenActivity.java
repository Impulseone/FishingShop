package com.skynet.fish_shop.view.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.extension.SubscriptionName;
import com.skynet.fish_shop.view.main.MainActivity;
import com.skynet.fish_shop.view.main.SubscriptionActivity;

public class SplashScreenActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        bp = new BillingProcessor(this, null, this);
        bp.initialize();
    }

    private void checkAuthUserAndSubscription() {
         if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (bp.isSubscribed(SubscriptionName.subName)) {
                startMainActivity();
            } else startSubscriptionActivity();
        } else {
            startAuthActivity();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    private void startSubscriptionActivity() {
        Intent intent = new Intent(this, SubscriptionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    private void startAuthActivity() {
        Intent intent = new Intent(this, AuthorizationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    @Override
    public void onBillingInitialized() {
        checkAuthUserAndSubscription();
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