package com.skynet.fish_shop.view.main.catalog;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skynet.fish_shop.R;
import com.skynet.fish_shop.extension.CategoriesKeeper;

public class SubCategoriesFragment extends Fragment {

    SubCategoriesAdapter subCategoriesAdapter;

    public SubCategoriesFragment(String subCategoryName) {
        subCategoriesAdapter = new SubCategoriesAdapter(this, subCategoryName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_categories, container, false);
        setBackButton(view);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.categories_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(subCategoriesAdapter);
        return view;
    }

    private void setBackButton(View view){
        view.findViewById(R.id.back_button).setOnClickListener(view1 -> checkoutToCategoriesFragment());
    }

    private void checkoutToCategoriesFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_relative_layout, new CategoriesFragment());
        ft.commit();
    }

    public void update(){
        subCategoriesAdapter.notifyDataSetChanged();
    }
}