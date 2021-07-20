package com.skynet.fish_shop.view.main.productsList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skynet.fish_shop.R;
import com.skynet.fish_shop.model.Category;
import com.skynet.fish_shop.view.main.catalog.CategoriesFragment;

public class ProductsListForCategoryFragment extends Fragment {

    private final Category category;

    public ProductsListForCategoryFragment(Category category) {
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products_list, container, false);
        setBackButton(view);
        setCategoryName(view);
        setAdapter(view);
        return view;
    }

    private void setBackButton(View view) {
        view.findViewById(R.id.back_button).setOnClickListener(view1 -> back());
    }

    private void setCategoryName(View view) {
        String[] categoryNameSplit = category.getName().split(" ");
        StringBuilder resultCategoryName = new StringBuilder();
        for (int i = 1; i < categoryNameSplit.length; i++) {
            resultCategoryName.append(categoryNameSplit[i]).append(" ");
        }
        ((TextView) view.findViewById(R.id.category_name)).setText(resultCategoryName.toString());
    }

    private void setAdapter(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.products_rv);
        ProductsListForCategoryAdapter adapter = new ProductsListForCategoryAdapter(category, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void back() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        CategoriesFragment categoriesFragment = new CategoriesFragment();
        ft.replace(R.id.main_relative_layout, categoriesFragment);
        ft.commit();
    }
}