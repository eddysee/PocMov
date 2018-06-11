package com.gilandeddy.pocketmovie.model;

import android.app.IntentService;
import android.content.ContentValues;
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
        final String filter = intent.getStringExtra("filter");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseString = new String(response.body().string());
            if (filter.equalsIgnoreCase("popular")) {
                Intent completeIntent = new Intent("httpRequestComplete");
                completeIntent.putExtra("responseString", responseString);
                sendBroadcast(completeIntent);
            } else if (filter.equalsIgnoreCase("detail")) {
                Intent completeIntent = new Intent("detailRequestComplete");
                completeIntent.putExtra("responseString", responseString);
                sendBroadcast(completeIntent);
            } else if (filter.equalsIgnoreCase("trailer")) {
                Intent completeIntent = new Intent("trailerRequestComplete");
                completeIntent.putExtra("responseString", responseString);
                sendBroadcast(completeIntent);
            } else if (filter.equalsIgnoreCase("search")) {
                Intent completeIntent = new Intent("searchRequestComplete");
                completeIntent.putExtra("responseString", responseString);
                sendBroadcast(completeIntent);
            }


        } catch (IOException e) {
            Intent completeIntent = new Intent("failedHttpRequest");
            sendBroadcast(completeIntent);
            e.printStackTrace();
        }


    }


    public static void startActionRequestHttp(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra("filter", "popular");
        intent.putExtra(EXTRA_URLSTRING, url);
        context.startService(intent);

    }

    public static void startMovieDetailRequest(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra("filter", "detail");
        intent.putExtra(EXTRA_URLSTRING, url);
        context.startService(intent);

    }

    public static void startTrailerPathRequest(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra("filter", "trailer");
        intent.putExtra(EXTRA_URLSTRING, url);
        context.startService(intent);

    }

    public static void startSearchRequest(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra("filter", "search");
        intent.putExtra(EXTRA_URLSTRING, url);
        context.startService(intent);
    }
}