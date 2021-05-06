package com.skynet.fish_shop.view.authorization;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.view.extension.WarningDialogView;
import com.skynet.fish_shop.view.main.MainActivity;
import com.skynet.fish_shop.view.main.SubscriptionActivity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PhoneConfirmationActivity extends AppCompatActivity implements TextWatcher, BillingProcessor.IBillingHandler {

    private final ArrayList<EditText> editTextArray = new ArrayList<>(4);
    private String numTemp;
    private FirebaseAuth firebaseAuth;
    private String id;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callback;
    private PhoneAuthProvider.ForceResendingToken token;
    private String phoneNumber;

    private BillingProcessor bp;

    private int timerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirmation);

        bp = new BillingProcessor(this, null, this);
        bp.initialize();

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
        findViewById(R.id.back_button).setOnClickListener(view -> PhoneConfirmationActivity.this.finish());
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
        String code = getInputCodeFromTextFields();
        if (code == null || code.equals("") || code.length() < 6) {
            new WarningDialogView("Ошибка", "Введите 6-значный код").show(getSupportFragmentManager(), "");
        } else {
           if(bp.isInitialized()) {
               new SubmitCodeTask(this).execute();
           }
           else submitCode();
        }
    }

    private void setResendCodeButton() {
        findViewById(R.id.resend_code_button).setOnClickListener(view -> resendCode());
        setTimer();
    }

    private void setTimer() {
        new CountDownTimer(60_000, 1000) {
            @Override
            public void onTick(long millis) {
                timerCount = (int) millis / 1000;
                ((TextView) findViewById(R.id.time_counter)).setText((int) millis / 1000 + "с");
            }

            @Override
            public void onFinish() {
                timerCount = 0;
            }
        }.start();
    }

    private void resendCode() {
        if (timerCount != 0) {
            new WarningDialogView("Ошибка", "До повторной отправки осталось " + timerCount + "с").show(getSupportFragmentManager(), "");
        } else {
            resendVerificationCode(token);
        }
    }

    private void resendVerificationCode(PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                callback,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
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

    private void startSubscriptionActivity() {
        Intent intent = new Intent(this, SubscriptionActivity.class);
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

    private class SubmitCodeTask extends AsyncTask<Void,Void,Void> {

        private final Activity activity;

        private SubmitCodeTask(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            activity.findViewById(R.id.apply_button).setVisibility(View.GONE);
            activity.findViewById(R.id.apply_button_progress).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(id, getInputCodeFromTextFields());
            firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (bp.isSubscribed("sub_1")) startMainActivity();
                    else startSubscriptionActivity();
                } else {
                    Snackbar.make(activity.findViewById(R.id.apply_button), Objects.requireNonNull(task.getException()).getMessage(),BaseTransientBottomBar.LENGTH_LONG).show();
                    System.out.println(Objects.requireNonNull(task.getException()).getMessage());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            activity.findViewById(R.id.apply_button).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.apply_button_progress).setVisibility(View.GONE);
        }
    }
}