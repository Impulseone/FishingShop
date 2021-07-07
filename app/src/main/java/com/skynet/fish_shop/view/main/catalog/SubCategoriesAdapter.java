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
    private final Category category;
    private List<Category> categories;

    public SubCategoriesAdapter(Fragment fragment, Category category) {
        this.fragment = fragment;
        this.category = category;
        setSubCategories();
        notifyDataSetChanged();
    }

    private void setSubCategories() {

        List<SubCategory> subCategories = CategoriesKeeper.getInstance().getSubCategories();
        List<Category> categoriesForSubCategoriesList = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            if (subCategory.getId().substring(0, 1).equals(category.getName().substring(0, 1))) {
                categoriesForSubCategoriesList.add(new Category(subCategory.getId()+" "+ subCategory.getName(), new ArrayList<>()));
            }
        }

        for (Category categoryForSubCategoriesList : categoriesForSubCategoriesList) {
            for (Product product : category.getProductList()) {
                if (product.subCategory != null && product.subCategory.equals(categoryForSubCategoriesList.getName().split(" ")[0])) {
                    categoryForSubCategoriesList.getProductList().add(product);
                }
            }

        }
        categories = categoriesForSubCategoriesList;
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
        return categories.size();
    }
}
