package com.skynet.fishingshop.view.main.cart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.orderConfirmation.OrderConfirmationFragment;

public class CartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.products_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new CartProductsAdapter(this));

        view.findViewById(R.id.confirm_button).setOnClickListener(view1 -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            OrderConfirmationFragment orderConfirmationFragment = new OrderConfirmationFragment();
            ft.replace(R.id.main_relative_layout, orderConfirmationFragment);
            ft.commit();
        });
        return view;
    }
}