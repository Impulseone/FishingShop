package com.skynet.fishingshop.view.main.productsList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.model.Category;
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.main.product.ProductForCategoryFragment;

public class ProductForCategoryTileView {
    private final View itemView;
    private final Product product;
    private final Fragment fragment;
    private final Category category;
    private int resId;

    public ProductForCategoryTileView(View itemView, Product product, Fragment fragment, Category category) {
        this.itemView = itemView;
        this.product = product;
        this.fragment = fragment;
        this.category = category;
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
            ((TextView) itemView.findViewById(resId).findViewById(R.id.old_price)).setText(""+calculateOldPrice());
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
        Glide.with(itemView.getContext()).load(product.imagePath).into(imageView);
    }

    private void setProductName() {
        ((TextView) itemView.findViewById(resId).findViewById(R.id.product_name)).setText(product.name);
    }

    private void setListener() {
        itemView.findViewById(resId).setOnClickListener(view1 -> {
            FragmentTransaction ft = fragment.getParentFragmentManager().beginTransaction();
            ProductForCategoryFragment productForCategoryFragment = new ProductForCategoryFragment(category, product);
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
