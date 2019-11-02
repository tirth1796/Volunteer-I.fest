package com.example.tirthshah.volunteerifest;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Cancellation extends AppCompatActivity {
    String id;
    Button checkReg;
    EditText pin;
    AsyncHttpClient a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation);

        Intent i = getIntent();
        id = i.getStringExtra("id");

        checkReg= (Button) findViewById(R.id.deletePIN);
        pin= (EditText) findViewById(R.id.registrationID);
        checkReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check database
                String regId=pin.getText().toString();
                a=new AsyncHttpClient();
                a.setBasicAuth(Constants.user,Constants.password);

                a.delete(Constants.getURL() + "participant/" + pin.getText().toString(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        a.get(Constants.getURL() + "volunteer/" + id, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                try {
                                    JSONObject json=new JSONObject(new String(responseBody)).getJSONObject("_source");
                                    if(json.has("cancellation")){
                                        int can=json.getInt("cancellation");
                                        json.remove("cancellation");
                                        json.put("cancellation",++can);


                                    }else{
                                        json.put("cancellation",1);
                                    }
                                    a.put(getApplicationContext(), Constants.getURL() + "volunteer/" + id, new StringEntity(json.toString()), "application/json", new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            }
                        });
                        Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });
    }

}
