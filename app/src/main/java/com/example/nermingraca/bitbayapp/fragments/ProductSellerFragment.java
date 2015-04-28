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
public class ProductSellerFragment extends Fragment {

    public static final String PRODUCT_SELLER_FRAGMENT_KEY =
            "ba.nermin.bitcamp.product_seller_fragment_key";


    public ProductSellerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_seller, container, false);
        Bundle arguments = getArguments();
        int position = arguments.getInt(PRODUCT_SELLER_FRAGMENT_KEY);
        String seller = arguments.getString("seller");
        String sellerAddress = arguments.getString("sellerAddress");
        double sellerRating = arguments.getDouble("sellerRating");
        String sellerRatingString = String.format("%.2f", sellerRating);

        TextView sellerTV = (TextView) v.findViewById(R.id.sellerName);
        TextView sellerAddressTV = (TextView) v.findViewById(R.id.sellerAddress);
        TextView sellerRatingTV = (TextView) v.findViewById(R.id.sellerRating);

        sellerTV.setText(seller);
        sellerAddressTV.setText(sellerAddress);
        sellerRatingTV.setText(sellerRatingString);

        return v;
    }


}
