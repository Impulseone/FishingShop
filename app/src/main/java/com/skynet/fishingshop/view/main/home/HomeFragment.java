package com.skynet.fishingshop.view.main.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.extension.CategoriesKeeper;
import com.skynet.fishingshop.model.Category;
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.main.unique_offers.UniqueOfferFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private View view;
    private UniqueOfferFragment uniqueOfferFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setUniqueOffersButtons();
        return view;
    }

    public void update() {
        setUniqueOffersButtons();
        updateUniqueOfferFragment();
    }

    private void setUniqueOffersButtons() {
        List<Category> categories = CategoriesKeeper.getInstance().getCategories();
        List<Product> promotionProducts = new ArrayList<>();
        List<Product> salesProducts = new ArrayList<>();
        List<Product> newProducts = new ArrayList<>();
        for (Category category : categories) {
            for (Product product :
                    category.getProductList()) {
                switch (product.status) {
                    case "Акции и предложения":
                        promotionProducts.add(product);
                        break;
                    case "Распродажи":
                        salesProducts.add(product);
                        break;
                    case "Новинки":
                        newProducts.add(product);
                        break;
                }
            }
        }
        view.findViewById(R.id.promotions).setOnClickListener(view1 -> createUniqueOfferFragment(promotionProducts, "Акции и предложения"));
        view.findViewById(R.id.new_products).setOnClickListener(view1 -> createUniqueOfferFragment(newProducts, "Новинки"));
        view.findViewById(R.id.sales_products).setOnClickListener(view1 -> createUniqueOfferFragment(salesProducts, "Распродажи"));
    }

    private void createUniqueOfferFragment(List<Product> products, String categoryName) {
        uniqueOfferFragment = new UniqueOfferFragment(products, categoryName);
        FragmentTransaction ft = this.getParentFragmentManager().beginTransaction();
        ft.replace(R.id.main_relative_layout, uniqueOfferFragment);
        ft.commit();
    }

    private void updateUniqueOfferFragment() {
        if (uniqueOfferFragment != null) uniqueOfferFragment.update();
    }
}