package com.skynet.fishingshop.view.main.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.extension.CategoriesKeeper;
import com.skynet.fishingshop.model.Category;
import com.skynet.fishingshop.view.main.productsList.ProductsListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryTileView> {

    private final Fragment fragment;
    private final List<Category> categoriesData = CategoriesKeeper.getInstance().getCategories();

    public CategoriesAdapter(Fragment fragment) {
        this.fragment = fragment;
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
        holder.setView(categoriesData.get(position), fragment);
    }

    @Override
    public int getItemCount() {
        return categoriesData.size();
    }

    public static class CategoryTileView extends RecyclerView.ViewHolder {

        public CategoryTileView(View view) {
            super(view);
        }

        public void setView(Category category, Fragment fragment) {
            ((TextView) itemView.findViewById(R.id.products_count)).setText(category.getProductList().size() + " товаров");
            ((TextView) itemView.findViewById(R.id.category_name)).setText(category.getCategoryName());
            itemView.setOnClickListener(view1 -> {
                FragmentTransaction ft = fragment.getParentFragmentManager().beginTransaction();
                ProductsListFragment productsListFragment = new ProductsListFragment(category.getProductList());
                ft.replace(R.id.main_relative_layout, productsListFragment);
                ft.commit();
            });
        }
    }
}