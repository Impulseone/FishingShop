package com.skynet.fishingshop.view.main.cart;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skynet.fishingshop.App;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.db.CartProduct;

import java.util.List;

public class CartProductTileView extends RecyclerView.ViewHolder {

    private CartProduct cartProduct;
    private final CartProductsAdapter adapter;
    private final List<CartProduct> products;

    public CartProductTileView(@NonNull View itemView, List<CartProduct> products, CartProductsAdapter adapter) {
        super(itemView);
        this.products = products;
        this.adapter = adapter;
    }

    public void setView(CartProduct cartProduct) {
        this.cartProduct = cartProduct;
        setImage();
        setPrice();
        setProductName();
        setProductDescription();
        setProductCount();
        setDeleteButton();
    }

    private void setImage() {
        ImageView imageView = itemView.findViewById(R.id.product_image);
        Glide.with(itemView.getContext()).load(cartProduct.imagePath).into(imageView);
    }

    private void setPrice() {
        ((TextView) itemView.findViewById(R.id.product_price)).setText(cartProduct.price + " руб.");
    }

    private void setProductName() {
        ((TextView) itemView.findViewById(R.id.product_name)).setText(cartProduct.name);
    }

    private void setProductDescription() {
        ((TextView) itemView.findViewById(R.id.product_description)).setText(cartProduct.description);
    }

    private void setProductCount() {
        ((TextView) itemView.findViewById(R.id.product_count)).setText(cartProduct.count + " шт.");
    }

    private void setDeleteButton() {
        itemView.findViewById(R.id.delete_button).setOnClickListener(view -> {
            new DeleteTask(adapter,products).execute(cartProduct);
        });
    }

    static class DeleteTask extends AsyncTask<CartProduct, Void, Void> {

        private final CartProductsAdapter adapter;
        private final List<CartProduct> products;

        public DeleteTask(CartProductsAdapter adapter, List<CartProduct> products) {
            this.adapter = adapter;
            this.products = products;
        }

        @Override
        protected Void doInBackground(CartProduct... cartProducts) {
            App.getInstance().getDatabase().productDao().delete(cartProducts[0]);
            products.remove(cartProducts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
        }
    }
}
