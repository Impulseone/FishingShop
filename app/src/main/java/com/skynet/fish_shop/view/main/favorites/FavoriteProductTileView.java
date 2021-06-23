package com.skynet.fish_shop.view.main.favorites;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skynet.fish_shop.App;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.db.AppDatabase;
import com.skynet.fish_shop.db.CartProduct;
import com.skynet.fish_shop.db.FavoritesProduct;
import com.squareup.picasso.Picasso;

public class FavoriteProductTileView extends RecyclerView.ViewHolder{

    private FavoritesProduct favoritesProduct;

    public FavoriteProductTileView(@NonNull View itemView) {
        super(itemView);
    }

    public void setView(FavoritesProduct favoritesProduct){
        this.favoritesProduct = favoritesProduct;
        setImage();
        setPrice();
        setProductName();
        setProductDescription();
        setAddToCartButton();
    }

    private void setImage() {
        ImageView imageView = itemView.findViewById(R.id.product_image);
        String path = favoritesProduct.imagePath;
        if (path != null && !path.isEmpty()) Picasso.get().load(path).into(imageView);
    }

    private void setPrice() {
        ((TextView) itemView.findViewById(R.id.product_price)).setText(favoritesProduct.price + " руб.");
    }

    private void setProductName() {
        ((TextView) itemView.findViewById(R.id.product_name)).setText(favoritesProduct.name);
    }

    private void setProductDescription() {
        ((TextView) itemView.findViewById(R.id.product_description)).setText(favoritesProduct.description);
    }

    private void setAddToCartButton() {
        View addToCartButton = itemView.findViewById(R.id.add_to_cart_button);
        addToCartButton.setOnClickListener(view1 -> new AddProductToCartTask(favoritesProduct, itemView.getContext()).execute());
    }
    static class AddProductToCartTask extends AsyncTask<Void, Void, Void> {

        private final Context context;
        private final FavoritesProduct product;

        public AddProductToCartTask(FavoritesProduct product, Context context) {
            this.product = product;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase appDatabase = App.getInstance().getDatabase();
            CartProduct cartProduct = new CartProduct(product);
            CartProduct existing = appDatabase.cartProductDao().getById(cartProduct.id);
            if (existing != null) {
                cartProduct.count = cartProduct.count + 1;
                appDatabase.cartProductDao().update(cartProduct);
            } else
                appDatabase.cartProductDao().insert(cartProduct);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(context, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
        }
    }
}
