package com.skynet.fish_shop.view.main.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fish_shop.R;
import com.skynet.fish_shop.extension.CategoriesKeeper;
import com.skynet.fish_shop.model.Category;
import com.skynet.fish_shop.model.Product;
import com.skynet.fish_shop.model.SubCategory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter<CategoryTileView> {

    private final Fragment fragment;
    private final String subCategoryName;
    private List<Category> categories;

    public SubCategoriesAdapter(Fragment fragment, String subCategoryName) {
        this.fragment = fragment;
        this.subCategoryName = subCategoryName;
        setCategories();
    }

    private void setCategories() {
        List<Category> allCategories = CategoriesKeeper.getInstance().getCategories();
        List<Category> filteredCategories = new ArrayList<>();
        for (Category category : allCategories) {
            List<Product> filteredProductList = new ArrayList<>();
            List<Product> productsFromCategory = category.getProductList();
            for (Product product : productsFromCategory) {
                if (product.subCategory != null && product.subCategory.substring(0, 1).equals(subCategoryName)) {
                    filteredProductList.add(product);
                }
            }
            filteredCategories.add(new Category(subCategoryName, filteredProductList));
        }
        categories = filteredCategories;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryTileView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_tile_view, parent, false);
        return new CategoryTileView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryTileView holder, int position) {
        holder.setView(categories.get(position), fragment, false);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
