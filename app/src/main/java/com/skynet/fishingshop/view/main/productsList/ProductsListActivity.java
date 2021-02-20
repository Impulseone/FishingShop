package com.skynet.fishingshop.view.main.productsList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Pair;

import com.skynet.fishingshop.R;

import java.util.ArrayList;
import java.util.List;

public class ProductsListActivity extends AppCompatActivity {

   private List<Pair<String,String>> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        createProducts();

        findViewById(R.id.back_button).setOnClickListener(view -> back());

//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.products_rv);
//        ProductsListAdapter adapter = new ProductsListAdapter(products, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
    }

    private void back(){
        super.onBackPressed();
    }

    private void createProducts(){
        products = new ArrayList<>();
        products.add(new Pair<>("Катушка New Brand 1","Катушка New Brand 2"));
        products.add(new Pair<>("Катушка New Brand 3","Катушка New Brand 4"));
        products.add(new Pair<>("Катушка Old Brand 1","Катушка Old Brand 2"));
        products.add(new Pair<>("Катушка Old Brand 3","Катушка Old Brand 4"));
    }
}