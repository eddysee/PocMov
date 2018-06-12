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
 * @author Gilbert & Eddy
 * This class HttpRequestService handles the Http Requests that are made in different activities
 * in the app. It handles the Http Requests from 'The Movie Data Base' TMDB:
 * Http Requests for
 * - Popular Movies
 * - Details
 * - Trailer
 * - Movie Search
 */

public class HttpRequestService extends IntentService {
    static String EXTRA_URLSTRING = "com.gilandeddy.123456";

    /** This class HttpReciever which extends BreadcastReciever allows to register for system events
     *
     */
    public HttpRequestService() {
        super("HttpRequestService");
    }


    /**
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        final String url = intent.getStringExtra(EXTRA_URLSTRING);
        final String filter = intent.getStringExtra("filter");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        /**
         *This block of if statements reads the filter that was added to the incoming intent and
         * broadcasts the response to the request with the appropriate action.
         */
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

    /** The method startActionRequestHttp takes a context and url as params and launches an
     * http request for popular movies to the url.
     *
     * @param context
     * @param url
     */
    public static void startActionRequestHttp(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra("filter", "popular");
        intent.putExtra(EXTRA_URLSTRING, url);
        context.startService(intent);

    }

    /**  The method startMovieDetailsRequest takes a context and url as params and launches an
     * http request for movie details to the url.
     *
     * @param context
     * @param url
     */

    public static void startMovieDetailRequest(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra("filter", "detail");
        intent.putExtra(EXTRA_URLSTRING, url);
        context.startService(intent);

    }

    /**  The method startMovieTrailerPathRequest takes a context and url as params and launches an
     * http request for movie videos to the url.
     *
     * @param context
     * @param url
     */

    public static void startTrailerPathRequest(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra("filter", "trailer");
        intent.putExtra(EXTRA_URLSTRING, url);
        context.startService(intent);

    }

    /**  The method startSearchRequest takes a context and url as params and launches an
     * http request for movies to the url.
     *
     * Not in use as Search function is still unfinished.
     *
     * @param context
     * @param url
     */

    public static void startSearchRequest(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra("filter", "search");
        intent.putExtra(EXTRA_URLSTRING, url);
        context.startService(intent);
    }
}