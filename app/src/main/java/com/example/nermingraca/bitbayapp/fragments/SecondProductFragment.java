package com.example.nermingraca.bitbayapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nermingraca.bitbayapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondProductFragment extends Fragment {

    public static final String SECOND_PRODUCT_FRAGMENT_KEY =
            "ba.nermin.bitcamp.second_product_fragment_key";


    public SecondProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_second_product, container, false);

        Bundle arguments = getArguments();
        int position = arguments.getInt(SECOND_PRODUCT_FRAGMENT_KEY);
        String description = arguments.getString("description");
        String seller = arguments.getString("seller");

        TextView productDesc = (TextView) v.findViewById(R.id.productDesc);
        TextView productSeller = (TextView) v.findViewById(R.id.productSeller);

        productDesc.setText(description);
        productSeller.setText(seller);

        return v;
    }


}
