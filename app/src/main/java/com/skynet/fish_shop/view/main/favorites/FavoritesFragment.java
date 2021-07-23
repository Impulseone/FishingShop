package com.skynet.fish_shop.view.main.favorites;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fish_shop.App;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.db.FavoritesProduct;

import java.util.List;

public class FavoritesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        new GetFavoritesProductsTask(view).execute();
        return view;
    }

    static class GetFavoritesProductsTask extends AsyncTask<Void, Void, Void> {

        private final View view;
        private List<FavoritesProduct> products;

        public GetFavoritesProductsTask(View view) {
            this.view = view;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            products = App.getInstance().getDatabase().favoritesProductDao().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.products_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(new FavoriteProductsAdapter(products));
        }
    }
}