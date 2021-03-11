package com.skynet.fishingshop.view.main.productsList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.model.Category;
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.main.catalog.CatalogFragment;

import java.util.ArrayList;
import java.util.List;

public class ProductsListFragment extends Fragment {

    private final Category category;

    public ProductsListFragment(Category category) {
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products_list, container, false);

        view.findViewById(R.id.back_button).setOnClickListener(view1 -> back());
        ((TextView) view.findViewById(R.id.category_name)).setText(category.getCategoryName());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.products_rv);
        ProductsListAdapter adapter = new ProductsListAdapter(category, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void back() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        CatalogFragment catalogFragment = new CatalogFragment();
        ft.replace(R.id.main_relative_layout, catalogFragment);
        ft.commit();
    }
}