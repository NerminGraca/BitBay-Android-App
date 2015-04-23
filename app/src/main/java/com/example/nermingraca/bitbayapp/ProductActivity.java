package com.example.nermingraca.bitbayapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nermingraca.bitbayapp.models.User;
import com.example.nermingraca.bitbayapp.service.ServiceRequest;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class ProductActivity extends ActionBarActivity {

    private Button mViewUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String imagePath = intent.getStringExtra("imagePath");
        String seller = intent.getStringExtra("seller");
        final int sellerId = intent.getIntExtra("sellerId", 0);

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

        mViewUserButton = (Button) findViewById(R.id.view_user_button);
        mViewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = String.format("http://10.0.2.2:9000/api/showuser/%d", sellerId);
                Log.e("RESPONSE", url);
                Callback callback = parseResponse();
                ServiceRequest.get(url, callback);

            }
        });
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

                    Intent goToSeller = new Intent(ProductActivity.this, SellerActivity.class);
                    goToSeller.putExtra("username", username);
                    goToSeller.putExtra("email", email);

                    startActivity(goToSeller);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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

        return super.onOptionsItemSelected(item);
    }
}
