package com.example.tirthshah.volunteerifest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class CheckRegistration extends AppCompatActivity {
    Button checkReg;
    EditText pin;
    AsyncHttpClient a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_registration);
        checkReg= (Button) findViewById(R.id.checkPIN);
        pin= (EditText) findViewById(R.id.registrationID);
        checkReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check database
                String regId=pin.getText().toString();
                a=new AsyncHttpClient();
                a.setBasicAuth(Constants.user,Constants.password);
                a.get(Constants.getURL() + "participant/" + pin.getText().toString(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Intent i=new Intent(CheckRegistration.this,ShowRegistration.class);
                        i.putExtra("response",responseBody);
                        i.putExtra("id",Integer.parseInt(pin.getText().toString()));
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });
    }
}
