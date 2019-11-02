package com.example.tirthshah.volunteerifest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowCollections extends AppCompatActivity {
    TextView idView,registrationView,collectedView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_collections);
        Intent i=getIntent();
        idView= (TextView) findViewById(R.id.id);
        registrationView= (TextView) findViewById(R.id.registrations);
        collectedView= (TextView) findViewById(R.id.collected);
        int registrations=i.getIntExtra("registrations",0);
        int collected=i.getIntExtra("collected",0);
        String id=i.getStringExtra("id");
        idView.setText(id+"");
        registrationView.setText(registrations+"");
        collectedView.setText(collected+"");
    }
}
