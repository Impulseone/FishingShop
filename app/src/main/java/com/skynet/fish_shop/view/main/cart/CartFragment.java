package com.skynet.fish_shop.view.main.cart;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fish_shop.App;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.db.CartProduct;
import com.skynet.fish_shop.view.main.orderConfirmation.OrderConfirmationFragment;

import java.util.List;

public class CartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        new GetCartProductsTask(view, getParentFragmentManager()).execute();

        return view;
    }

    interface ChangePrice {
        void changePrice();
    }

    static class GetCartProductsTask extends AsyncTask<Void, Void, Void> {

        private final View view;
        private List<CartProduct> products;
        private final FragmentManager fragmentManager;

        public GetCartProductsTask(View view, FragmentManager fragmentManager) {
            this.view = view;
            this.fragmentManager = fragmentManager;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            products = App.getInstance().getDatabase().cartProductDao().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.products_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(new CartProductsAdapter(products, () -> new GetCartProductsTask(view,fragmentManager).execute()));
            int allPrice = 0;
            for (CartProduct cartProduct : products) {
                allPrice += cartProduct.price * cartProduct.count;
            }
            ((TextView) view.findViewById(R.id.all_price)).setText(allPrice + " руб.");
            if (products.size() > 0) {
                View confirmButton = view.findViewById(R.id.confirm_button);
                confirmButton.setVisibility(View.VISIBLE);
                confirmButton.setOnClickListener(view1 -> {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    OrderConfirmationFragment orderConfirmationFragment = new OrderConfirmationFragment(products);
                    ft.replace(R.id.main_relative_layout, orderConfirmationFragment);
                    ft.commit();
                });
            }
        }
    }
}