package com.skynet.fishingshop.view.main.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.productsList.ProductsListFragment;

import org.jetbrains.annotations.NotNull;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryTileView> {

    private final Fragment fragment;

    public CategoriesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NotNull
    @Override
    public CategoryTileView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_tile_view, parent, false);
        return new CategoryTileView(view, fragment);
    }

    @Override
    public void onBindViewHolder(final CategoryTileView holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public static class CategoryTileView extends RecyclerView.ViewHolder {

        public CategoryTileView(View view, Fragment fragment) {
            super(view);
            view.setOnClickListener(view1 -> {
                FragmentTransaction ft = fragment.getParentFragmentManager().beginTransaction();
                ProductsListFragment productsListFragment = new ProductsListFragment();
                ft.replace(R.id.main_relative_layout, productsListFragment);
                ft.commit();
            });
        }
    }
}