package com.androidexample.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PersonalDataActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        Intent intent = getIntent();

        String message = intent.getStringExtra("message");
        ((TextView)findViewById(R.id.textView)).setText(message);

    }
}
