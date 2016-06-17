package com.androidexample.listview;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class ListViewAndroidExample extends Activity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_android_example);

        listView = (ListView) findViewById(R.id.list);
        String[] values = new String[]{"Artak Vardanyan", "Tigran Levonyan", "Armen Gyozalyan",
                "Vahan Levonyan", "Naira Khachatryan", "Sargis Kocharyan", "Hamlet Grigoryan", "Marat Mirzoyan"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                String resourceName = itemValue.replaceAll(" ", "_").toLowerCase();

                Resources res = getResources();

                int personResourceId = res.getIdentifier(resourceName, "array", getPackageName());
                String[] personArray = res.getStringArray(personResourceId);
                final Person person = new Person(personArray);

                Intent intent = new Intent(ListViewAndroidExample.this, PersonalDataActivity.class);
                intent.putExtra("person1", person);
                startActivity(intent);

            }


        });
    }

}
