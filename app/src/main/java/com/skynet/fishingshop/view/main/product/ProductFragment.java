package com.skynet.fishingshop.view.main.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.productsList.ProductsListFragment;

public class ProductFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product, container, false);
        View v = view.findViewById(R.id.back_button);
        v.setOnClickListener(view1 -> back());
        this.getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
        return view;
    }

    private void back() {
        this.getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ProductsListFragment productsListFragment = new ProductsListFragment();
        ft.replace(R.id.main_relative_layout, productsListFragment);
        ft.commit();
    }
}