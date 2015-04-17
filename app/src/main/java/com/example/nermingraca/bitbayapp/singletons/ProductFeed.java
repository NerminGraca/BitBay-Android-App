package com.example.nermingraca.bitbayapp.singletons;

import android.util.Log;

import com.example.nermingraca.bitbayapp.models.Product;
import com.example.nermingraca.bitbayapp.service.ServiceRequest;
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
                if(mFeed.size() > 0) {
                    mFeed.clear();
                }
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    for(int i = 0; i < array.length(); i++){
                        JSONObject productObj = array.getJSONObject(i);
                        Log.d("RESPONSE", productObj.toString());
                        int id = productObj.getInt("id");
                        String name = productObj.getString("name");
                        double price = productObj.getDouble("price");
                        String description = productObj.getString("description");
                        mFeed.add(new Product(id, name, price, description));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
