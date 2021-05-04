package com.skynet.fish_shop.view.main.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.skynet.fish_shop.R;
import com.skynet.fish_shop.extension.CategoriesKeeper;
import com.skynet.fish_shop.model.Category;
import com.skynet.fish_shop.model.Product;
import com.skynet.fish_shop.view.main.productsList.SearchedProductsListFragment;
import com.skynet.fish_shop.view.main.unique_offers.UniqueOfferFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private View view;
    private UniqueOfferFragment uniqueOfferFragment;
    private SearchedProductsListFragment searchedProductsListFragment;
    private EditText searchField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setSearchField();
        setUniqueOffersButtons();
        return view;
    }

    public void update() {
        setUniqueOffersButtons();
        updateUniqueOfferFragment();
    }

    private void setSearchField(){
        searchField = view.findViewById(R.id.search_product);
        searchField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                createSearchProductFragment(searchField.getText().toString());
            }
            return true;
        }
        );
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

    private void createSearchProductFragment(String search){
        searchedProductsListFragment = new SearchedProductsListFragment(search);
        FragmentTransaction ft = this.getParentFragmentManager().beginTransaction();
        ft.replace(R.id.main_relative_layout, searchedProductsListFragment);
        ft.commit();
        hideKeyboard(getActivity());
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void updateUniqueOfferFragment() {
        if (uniqueOfferFragment != null) uniqueOfferFragment.update();
    }
}