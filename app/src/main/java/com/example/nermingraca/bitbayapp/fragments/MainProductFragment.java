package com.example.nermingraca.bitbayapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nermingraca.bitbayapp.CartActivity;
import com.example.nermingraca.bitbayapp.R;
import com.example.nermingraca.bitbayapp.service.ServiceRequest;
import com.example.nermingraca.bitbayapp.singletons.UserData;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainProductFragment extends Fragment {

    public static final String MAIN_PRODUCT_FRAGMENT_KEY =
            "ba.nermin.bitcamp.main_product_fragment_key";
    private Button mToCartButton;


    public MainProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_product, container, false);

        Bundle arguments = getArguments();
        int position = arguments.getInt(MAIN_PRODUCT_FRAGMENT_KEY);
        String imgPath = arguments.getString("imgPath");
        String name = arguments.getString("name");
        String price = arguments.getString("price");
        final int buyerId = UserData.getInstance().getId();
        final int productId = arguments.getInt("productId");


        TextView productName = (TextView) v.findViewById(R.id.productName);
        TextView productPrice = (TextView) v.findViewById(R.id.productPrice);
        ImageView productImage = (ImageView) v.findViewById(R.id.productImage);

        productName.setText(name);
        productPrice.setText(price);
        Picasso.with(getActivity().getApplicationContext()).load(imgPath).into(productImage);

        mToCartButton = (Button) v.findViewById(R.id.view_to_cart_button);
        mToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.service_to_cart);
                JSONObject json = new JSONObject();

                try {
                    json.put("userId", buyerId);
                    json.put("productId", productId);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", e.getMessage());
                }
                String jsonString = json.toString();
                Log.d("DEBUG", jsonString);
                Callback callback = response();
                ServiceRequest.post(url, jsonString, callback);

            }
        });

        return v;
    }

    public Callback response() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("ERROR", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String responseJson = response.body().string();
                Log.d("DEBUG", responseJson);
                Intent toCart = new Intent(getActivity(), CartActivity.class);
                toCart.putExtra("jsonProducts", responseJson);
                startActivity(toCart);
            }
        };

    }


}
