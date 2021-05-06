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
import com.skynet.fish_shop.view.main.MainActivity;
import com.skynet.fish_shop.view.main.SubscriptionActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new CheckAuthAndSubscription(this).execute();
    }

    private static class CheckAuthAndSubscription extends AsyncTask<Void, Void, Void> implements BillingProcessor.IBillingHandler {

        private final Activity activity;
        private BillingProcessor bp;

        private CheckAuthAndSubscription(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bp = new BillingProcessor(activity, null, this);
            bp.initialize();
            (new Handler(Looper.getMainLooper())).postDelayed(this::checkAuthUserAndSubscription, 3000);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }

        private void checkAuthUserAndSubscription() {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                if (bp.isSubscribed("sub_1")) {
                    startMainActivity();
                } else startSubscriptionActivity();
            } else {
                startAuthActivity();
            }
        }

        private void startMainActivity() {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
        }

        private void startSubscriptionActivity() {
            Intent intent = new Intent(activity, SubscriptionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
        }

        private void startAuthActivity() {
            Intent intent = new Intent(activity, AuthorizationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
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
}