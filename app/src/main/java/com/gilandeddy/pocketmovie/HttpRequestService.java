package com.gilandeddy.pocketmovie;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gilbert on 5/12/18.
 */

public class HttpRequestService extends IntentService {
    static String EXTRA_URLSTRING = "com.gilandeddy.123456";

    public HttpRequestService() {
        super("HttpRequestService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        final String url = intent.getStringExtra(EXTRA_URLSTRING);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseString = new String(response.body().string());
            Intent completeIntent = new Intent("httpRequestComplete");
            completeIntent.putExtra("responseString",responseString);
            sendBroadcast(completeIntent);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void startActionRequestHttp(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra(EXTRA_URLSTRING, url);
        context.startService(intent);


    }
}