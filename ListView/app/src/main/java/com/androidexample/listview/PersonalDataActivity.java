package com.androidexample.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PersonalDataActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        Intent intent = getIntent();

        Person person1 = (Person)intent.getSerializableExtra("person1");

        ((TextView)findViewById(R.id.textName)).setText(person1.getName());
        ((TextView)findViewById(R.id.textAge)).setText(person1.getAge());
        ((TextView)findViewById(R.id.textCompany)).setText(person1.getCompany());
        ((TextView)findViewById(R.id.textEmail)).setText(person1.getEmail());
        ((TextView)findViewById(R.id.textJobTitle)).setText(person1.getJobTitle());
        ((TextView)findViewById(R.id.textLastName)).setText(person1.getLastName());
    }
}
