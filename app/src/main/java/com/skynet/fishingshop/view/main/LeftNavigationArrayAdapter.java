package com.skynet.fishingshop.view.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.skynet.fishingshop.R;

public class LeftNavigationArrayAdapter extends BaseAdapter {

    private final String[] data;
    private final LayoutInflater inflater;
    private final Context context;

    public LeftNavigationArrayAdapter(Context context, String[] data) {
        this.data = data;
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.drawer_list_item, viewGroup, false);
        }
        String item = data[i];
        ((TextView) view.findViewById(R.id.left_menu_item_text)).setText(item);
        if (item.equals("Профиль")){
            ((ImageView) view.findViewById(R.id.left_menu_item_icon)).setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_icon_awesome_user_circle));
            ((ImageView) view.findViewById(R.id.left_menu_item_icon)).setPadding(20,20,20,20);
        }
        if (item.equals("Избранное")) {
            ((ImageView) view.findViewById(R.id.left_menu_item_icon)).setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_diamond));
        }
        return view;
    }
}
