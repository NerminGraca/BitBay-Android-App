package com.example.nermingraca.bitbayapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.nermingraca.bitbayapp.R;
import com.example.nermingraca.bitbayapp.models.User;
import com.example.nermingraca.bitbayapp.service.ServiceRequest;
import com.example.nermingraca.bitbayapp.singletons.UserData;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CheckoutWebViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_web_view);

        final WebView myWebView = (WebView) findViewById(R.id.webview);
        User user = ProfileActivity.getCurrentUser();
        String url = getString(R.string.service_cart_checkout) + String.valueOf(user.getmId());
        myWebView.loadUrl(url);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("backToApp")) {
                    Intent i = new Intent(CheckoutWebViewActivity.this, ProductsActivity.class);
                    startActivity(i);
                    return false;
                }
                myWebView.loadUrl(url);
                return true;
            }
        });

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
            Intent toLogin = new Intent(CheckoutWebViewActivity.this, MainActivity.class);
            startActivity(toLogin);
            return true;
        }

        if (id == R.id.profile_action) {
            User user = ProfileActivity.getCurrentUser();
            moveTaskToBack(true);
            Intent intent = new Intent(CheckoutWebViewActivity.this, ProfileActivity.class);
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
                Intent toCart = new Intent(CheckoutWebViewActivity.this, CartActivity.class);
                toCart.putExtra("jsonProducts", responseJson);
                startActivity(toCart);
            }
        };

    }

}
