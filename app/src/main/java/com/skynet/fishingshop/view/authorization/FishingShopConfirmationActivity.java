package com.skynet.fishingshop.view.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.skynet.fishingshop.view.main.MainScreenActivity;
import com.skynet.fishingshop.R;

import java.util.ArrayList;

public class FishingShopConfirmationActivity extends AppCompatActivity implements TextWatcher {

    private int current;
    private ArrayList<EditText> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishing_shop_confirmation);

        findViewById(R.id.get_code_button).setOnClickListener((v) -> openMainScreenActivity());
        findViewById(R.id.back_button).setOnClickListener(view -> FishingShopConfirmationActivity.this.finish());

        current = 0;
        array = new ArrayList<>(4);

        EditText edit1 = (EditText) findViewById(R.id.et1);
        EditText edit2 = (EditText) findViewById(R.id.et2);
        EditText edit3 = (EditText) findViewById(R.id.et3);
        EditText edit4 = (EditText) findViewById(R.id.et4);

        edit1.addTextChangedListener(this);
        edit2.addTextChangedListener(this);
        edit3.addTextChangedListener(this);
        edit4.addTextChangedListener(this);

        array.add(0, edit1);
        array.add(1, edit2);
        array.add(2, edit3);
        array.add(3, edit4);
    }

    private void openMainScreenActivity() {
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        //TODO: check request code
        if (editable.length() == 1) {
            current++;
            if (current < 4) {
                array.get(current).requestFocus();
            } else {
                Intent intent = new Intent(this, MainScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        } else {
            if (current != 0) {
                current--;
                if (current != 0) array.get(current - 1).requestFocus();
                else array.get(current).requestFocus();
            }
        }
    }
}