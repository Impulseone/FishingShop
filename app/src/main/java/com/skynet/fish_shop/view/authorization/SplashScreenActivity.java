package com.skynet.fish_shop.view.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.extension.SubscriptionName;
import com.skynet.fish_shop.view.main.MainActivity;
import com.skynet.fish_shop.view.main.SubscriptionActivity;

import java.util.Arrays;
import java.util.Collections;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    private View getCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initGetCodeButton();
        checkAuthUserAndSubscription();
    }

    private void initGetCodeButton() {
        getCodeButton = findViewById(R.id.get_code_button);
        getCodeButton.setVisibility(View.GONE);
        getCodeButton.setOnClickListener((view) -> authenticateUser());
    }

    private void authenticateUser() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                new AuthUI.IdpConfig.PhoneBuilder().build()))
                        .build(),
                REQUEST_CODE);
    }

    private void checkAuthUserAndSubscription() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        findViewById(R.id.progress_indicator).setVisibility(View.VISIBLE);
        if (auth.getCurrentUser() != null) {
            findViewById(R.id.progress_indicator).setVisibility(View.VISIBLE);
            getCodeButton.setVisibility(View.GONE);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            getCodeButton.setVisibility(View.VISIBLE);
            findViewById(R.id.progress_indicator).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                if (response == null) {
                    System.out.println("RESPONSE IS NULL");
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    System.out.println("NO NETWORK");
                    return;
                }
                System.out.println("UNKNOWN ERROR");
                Log.e("-1", "Sign-in error: ", response.getError());
            }
        }
    }
}