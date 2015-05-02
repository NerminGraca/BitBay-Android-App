package com.example.nermingraca.bitbayapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nermingraca.bitbayapp.R;
import com.example.nermingraca.bitbayapp.fragments.MainOwnProductFragment;
import com.example.nermingraca.bitbayapp.fragments.MainProductFragment;
import com.example.nermingraca.bitbayapp.fragments.ProductSellerFragment;
import com.example.nermingraca.bitbayapp.fragments.SecondOwnProductFragment;
import com.example.nermingraca.bitbayapp.fragments.SecondProductFragment;
import com.example.nermingraca.bitbayapp.models.User;
import com.example.nermingraca.bitbayapp.service.ServiceRequest;
import com.example.nermingraca.bitbayapp.singletons.UserData;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class FragmentedProductActivity extends ActionBarActivity {

    private int id;
    private String name;
    private String description;
    private String price;
    private String imagePath;
    private String seller;
    private int quantity;
    private int sellerId;
    private double sellerRating;
    private String sellerAddress;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmented_product);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        description = intent.getStringExtra("description");
        price = intent.getStringExtra("price");
        imagePath = intent.getStringExtra("imagePath");
        seller = intent.getStringExtra("seller");
        sellerId = intent.getIntExtra("sellerId", 0);
        quantity = intent.getIntExtra("quantity", 0);
        sellerRating = intent.getDoubleExtra("sellerRating", 0);
        sellerAddress = intent.getStringExtra("sellerAddress");

        BitAdapter bitAdapter = new BitAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(bitAdapter);
    }

    private class BitAdapter extends FragmentStatePagerAdapter {

        public BitAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment show;
            user = ProfileActivity.getCurrentUser();

            if(position == 0) {
                if(sellerId == user.getmId()) {
                    show = new MainOwnProductFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt(MainOwnProductFragment.MAIN_OWN_PRODUCT_FRAGMENT_KEY, position);
                    arguments.putString("imgPath", imagePath);
                    arguments.putString("name", name);
                    arguments.putString("price", price);
                    show.setArguments(arguments);
                    return show;
                } else {
                    show = new MainProductFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt(MainProductFragment.MAIN_PRODUCT_FRAGMENT_KEY, position);
                    arguments.putString("imgPath", imagePath);
                    arguments.putString("name", name);
                    arguments.putString("price", price);
                    arguments.putInt("sellerId", sellerId);
                    arguments.putInt("productId", id);
                    show.setArguments(arguments);
                    return show;
                }
            }

            if(position == 1) {
                if(sellerId == user.getmId()) {
                    show = new SecondOwnProductFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt(SecondOwnProductFragment.SECOND_OWN_PRODUCT_FRAGMENT_KEY, position);
                    arguments.putString("description", description);
                    arguments.putString("seller", seller);
                    show.setArguments(arguments);
                    return show;
                } else {
                    show = new SecondProductFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt(SecondProductFragment.SECOND_PRODUCT_FRAGMENT_KEY, position);
                    arguments.putString("description", description);
                    arguments.putString("seller", seller);
                    arguments.putInt("quantity", quantity);
                    arguments.putInt("productId", id);
                    show.setArguments(arguments);
                    return show;
                }
            }

            if(position == 2) {
                show = new ProductSellerFragment();
                Bundle arguments = new Bundle();
                arguments.putInt(ProductSellerFragment.PRODUCT_SELLER_FRAGMENT_KEY, position);
                arguments.putString("seller", seller);
                arguments.putString("sellerAddress", sellerAddress);
                arguments.putDouble("sellerRating", sellerRating);
                arguments.putInt("sellerId", sellerId);
                show.setArguments(arguments);
                return show;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Product info";
            } else if (position == 1) {
                return "Product description";
            } else {
                return "Sellers info";
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(MainActivity.getmSharedPreferences().getString(
                getString(R.string.key_user_email),
                null
        ) != null){
            getMenuInflater().inflate(R.menu.menu_show_product, menu);
            return true;
        } else {
            return false;
        }
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
            Intent toLogin = new Intent( FragmentedProductActivity.this, MainActivity.class);
            startActivity(toLogin);
            return true;
        }

        if (id == R.id.profile_action) {
            user = ProfileActivity.getCurrentUser();
            moveTaskToBack(true);
            Intent intent = new Intent(FragmentedProductActivity.this, ProfileActivity.class);
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
                Intent toCart = new Intent(FragmentedProductActivity.this, CartActivity.class);
                toCart.putExtra("jsonProducts", responseJson);
                startActivity(toCart);
            }
        };

    }

}
