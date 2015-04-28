package com.example.nermingraca.bitbayapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nermingraca.bitbayapp.R;
import com.example.nermingraca.bitbayapp.SellerActivity;
import com.example.nermingraca.bitbayapp.service.ServiceRequest;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
        int quantity = arguments.getInt("quantity");
        String quantityText = "Available quantity: " + quantity;

        TextView productDesc = (TextView) v.findViewById(R.id.productDesc);
        TextView productQuantity = (TextView) v.findViewById(R.id.productQuantity);

        productDesc.setText(description);
        productQuantity.setText(quantityText);

        return v;
    }

}
