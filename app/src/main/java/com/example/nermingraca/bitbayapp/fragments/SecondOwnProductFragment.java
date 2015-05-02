package com.example.nermingraca.bitbayapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nermingraca.bitbayapp.R;
import com.example.nermingraca.bitbayapp.singletons.UserData;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondOwnProductFragment extends Fragment {

    public static final String SECOND_OWN_PRODUCT_FRAGMENT_KEY =
            "ba.nermin.bitcamp.second_own_product_fragment_key";

    public SecondOwnProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_second_own_product, container, false);

        Bundle arguments = getArguments();
        int position = arguments.getInt(SECOND_OWN_PRODUCT_FRAGMENT_KEY);
        String description = arguments.getString("description");
        int quantity = arguments.getInt("quantity");
        String quantityText = "Available quantity: " + quantity;

        TextView productDesc = (TextView) v.findViewById(R.id.productDesc);
        TextView productQuantity = (TextView) v.findViewById(R.id.productQuantity);

        productDesc.setText(description);
        productQuantity.setText(quantityText);

        return v;
    }


}
