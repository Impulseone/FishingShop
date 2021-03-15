package com.skynet.fishingshop.view.authorization;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.extension.CategoriesKeeper;
import com.skynet.fishingshop.model.Category;
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.main.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ConfirmationActivity extends AppCompatActivity implements TextWatcher {

    private final ArrayList<EditText> editTextArray = new ArrayList<>(4);
    private String numTemp;
    private FirebaseAuth firebaseAuth;
    private String id;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callback;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirmation);

        id = getIntent().getStringExtra("id");
        phoneNumber = getIntent().getStringExtra("phone_number");

        initFirebase();
        initApplyButton();
        initBackButton();
        initPinCodeField();
        initCallback();
        setResendCodeButton();

        editTextArray.get(0).requestFocus();
    }

    private void initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initApplyButton() {
        findViewById(R.id.apply_button).setOnClickListener((v) -> {
            submitCode();
        });
    }

    private void initPinCodeField() {
        LinearLayout layout = findViewById(R.id.pin_code_layout);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if (view instanceof EditText) {
                editTextArray.add(i, (EditText) view);
                editTextArray.get(i).addTextChangedListener(this);
                int finalI = i;
                editTextArray.get(i).setOnKeyListener((view1, i1, keyEvent) -> {
                    if (i1 == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        if (finalI != 0) {
                            editTextArray.get(finalI - 1).requestFocus();
                            editTextArray.get(finalI - 1).setSelection(editTextArray.get(finalI - 1).length());
                        }
                    }
                    return false;
                });
            }
        }
    }

    private void initBackButton() {
        findViewById(R.id.back_button).setOnClickListener(view -> ConfirmationActivity.this.finish());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        numTemp = charSequence.toString();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        for (int i = 0; i < editTextArray.size(); i++) {
            if (editable == editTextArray.get(i).getEditableText()) {
                if (editable.toString().equals("")) {
                    return;
                }
                if (editable.length() >= 2) {//if more than 1 char
                    String newTemp = editable.toString().substring(editable.length() - 1, editable.length());//get 2nd digit
                    if (!newTemp.equals(numTemp)) {
                        editTextArray.get(i).setText(newTemp);
                    } else {
                        editTextArray.get(i).setText(editable.toString().substring(0, editable.length() - 1));
                    }
                } else if (i != editTextArray.size() - 1) {
                    editTextArray.get(i + 1).requestFocus();
                    editTextArray.get(i + 1).setSelection(editTextArray.get(i + 1).length());
                    return;
                }
            }
        }
    }

    private String getInputCodeFromTextFields() {
        StringBuilder code = new StringBuilder();
        for (EditText editText : editTextArray) {
            code.append(editText.getText().toString());
        }
        return code.toString();
    }

    private void submitCode() {
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(id, getInputCodeFromTextFields());
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                openMainScreenActivity();
            } else {
                System.out.println(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    private void openMainScreenActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setResendCodeButton() {
        findViewById(R.id.resend_code_button).setOnClickListener(view -> resendCode());
        setTimer();
    }

    private void setTimer() {
        new CountDownTimer(60_000, 1000) {
            @Override
            public void onTick(long millis) {
                ((TextView) findViewById(R.id.time_counter)).setText((int) millis / 1000 + "с");
            }

            @Override
            public void onFinish() {}
        }.start();
    }

    private void resendCode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, callback);
    }

    private void initCallback() {
        callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startMainActivity();
                    } else {
                        System.out.println(task.getException().getMessage());
                        Toast toast = Toast.makeText(getApplicationContext(),
                                task.getException().getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                System.out.println(e.getMessage());
                Toast toast = Toast.makeText(getApplicationContext(),
                        e.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Код отправлен повторно", Toast.LENGTH_SHORT);
                toast.show();
                setTimer();
            }
        };
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}