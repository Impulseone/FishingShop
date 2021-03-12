package com.skynet.fishingshop.view.main.cart;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        new GetCartProductsTask(view).execute();
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

    interface ChangePrice {
        void changePrice();
    }

    static class GetCartProductsTask extends AsyncTask<Void, Void, Void> {

        private final View view;
        private List<CartProduct> products;

        public GetCartProductsTask(View view) {
            this.view = view;
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
            recyclerView.setAdapter(new CartProductsAdapter(products, () -> new GetCartProductsTask(view).execute()));

            int allPrice = 0;
            for (CartProduct cartProduct : products) {
                allPrice += cartProduct.price * cartProduct.count;
            }
            ((TextView) view.findViewById(R.id.all_price)).setText(allPrice + " руб.");
        }
    }
}