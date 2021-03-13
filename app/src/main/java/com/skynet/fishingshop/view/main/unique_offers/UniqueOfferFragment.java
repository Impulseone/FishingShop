package com.skynet.fishingshop.view.main.unique_offers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.main.catalog.CatalogFragment;
import com.skynet.fishingshop.view.main.home.HomeFragment;
import com.skynet.fishingshop.view.main.productsList.ProductsListForCategoryAdapter;

import java.util.List;

public class UniqueOfferFragment extends Fragment {

    final List<Product> products;
    final String categoryName;

    public UniqueOfferFragment(List<Product> products, String categoryName) {
        this.products = products;
        this.categoryName = categoryName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unique_offer, container, false);
        setBackButton(view);
        setCategoryName(view);
        setAdapter(view);
        return view;
    }

    private void setBackButton(View view) {
        view.findViewById(R.id.back_button).setOnClickListener(view1 -> back());
    }

    private void setCategoryName(View view) {
        ((TextView) view.findViewById(R.id.category_name)).setText(categoryName);
    }

    private void setAdapter(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.products_rv);
        ProductsAdapter adapter = new ProductsAdapter(this, products, categoryName);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void back() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        HomeFragment catalogFragment = new HomeFragment();
        ft.replace(R.id.main_relative_layout, catalogFragment);
        ft.commit();
    }
}