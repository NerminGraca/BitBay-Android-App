package com.example.nermingraca.bitbayapp;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by nermingraca on 13/04/15.
 */
public class PostRequest {

    private static CookieManager ckm = new CookieManager();

    public static void post(String url,
                            String jsonObject,
                            Callback callback){

        MediaType JSON = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonObject);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        ckm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(ckm);

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
