package com.skynet.fish_shop.view.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.skynet.fish_shop.R;
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

        checkAuthUserAndSubscription();

        findViewById(R.id.auth_button).setOnClickListener((v) -> openInputPhoneNumberActivity());
    }

    private void openInputPhoneNumberActivity() {
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }

    private void checkAuthUserAndSubscription() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null && bp.isSubscribed("sub_1")) {
            startMainActivity();
        } else if (!bp.isSubscribed("sub_1")) {
            startSubscriptionActivity();
        } else startAuthActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void startSubscriptionActivity() {
        Intent intent = new Intent(this, SubscriptionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void startAuthActivity() {
        Intent intent = new Intent(this, AuthorizationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

    }
}