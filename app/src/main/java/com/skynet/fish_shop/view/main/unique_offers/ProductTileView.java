package com.skynet.fish_shop.view.main.unique_offers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.skynet.fish_shop.R;
import com.skynet.fish_shop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductTileView {
    private final View itemView;
    private final Product product;
    private final Fragment fragment;
    private int resId;
    private final int scrollPosition;
    private final String searchPhrase;
    private final String categoryName;
    private final List<Product> products;

    public ProductTileView(View itemView, Product product, Fragment fragment, String searchPhrase, int scrollPosition, String categoryName, List<Product> productList) {
        this.itemView = itemView;
        this.product = product;
        this.fragment = fragment;
        this.searchPhrase = searchPhrase;
        this.scrollPosition = scrollPosition;
        this.categoryName = categoryName;
        this.products = productList;
    }

    public void setView(int resId) {
        this.resId = resId;
        setProductName();
        setListener();
        setImage();
        setPrice();
        if (discountExists()) {
            itemView.findViewById(resId).findViewById(R.id.discount_row).setVisibility(View.VISIBLE);
            itemView.findViewById(resId).findViewById(R.id.old_price).setVisibility(View.VISIBLE);
            ((TextView) itemView.findViewById(resId).findViewById(R.id.old_price)).setText("" + calculateOldPrice());
            ((TextView) itemView.findViewById(resId).findViewById(R.id.discount)).setText("-" + product.discount + "%");
        } else {
            itemView.findViewById(resId).findViewById(R.id.discount_row).setVisibility(View.GONE);
            itemView.findViewById(resId).findViewById(R.id.old_price).setVisibility(View.GONE);
        }
    }

    private void setPrice() {
        ((TextView) itemView.findViewById(resId).findViewById(R.id.actual_price)).setText(product.price + " руб.");
    }

    private void setImage() {
        ImageView imageView = itemView.findViewById(resId).findViewById(R.id.product_image);
        String path = product.imagePath;
        if (path != null && !path.isEmpty()) Picasso.get().load(path).into(imageView);
    }

    private void setProductName() {
        ((TextView) itemView.findViewById(resId).findViewById(R.id.product_name)).setText(product.name);
    }

    private void setListener() {
        itemView.findViewById(resId).setOnClickListener(view1 -> {
            FragmentTransaction ft = fragment.getParentFragmentManager().beginTransaction();
            ProductFragment productForCategoryFragment = new ProductFragment(product, searchPhrase, scrollPosition,products,categoryName);
            ft.replace(R.id.main_relative_layout, productForCategoryFragment);
            ft.commit();
        });
    }

    private boolean discountExists() {
        return product.discount != 0;
    }

    private int calculateOldPrice() {
        return (product.price * 100) / (100 - product.discount);
    }
}
