package com.example.nermingraca.bitbayapp.singletons;

import android.util.Log;

import com.example.nermingraca.bitbayapp.models.Product;
import com.example.nermingraca.bitbayapp.models.User;
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
 * Created by nermingraca on 22/04/15.
 */
public class UserFeed {
    private static UserFeed ourInstance = new UserFeed();

    public static UserFeed getInstance() {
        return ourInstance;
    }

    private User user = new User();

    private UserFeed() {
        User user = new User();
    }

    public User getFeed(String url){
        ServiceRequest.get(url, parseResponse());
        return user;
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
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("RESPONSE", jsonObject.toString());
                    int userId = jsonObject.getInt("id");
                    String email = jsonObject.getString("email");
                    String password = jsonObject.getString("password");
                    String username = jsonObject.getString("username");
                    List<Product> products = new ArrayList<Product>();

                    JSONArray array = jsonObject.getJSONArray("products");
                    for(int i = 0; i < array.length(); i++){
                        JSONObject productObj = array.getJSONObject(i);
                        Log.d("RESPONSE", productObj.toString());
                        int id = productObj.getInt("id");
                        String name = productObj.getString("name");
                        double price = productObj.getDouble("price");
                        String description = productObj.getString("description");
                        String owner = productObj.getString("owner");
                        String imagePath = productObj.getString("productImagePath1");
                        int sellerId = productObj.getInt("ownerId");
                        int quantity = productObj.getInt("quantity");
                        double ownerRating = productObj.getDouble("ownerRating");
                        String ownerAddress = productObj.getString("ownerAddress");
                        products.add(new Product
                                (id, name, price, description, owner, imagePath, sellerId, quantity,
                                        ownerRating, ownerAddress));
                    }

                    user.setmEmail(email);
                    user.setmId(userId);
                    user.setmPassword(password);
                    user.setmUsername(username);
                    user.setmProducts(products);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
