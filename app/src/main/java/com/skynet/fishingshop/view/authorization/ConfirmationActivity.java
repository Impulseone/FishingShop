package com.skynet.fishingshop.view.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfirmationActivity extends AppCompatActivity implements TextWatcher {

    private final ArrayList<EditText> editTextArray = new ArrayList<>(4);
    private String numTemp;
    private DatabaseReference mDatabaseReference;
    private final String TAG = "Firebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirmation);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("Категории");

        findViewById(R.id.apply_button).setOnClickListener((v) -> {
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    System.out.println(value);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
            openMainScreenActivity();
        });
        findViewById(R.id.back_button).setOnClickListener(view -> ConfirmationActivity.this.finish());

        LinearLayout layout = findViewById(R.id.codeLayout);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if (view instanceof EditText) {
                editTextArray.add(i, (EditText) view);
                editTextArray.get(i).addTextChangedListener(this);
                int finalI = i;
                editTextArray.get(i).setOnKeyListener((view1, i1, keyEvent) -> {
                    if (i1 == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        if (finalI != 0) { //Don't implement for first digit
                            editTextArray.get(finalI - 1).requestFocus();
                            editTextArray.get(finalI - 1).setSelection(editTextArray.get(finalI - 1).length());
                        }
                    }
                    return false;
                });
            }
        }
        editTextArray.get(0).requestFocus();
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

    private void openMainScreenActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}