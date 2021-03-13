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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setUniqueOffersButtons(view);
        return view;
    }


    private void setUniqueOffersButtons(View view) {
        List<Category> categories = CategoriesKeeper.getInstance().getCategories();
        List<Product> promotionProducts = new ArrayList<>();
        List<Product> salesProducts = new ArrayList<>();
        List<Product> newProducts = new ArrayList<>();
        for (Category category : categories) {
            for (Product product :
                    category.getProductList()) {
                switch (product.status) {
                    case "Акция":
                        promotionProducts.add(product);
                        break;
                    case "Распродажа":
                        salesProducts.add(product);
                        break;
                    case "Новый":
                        newProducts.add(product);
                        break;
                }
            }
        }
        view.findViewById(R.id.promotions).setOnClickListener(view1 -> createUniqueOfferFragment(promotionProducts,"Акции и предложения"));
        view.findViewById(R.id.new_products).setOnClickListener(view1 -> createUniqueOfferFragment(newProducts, "Новинки"));
        view.findViewById(R.id.sales_products).setOnClickListener(view1-> createUniqueOfferFragment(salesProducts, "Распродажи"));
    }

    private void createUniqueOfferFragment(List<Product> products, String categoryName) {
        FragmentTransaction ft = this.getParentFragmentManager().beginTransaction();
        ft.replace(R.id.main_relative_layout, new UniqueOfferFragment(products, categoryName));
        ft.commit();
    }
}