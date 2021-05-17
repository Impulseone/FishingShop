package com.skynet.fish_shop.view.main.requisites;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skynet.fish_shop.R;

public class RequisitesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requisites, container, false);
        setRequisitesText(view);
        return view;
    }

    private void setRequisitesText(View view) {
        TextView textView = view.findViewById(R.id.requisites_text);
        textView.setText("Индивидуальный предприниматель ГУЛЕНКО ДМИТРИЙ АЛЕКСЕЕВИЧ\n" +
                "Расчётный счёт: 40802810330000072967\n" +
                "Банк: КРАСНОДАРСКОЕ ОТДЕЛЕНИЕ N8619 ПАО СБЕРБАНК\n" +
                "БИК: 040349602" +
                "Кор. Cчёт: 30101810100000000602\n" +
                "ОГРН: 319237500379634\n" +
                "ИНН: 781117867809\n" +
                "КПП: —\n\n" + "Краснодарский край, станица Каневская, ул. Свердликова, дом 116\n\n" +
                "Телефон: +79676726107, +79528535974");
    }
}