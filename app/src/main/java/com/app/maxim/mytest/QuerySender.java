package com.app.maxim.mytest;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class QuerySender {
    private static final QuerySender INSTANCE = new QuerySender();

    public static QuerySender getInstance() {
        return INSTANCE;
    }

    private QuerySender() {
        Log.i("Creating object", "----- Create new QuerySender -----");
    }

    private final OkHttpClient client = new OkHttpClient();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public void get(final String url, final OnRequestCompleteListener listener) {
        final Request request = new Request.Builder().url(url).build();
        performRequest(request, listener);
    }

    private void performRequest(final Request request, final OnRequestCompleteListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final String body = getBodyContent(request);
                listener.onRequestComplete(body);
            }
        });
    }

    private String getBodyContent(final Request request) {
        try {
            final Response response = client.newCall(request).execute();
            try (ResponseBody body = response.body()) {
                if (response.isSuccessful() && body != null) {
                    return body.string();
                }
            }
        } catch (IOException e) {
            return "__ERROR__";
        }

        return "__ERROR__";
    }

    public interface OnRequestCompleteListener {
        void onRequestComplete(final String body);
    }
}
