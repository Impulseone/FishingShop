package com.skynet.fish_shop.view.main.productsList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.view.main.home.HomeFragment;

public class SearchedProductsListFragment extends Fragment {

    private final String searchPhrase;
    private final int scrollPosition;

    public SearchedProductsListFragment(String searchPhrase, int scrollPosition) {
        this.searchPhrase = searchPhrase;
        this.scrollPosition = scrollPosition;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searched_products_list, container, false);
        setBackButton(view);
        setAdapter(view);
        return view;
    }

    private void setBackButton(View view) {
        view.findViewById(R.id.back_button).setOnClickListener(view1 -> back());
    }

    private void setAdapter(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.products_rv);
        SearchedProductsAdapter adapter = new SearchedProductsAdapter(this, searchPhrase);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.getLayoutManager().scrollToPosition(scrollPosition);
        recyclerView.setAdapter(adapter);
    }

    private void back() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.replace(R.id.main_relative_layout, homeFragment);
        ft.commit();
        ((BottomNavigationView) this.getActivity().findViewById(R.id.bottom_navigation)).setSelectedItemId(R.id.action_main);
    }
}