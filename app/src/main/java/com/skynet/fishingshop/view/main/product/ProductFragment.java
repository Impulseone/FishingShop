package com.skynet.fishingshop.view.main.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.model.Category;
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.main.productsList.ProductsListFragment;

import java.util.List;

public class ProductFragment extends Fragment {

   private final Category category;
   private final Product product;

    public ProductFragment(Category category, Product product) {
        this.category = category;
        this.product = product;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product, container, false);
        setBackButton(view);
        setBottomNavigationVisibility();
        setTitle(view);
        setProductDescription(view);
        setPrice(view);
        return view;
    }

    private void setTitle(View view){
        ((TextView)view.findViewById(R.id.product_name)).setText(product.name.substring(2));
    }
    private void setProductDescription(View view){
        ((TextView)view.findViewById(R.id.product_description)).setText(product.description);
    }

    private void setBackButton(View view){
        View backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(view1 -> back());
    }

    private void setBottomNavigationVisibility(){
        this.getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
    }

    private void setPrice(View view){
        ((TextView)view.findViewById(R.id.price)).setText(product.price+" руб.");
    }

    private void back() {
        this.getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ProductsListFragment productsListFragment = new ProductsListFragment(category);
        ft.replace(R.id.main_relative_layout, productsListFragment);
        ft.commit();
    }
}