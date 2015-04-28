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
    private Button mViewUserButton;


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
        int quantity = arguments.getInt("quantity");
        String quantityText = "Available quantity: " + quantity;
        final int sellerId = arguments.getInt("sellerId");

        TextView productDesc = (TextView) v.findViewById(R.id.productDesc);
        TextView productSeller = (TextView) v.findViewById(R.id.productSeller);
        TextView productQuantity = (TextView) v.findViewById(R.id.productQuantity);

        productDesc.setText(description);
        productSeller.setText(seller);
        productQuantity.setText(quantityText);

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
                Log.e("RESPONSE", e.getMessage());
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
