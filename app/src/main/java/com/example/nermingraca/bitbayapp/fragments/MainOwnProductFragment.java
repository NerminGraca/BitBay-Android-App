package com.example.nermingraca.bitbayapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nermingraca.bitbayapp.R;
import com.example.nermingraca.bitbayapp.singletons.UserData;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainOwnProductFragment extends Fragment {

    public static final String MAIN_OWN_PRODUCT_FRAGMENT_KEY =
            "ba.nermin.bitcamp.main_own_product_fragment_key";


    public MainOwnProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_own_product, container, false);

        Bundle arguments = getArguments();
        int position = arguments.getInt(MAIN_OWN_PRODUCT_FRAGMENT_KEY);
        String imgPath = arguments.getString("imgPath");
        String name = arguments.getString("name");
        String price = arguments.getString("price");

        TextView productName = (TextView) v.findViewById(R.id.productName);
        TextView productPrice = (TextView) v.findViewById(R.id.productPrice);
        ImageView productImage = (ImageView) v.findViewById(R.id.productImage);

        productName.setText(name);
        productPrice.setText(price);
        Picasso.with(getActivity().getApplicationContext()).load(imgPath).into(productImage);

        return v;
    }


}
