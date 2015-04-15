package com.example.nermingraca.bitbayapp.singletons;

import android.util.Log;

import com.example.nermingraca.bitbayapp.Product;
import com.example.nermingraca.bitbayapp.ServiceRequest;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nermingraca on 14/04/15.
 */
public class ProductFeed {
    private static ProductFeed ourInstance = new ProductFeed();

    public static ProductFeed getInstance() {
        return ourInstance;
    }

    private List<Product> mFeed;

    private ProductFeed() {
        mFeed = new ArrayList<Product>();
    }

    public void getFeed(String url){
        ServiceRequest.get(url, parseResponse());
    }

    public List<Product> getFeed(){
        return mFeed;
    }

    private Callback parseResponse(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("RESPONSE", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    for(int i = 0; i < array.length(); i++){
                        JSONObject postObj = array.getJSONObject(i);
                        Log.d("RESPONSE", postObj.toString());
                        int id = postObj.getInt("id");
                        String name = postObj.getString("name");
                        double price = postObj.getDouble("price");
                        mFeed.add(new Product(id, name, price));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
