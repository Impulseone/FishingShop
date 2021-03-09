package com.skynet.fishingshop.view.authorization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.MainActivity;

import java.util.concurrent.TimeUnit;

public class AuthorizationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String phoneNumber;
    private EditText phoneInputEditText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        phoneInputEditText = findViewById(R.id.phone_input);
        initCallback();

        firebaseAuth = FirebaseAuth.getInstance();
        checkAuthUser();

        findViewById(R.id.get_code_button).setOnClickListener((v) -> authUser());
    }

    private void authUser() {
        phoneNumber = phoneInputEditText.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, callback);
    }

    private void checkAuthUser() {
        if (firebaseAuth.getCurrentUser() != null) {
            startMainActivity();
        }
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void initCallback(){
        callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       Toast toast = Toast.makeText(getApplicationContext(),
                               "Добро пожаловать", Toast.LENGTH_SHORT);
                       toast.show();
                       startMainActivity();
                   }
                   else {
                       Toast toast = Toast.makeText(getApplicationContext(),
                               task.getException().getMessage(), Toast.LENGTH_SHORT);
                       toast.show();
                   }
                });
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        e.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                openSmsCodeActivity(id);
            }
        };
    }

    private void openSmsCodeActivity(String id) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}