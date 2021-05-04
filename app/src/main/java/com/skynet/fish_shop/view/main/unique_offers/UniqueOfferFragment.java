package com.skynet.fish_shop.view.main.unique_offers;

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
import com.skynet.fish_shop.extension.CategoriesKeeper;
import com.skynet.fish_shop.model.Category;
import com.skynet.fish_shop.model.Product;
import com.skynet.fish_shop.view.main.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class UniqueOfferFragment extends Fragment {

    private List<Product> products;
    private final String categoryName;
    private View view;

    public UniqueOfferFragment(List<Product> products, String categoryName) {
        this.products = products;
        this.categoryName = categoryName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_unique_offer, container, false);
        setBackButton();
        setCategoryName();
        setAdapter();
        return view;
    }

    private void setBackButton() {
        view.findViewById(R.id.back_button).setOnClickListener(view1 -> back());
    }

    private void setCategoryName() {
        ((TextView) view.findViewById(R.id.category_name)).setText(categoryName);
    }

    private void setAdapter() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.products_rv);
        ProductsAdapter adapter = new ProductsAdapter(this, products, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void back() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        HomeFragment catalogFragment = new HomeFragment();
        ft.replace(R.id.main_relative_layout, catalogFragment);
        ft.commit();
    }

    public void update() {
        List<Category> categories = CategoriesKeeper.getInstance().getCategories();
        products = new ArrayList<>();
        for (Category category : categories) {
            for (Product product :
                    category.getProductList()) {
                if (product.status.equals(categoryName)) products.add(product);
            }
        }
        setAdapter();
    }
}