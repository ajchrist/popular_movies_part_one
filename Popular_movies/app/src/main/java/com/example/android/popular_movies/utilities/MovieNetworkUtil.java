package com.example.android.popular_movies.utilities;

// adapted from NetworkUtil.java from T05b.03-exercise-PolishAsyncTask
// from the toybox in stage 1

import android.content.Context;
import android.net.Uri;

import com.example.android.popular_movies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MovieNetworkUtil {

    private final static String POPULAR_URL = "http://api.themoviedb.org/3/movie/popular?api_key=<API_KEY>";
    private final static String TOP_RATED_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=<API_KEY>";

    public static JSONArray query(String setting, Context context) throws IOException, JSONException {
        URL url = null;
        switch (setting){
            case "popular":
                url = new URL(Uri.parse(POPULAR_URL).buildUpon().build().toString());
                break;
            case "top_rated":
                url = new URL(Uri.parse(TOP_RATED_URL).buildUpon().build().toString());
                break;
        }
        if (url != null) {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    return new JSONObject(scanner.next()).getJSONArray(context.getString(R.string.results));
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}
