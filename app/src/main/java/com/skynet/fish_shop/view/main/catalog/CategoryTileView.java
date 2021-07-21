package com.skynet.fish_shop.view.main.catalog;

import android.view.View;
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

import java.util.List;

public class CategoryTileView extends RecyclerView.ViewHolder {

    public CategoryTileView(View view) {
        super(view);
    }

    public void setView(Category category, Fragment fragment, boolean isSubCategories) {
        setCategoryIcon(category.getName());
        ((TextView) itemView.findViewById(R.id.products_count)).setText(category.getProductList().size() + " товаров");
        ((TextView) itemView.findViewById(R.id.category_name)).setText(setCategoryName(category.getName()));
        if (!isSubCategories) itemView.setOnClickListener(view1 -> {
            FragmentTransaction ft = fragment.getParentFragmentManager().beginTransaction();
            ProductsListForCategoryFragment productsListForCategoryFragment = new ProductsListForCategoryFragment(category,0);
            ft.replace(R.id.main_relative_layout, productsListForCategoryFragment);
            ft.commit();
        });
        else {
            itemView.setOnClickListener(view -> {
                FragmentTransaction ft = fragment.getParentFragmentManager().beginTransaction();
                SubCategoriesFragment subCategoriesFragment = new SubCategoriesFragment(category);
                ft.replace(R.id.main_relative_layout, subCategoriesFragment);
                ft.commit();
            });
        }
    }

    private String setCategoryName(String categoryName){
        StringBuilder resultName = new StringBuilder();
        String[] split = categoryName.split(" ");
        for (int i = 1; i < split.length; i++) {
            resultName.append(split[i]+" ");
        }
        return resultName.toString();
    }

    private void setCategoryIcon(String categoryName) {
        ImageView imageView = itemView.findViewById(R.id.category_icon);
        String path = getImagePath(categoryName);
        if (path != null && !path.isEmpty()) Picasso.get().load(path).into(imageView);
    }

    private String getImagePath(String categoryName) {
        String imagePath = "";
        List<CategoryIcon> categoryIcons = CategoriesKeeper.getInstance().getCategoryIcons();
        for (CategoryIcon categoryIcon : categoryIcons) {
            if (categoryIcon.getCategoryName().contains(categoryName)) {
                imagePath = categoryIcon.getImagePath();
                break;
            }
        }
        return imagePath;
    }
}
