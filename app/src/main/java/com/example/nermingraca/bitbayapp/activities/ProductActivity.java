package com.example.nermingraca.bitbayapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nermingraca.bitbayapp.R;
import com.example.nermingraca.bitbayapp.models.User;
import com.example.nermingraca.bitbayapp.service.ServiceRequest;
import com.example.nermingraca.bitbayapp.singletons.UserData;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        //int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String imagePath = intent.getStringExtra("imagePath");
        String seller = intent.getStringExtra("seller");
        final int sellerId = intent.getIntExtra("sellerId", 0);
        String sId = String.valueOf(sellerId);
        Log.i("RESPONSE", sId);

        TextView productName = (TextView) findViewById(R.id.productName);
        TextView productDesc = (TextView) findViewById(R.id.productDesc);
        TextView productPrice = (TextView) findViewById(R.id.productPrice);
        ImageView productImage = (ImageView) findViewById(R.id.productImage);
        TextView productSeller = (TextView) findViewById(R.id.productSeller);
        productName.setText(name);
        productDesc.setText(description);
        productPrice.setText(price);
        Picasso.with(getBaseContext()).load(imagePath).into(productImage);
        productSeller.setText(seller);

        Button mViewUserButton = (Button) findViewById(R.id.view_user_button);
        mViewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resUrl = getString(R.string.service_user);
                String url = String.format(resUrl, sellerId);
                Log.i("RESPONSE", url);
                Callback callback = parseResponse();
                ServiceRequest.get(url, callback);

            }
        });
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

                    Intent goToSeller = new Intent(ProductActivity.this, SellerActivity.class);
                    goToSeller.putExtra("username", username);
                    goToSeller.putExtra("email", email);

                    startActivity(goToSeller);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", e.getMessage());
                }

            }
        };

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
            Intent toLogin = new Intent( ProductActivity.this, MainActivity.class);
            startActivity(toLogin);
            return true;
        }

        if (id == R.id.profile_action) {
            User user = ProfileActivity.getCurrentUser();
            moveTaskToBack(true);
            Intent intent = new Intent(ProductActivity.this, ProfileActivity.class);
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
                Intent toCart = new Intent(ProductActivity.this, CartActivity.class);
                toCart.putExtra("jsonProducts", responseJson);
                startActivity(toCart);
            }
        };

    }
}
