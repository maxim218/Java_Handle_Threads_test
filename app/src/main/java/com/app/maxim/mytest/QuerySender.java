package com.app.maxim.mytest;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

// класс для отправки запросов на сервер
public class QuerySender {
    // экземпляр данного класса
    private static final QuerySender INSTANCE = new QuerySender();

    // метод для возврата экземпляра данного класса
    public static QuerySender getInstance() {
        // возврат экземпляра данного класса
        return INSTANCE;
    }

    // конструктор
    private QuerySender() {
        // выводим информацию о срабатывании конструктора
        Log.i("Creating object", "----- Create new QuerySender -----");
    }

    // объект для работы с интернетом
    private final OkHttpClient client = new OkHttpClient();
    // объект для запуска
    private final Executor executor = Executors.newSingleThreadExecutor();

    // метод отправки запроса на сервер
    public void get(final String url, final OnRequestCompleteListener listener) {
        // создаём объект: запрос
        final Request request = new Request.Builder().url(url).build();
        // добавляем слушатель к объекту запроса
        performRequest(request, listener);
    }

    private void performRequest(final Request request, final OnRequestCompleteListener listener) {
        // выполняем запрос
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final String body = getBodyContent(request);
                listener.onRequestComplete(body);
            }
        });
    }

    // метод для получения тела ответа от сервера
    private String getBodyContent(final Request request) {
        try {
            // если во время работы НЕ было ошибок
            // получаем объект: ответ от сервера
            final Response response = client.newCall(request).execute();
            try (ResponseBody body = response.body()) {
                if (response.isSuccessful() && body != null) {
                    // возвращаем ответ от сервера в виде строки
                    return body.string();
                }
            }
        } catch (IOException e) {
            // если произошла ошибка во время работы, то возвращаем строку __ERROR__
            return "__ERROR__";
        }

        // если произошла ошибка во время работы, то возвращаем строку __ERROR__
        return "__ERROR__";
    }

    public interface OnRequestCompleteListener {
        void onRequestComplete(final String body);
    }
}
