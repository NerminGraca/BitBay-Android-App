package com.example.nermingraca.bitbayapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nermingraca.bitbayapp.CustomListAdapter;
import com.example.nermingraca.bitbayapp.R;
import com.example.nermingraca.bitbayapp.models.Product;
import com.example.nermingraca.bitbayapp.models.User;
import com.example.nermingraca.bitbayapp.service.ServiceRequest;
import com.example.nermingraca.bitbayapp.singletons.AppController;
import com.example.nermingraca.bitbayapp.singletons.ProductFeed;
import com.example.nermingraca.bitbayapp.singletons.UserData;
import com.example.nermingraca.bitbayapp.singletons.UserFeed;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ProfileActivity extends ActionBarActivity {

    private ListView mProfileProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        TextView usernameV = (TextView) findViewById(R.id.tvUsername);
        TextView emailV = (TextView) findViewById(R.id.tvEmail);

        usernameV.setText(username);
        emailV.setText(email);

        ProductFeed productFeed = ProductFeed.getInstance();
        productFeed.getFeed(getString(R.string.service_products));

        List<Product> products = productFeed.getFeed();

        List<Product> userProducts = new ArrayList<Product>();
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product temp = iterator.next();
            if (temp.getmOwner().equals(username)) {
                userProducts.add(temp);
            }
        }

        mProfileProductList = (ListView)findViewById(R.id.listOnProfile);

        CustomListAdapter productsAdapter = new CustomListAdapter
                (this, userProducts);

        mProfileProductList.setAdapter(productsAdapter);

        mProfileProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product clicked = (Product)parent.getItemAtPosition(position);
                Intent intent = new Intent(ProfileActivity.this, ProductActivity.class);
                intent.putExtra("id", clicked.getmId());
                intent.putExtra("name", clicked.getmName());
                intent.putExtra("description", clicked.getmDescription());
                intent.putExtra("imagePath", clicked.getThumbnailUrl());
                intent.putExtra("seller", clicked.getmOwner());
                double priceDouble = clicked.getmPrice();
                String price = String.format( "$" + "%.2f", priceDouble );
                intent.putExtra("price", price);
                intent.putExtra("sellerId", clicked.getmSellerId());
                startActivity(intent);
            }
        });
    }

    public static User getCurrentUser() {
        int id = UserData.getInstance().getId();
        String resUrl = AppController.getContext().getResources().getString(R.string.service_user);
        String url = String.format(resUrl, id);
        UserFeed userFeed = UserFeed.getInstance();
        return userFeed.getFeed(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */

        if (id == R.id.logout_action) {
            MainActivity.logout();
            moveTaskToBack(true);
            Intent toLogin = new Intent( ProfileActivity.this, MainActivity.class);
            startActivity(toLogin);
            return true;
        }

        if (id == R.id.profile_action) {
            User user = ProfileActivity.getCurrentUser();
            moveTaskToBack(true);
            Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
            intent.putExtra("username", user.getmUsername());
            intent.putExtra("email", user.getmEmail());
            startActivity(intent);
            return true;
        }

        if (id == R.id.cart_action) {
            toCart();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toCart() {
        int buyerId = UserData.getInstance().getId();
        String url = getString(R.string.service_get_cart);
        JSONObject json = new JSONObject();

        try {
            json.put("userId", buyerId);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.getMessage());
        }
        String jsonString = json.toString();
        Log.d("DEBUG", jsonString);
        Callback callback = response();
        ServiceRequest.post(url, jsonString, callback);
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
                Intent toCart = new Intent(ProfileActivity.this, CartActivity.class);
                toCart.putExtra("jsonProducts", responseJson);
                startActivity(toCart);
            }
        };

    }
}
