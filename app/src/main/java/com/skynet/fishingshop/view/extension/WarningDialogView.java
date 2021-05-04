package com.skynet.fishingshop.view.extension;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.skynet.fishingshop.R;

public class WarningDialogView extends DialogFragment {

    private final String title;
    private final String message;

    public WarningDialogView(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.text)).setText(message);
        ((TextView)view.findViewById(R.id.approve_button)).setText("ОК");
        view.findViewById(R.id.approve_button).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.cancel_button).setVisibility(View.GONE);
        builder.setView(view);
        builder.setCancelable(true);

        return builder.create();
    }
}
