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
import com.skynet.fishingshop.view.main.product.ProductFragment;

public class ProductTileView {
    private View itemView;
    private Product product;
    private Fragment fragment;
    private Category category;

    public ProductTileView(View itemView, Product product, Fragment fragment, Category category) {
        this.itemView = itemView;
        this.product = product;
        this.fragment = fragment;
        this.category = category;
    }

    public void setView() {
        setProductName();
        setListener();
        setImage();
        if (discountExists()) {
            itemView.findViewById(R.id.discount_row).setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.old_price).setVisibility(View.VISIBLE);
            ((TextView) itemView.findViewById(R.id.old_price)).setText(calculateOldPrice());
            ((TextView) itemView.findViewById(R.id.discount)).setText("-" + product.discount + "%");
        } else {
            itemView.findViewById(R.id.discount_row).setVisibility(View.GONE);
            itemView.findViewById(R.id.old_price).setVisibility(View.GONE);
        }
    }

    private void setImage(){
        ImageView imageView = itemView.findViewById(R.id.product_image);
        Glide.with(itemView.getContext()).load(product.imagePath).into(imageView);
    }

    private void setProductName() {
        ((TextView) itemView.findViewById(R.id.first).findViewById(R.id.product_name)).setText(product.name);
    }

    private void setListener() {
        itemView.findViewById(R.id.first).setOnClickListener(view1 -> {
            FragmentTransaction ft = fragment.getParentFragmentManager().beginTransaction();
            ProductFragment productFragment = new ProductFragment(category,product);
            ft.replace(R.id.main_relative_layout, productFragment);
            ft.commit();
        });
    }

    private boolean discountExists() {
        return product.discount != 0;
    }

    private int calculateOldPrice() {
        return (100 * product.price) / product.discount;
    }
}
