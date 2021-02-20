package com.skynet.fishingshop.view.main.productsList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.catalog.CatalogFragment;

import java.util.ArrayList;
import java.util.List;

public class ProductsListFragment extends Fragment {

    private List<Pair<String,String>> products;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products_list, container, false);
        createProducts();

        view.findViewById(R.id.back_button).setOnClickListener(view1 -> back());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.products_rv);
        ProductsListAdapter adapter = new ProductsListAdapter(products, view);
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



    private void createProducts() {
        products = new ArrayList<>();
        products.add(new Pair<>("Катушка New Brand 1", "Катушка New Brand 2"));
        products.add(new Pair<>("Катушка New Brand 3", "Катушка New Brand 4"));
        products.add(new Pair<>("Катушка Old Brand 1", "Катушка Old Brand 2"));
        products.add(new Pair<>("Катушка Old Brand 3", "Катушка Old Brand 4"));
    }
}