package com.skynet.fish_shop.view.main.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fish_shop.R;
import com.skynet.fish_shop.extension.CategoriesKeeper;
import com.skynet.fish_shop.model.Category;
import com.skynet.fish_shop.model.CategoryIcon;
import com.skynet.fish_shop.view.main.productsList.ProductsListForCategoryFragment;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryTileView> {

    private final Fragment fragment;
    private List<Category> categoriesData;

    public CategoriesAdapter(Fragment fragment) {
        this.fragment = fragment;
        categoriesData = CategoriesKeeper.getInstance().getCategories();
    }

    public void update() {
        categoriesData = CategoriesKeeper.getInstance().getCategories();
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public CategoryTileView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_tile_view, parent, false);
        return new CategoryTileView(view);
    }

    @Override
    public void onBindViewHolder(final CategoryTileView holder, int position) {
        holder.setView(categoriesData.get(position), fragment, true);
    }

    @Override
    public int getItemCount() {
        return categoriesData.size();
    }
}