package com.skynet.fish_shop.view.extension;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.github.chrisbanes.photoview.PhotoView;
import com.skynet.fish_shop.R;
import com.squareup.picasso.Picasso;

public class ImageDialogView extends DialogFragment {

    private final String imagePath;

    public ImageDialogView(String imagePath) {
        this.imagePath = imagePath;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.photo_view_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Picasso.get().load(imagePath).into((PhotoView)view.findViewById(R.id.photo_view));
        builder.setView(view);
        builder.setCancelable(true);

        return builder.create();
    }

}
