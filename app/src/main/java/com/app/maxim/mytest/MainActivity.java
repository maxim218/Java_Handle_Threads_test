package com.app.maxim.mytest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonFirstClick(View view) {
        final TextView field = (TextView) findViewById(R.id.textview_1);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                String s = field.getText().toString();
                s += " X ";
                field.setText(s);
            }
        });
    }
}
