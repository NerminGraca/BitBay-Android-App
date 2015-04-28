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
public class ProductSellerFragment extends Fragment {

    public static final String PRODUCT_SELLER_FRAGMENT_KEY =
            "ba.nermin.bitcamp.product_seller_fragment_key";
    private Button mViewUserButton;


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
        final int sellerId = arguments.getInt("sellerId");

        TextView sellerTV = (TextView) v.findViewById(R.id.sellerName);
        TextView sellerAddressTV = (TextView) v.findViewById(R.id.sellerAddress);
        TextView sellerRatingTV = (TextView) v.findViewById(R.id.sellerRating);

        sellerTV.setText(seller);
        sellerAddressTV.setText(sellerAddress);
        sellerRatingTV.setText(sellerRatingString);

        mViewUserButton = (Button) v.findViewById(R.id.view_user_button);
        mViewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resUrl = getString(R.string.service_user);
                String url = String.format(resUrl, sellerId);
                Log.e("RESPONSE", url);
                Callback callback = parseResponse();
                ServiceRequest.get(url, callback);

            }
        });

        return v;
    }

    public Callback parseResponse() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("ERROR", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {

                try {
                    String responseJson = response.body().string();

                    JSONObject userJson = new JSONObject(responseJson);
                    Log.e("RESPONSE", userJson.toString());

                    String username = userJson.getString("username");
                    Log.e("RESPONSE", username);

                    String email = userJson.getString("email");

                    Intent goToSeller = new Intent(getActivity(), SellerActivity.class);
                    goToSeller.putExtra("username", username);
                    goToSeller.putExtra("email", email);

                    startActivity(goToSeller);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

    }


}
