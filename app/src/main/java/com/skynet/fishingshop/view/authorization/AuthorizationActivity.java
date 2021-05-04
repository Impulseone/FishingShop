package com.skynet.fishingshop.view.authorization;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.extension.WarningDialogView;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class AuthorizationActivity extends AppCompatActivity {

    private String phoneNumber;
    private EditText phoneInputEditText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callback;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        phoneInputEditText = findViewById(R.id.phone_input);
        phoneInputEditText.setText("+7");
        setProgressBar();
        initCallback();

        findViewById(R.id.get_code_button).setOnClickListener((v) -> authUser());
    }

    private void setProgressBar() {
        progressBar = findViewById(R.id.progress_indicator);
        progressBar.getIndeterminateDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void authUser() {
        phoneNumber = phoneInputEditText.getText().toString();
        if (phoneNumber == null || phoneNumber.equals("") || !phoneNumber.contains("+7") || phoneNumber.length() != 12) {
            new WarningDialogView("Ошибка", "Введите действительный номер телефона").show(getSupportFragmentManager(), "");
        } else {
            findViewById(R.id.get_code_button_loading).setVisibility(View.VISIBLE);
            findViewById(R.id.get_code_button).setVisibility(View.GONE);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, callback);
        }
    }

    private void initCallback() {
        callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                findViewById(R.id.get_code_button_loading).setVisibility(View.GONE);
                findViewById(R.id.get_code_button).setVisibility(View.VISIBLE);
                openSmsCodeActivity(id);
            }
        };
    }

    private void openSmsCodeActivity(String id) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("phone_number", phoneNumber);
        startActivity(intent);
    }
}