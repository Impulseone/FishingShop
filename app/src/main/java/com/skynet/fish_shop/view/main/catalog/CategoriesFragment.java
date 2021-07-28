package com.skynet.fish_shop.view.main.catalog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fish_shop.R;
import com.skynet.fish_shop.extension.CategoriesKeeper;

public class CategoriesFragment extends Fragment {

    private CategoriesAdapter categoriesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (categoriesAdapter == null) categoriesAdapter = new CategoriesAdapter(this);
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        Context context = view.getContext();
        if (CategoriesKeeper.getInstance().getCategories().size() != 0) {
            view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.categories_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(categoriesAdapter);
        } else {
            view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        }
        return view;
    }

    public void update() {
        View view = getView();
        if (view != null)
            if (CategoriesKeeper.getInstance().getCategories().size() != 0) {
                view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.categories_rv);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                if (categoriesAdapter == null) categoriesAdapter = new CategoriesAdapter(this);
                recyclerView.setAdapter(categoriesAdapter);
                categoriesAdapter.update();
            } else {
                view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
            }
    }
}