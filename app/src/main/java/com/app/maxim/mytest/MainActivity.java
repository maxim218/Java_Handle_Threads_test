package com.app.maxim.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private final QuerySender.OnRequestCompleteListener listener = new QuerySender.OnRequestCompleteListener() {
        @Override
        public void onRequestComplete(final String body) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("Message", ":::::::: Get answer OK :::::::::::");
                    TextView field = (TextView) findViewById(R.id.textview_1);
                    field.setText(body);
                }
            });
        }
    };

    // событие при нажатии на кнопку
    public void buttonFirstClick(View view) {
        // отправляем запрос на сервер
        QuerySender.getInstance().get("https://api-news-nin1.herokuapp.com/", listener);
    }

}
