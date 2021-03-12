package com.skynet.fishingshop.view.main.cart;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.App;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.db.CartProduct;
import com.skynet.fishingshop.view.main.orderConfirmation.OrderConfirmationFragment;

import java.util.List;

public class CartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        new GetAllProductsTask(view).execute();
        setConfirmButton(view);

        return view;
    }

    private void setConfirmButton(View view) {
        view.findViewById(R.id.confirm_button).setOnClickListener(view1 -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            OrderConfirmationFragment orderConfirmationFragment = new OrderConfirmationFragment();
            ft.replace(R.id.main_relative_layout, orderConfirmationFragment);
            ft.commit();
        });
    }

    static class GetAllProductsTask extends AsyncTask<Void, Void, Void> {

        private final View view;
        private List<CartProduct> products;

        public GetAllProductsTask(View view) {
            this.view = view;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            products = App.getInstance().getDatabase().productDao().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.products_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(new CartProductsAdapter(products));
        }
    }
}