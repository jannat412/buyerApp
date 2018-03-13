package com.ingeniumbd.buyerapp.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ingeniumbd.buyerapp.R;

import java.util.ArrayList;

/**
 * Created by Princess on 2/9/2018.
 */

public class MyDialogFragment extends DialogFragment {

    ListView placeList;
    ArrayList<String> riyadh = new ArrayList<String>();
    ArrayList<String> jeddah = new ArrayList<String>();
    ArrayList<String> madinah = new ArrayList<String>();
    OnCitySelectedListener onCitySelectedListener;

    public interface OnCitySelectedListener {
        public void onCitySelected(String city);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        try {
            onCitySelectedListener = (OnCitySelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException((context.toString() + "must implement"));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.place_select_fragment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        placeList = (ListView) view.findViewById(R.id.placeList);

        riyadh.add("Al-Bat'ha");
        riyadh.add("Al-'Olayya & Sulaymaniyyah");
        riyadh.add("Nemar");
        riyadh.add("Irqah");
        riyadh.add("Diplomatic Quarter");
        riyadh.add("Al-Shemaysi");
        riyadh.add("Al-Ma'athar");
        riyadh.add("Al-Ha'ir");
        riyadh.add("Al-'Aziziyyah");
        riyadh.add("Al-Malaz");
        riyadh.add("Al-Shifa");
        riyadh.add("Al-Urayja");
        riyadh.add("Al-Shemal");
        riyadh.add("Al-Naseem");
        riyadh.add("Al-Rawdhah");
        riyadh.add("Al-Selayy");

        ArrayAdapter myAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, riyadh);
        placeList.setAdapter(myAdapter);
        placeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "" + riyadh.get(i), Toast.LENGTH_LONG).show();
                onCitySelectedListener.onCitySelected(riyadh.get(i));
                getDialog().dismiss();
            }
        });
        return view;
    }
}
