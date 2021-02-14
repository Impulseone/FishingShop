package com.skynet.fishingshop.view.main.categoryProducts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.skynet.fishingshop.R;

public class ProductsOfCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_of_category);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.products_rv);
        ProductsOfCategoryAdapter adapter = new ProductsOfCategoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}