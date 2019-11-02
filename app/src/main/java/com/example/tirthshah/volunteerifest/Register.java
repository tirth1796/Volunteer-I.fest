package com.example.tirthshah.volunteerifest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Register extends AppCompatActivity {
    Button login, checkCollection,cancelButton;
    EditText id, pass;
    AsyncHttpClient asyncHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setBasicAuth(Constants.user, Constants.password);

        setContentView(R.layout.activity_register);
        login = (Button) findViewById(R.id.loginRegister);
        id = (EditText) findViewById(R.id.studentID);
        pass = (EditText) findViewById(R.id.password);
        checkCollection= (Button) findViewById(R.id.collection);
        cancelButton= (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String checkID = id.getText().toString();
                final String checkPass = pass.getText().toString();
                Log.d("url", Constants.URL + "/" + Constants.app + "/" + "volunteer/" + checkID);
                asyncHttpClient.get(Constants.URL + "/" + Constants.app + "/" + "volunteer/" + checkID, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            Log.d("response", new String(responseBody));
                            JSONObject json = new JSONObject(new String(responseBody));
                            if (json.has("found")) {
                                if (json.getBoolean("found")) {
//                                    RestAPI send = new RestAPI(checkID, checkPass, getApplicationContext());
//                                    boolean x = send.logIn();
//                                    if(x) {
//                                        Intent i = new Intent(Register.this, Details.class);
//                                        i.putExtra("id",checkID);
//                                        startActivity(i);
//                                    }
                                    String jsonPass = json.getJSONObject("_source").getString("password");
                                    if (jsonPass.equals(checkPass)) {
                                        String jsonID = checkID;
                                        Intent i = new Intent(Register.this, Cancellation.class);
                                        i.putExtra("id", jsonID);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        checkCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String checkID = id.getText().toString();
                final String checkPass = pass.getText().toString();
                Log.d("url", Constants.URL + "/" + Constants.app + "/" + "volunteer/" + checkID);
                asyncHttpClient.get(Constants.URL + "/" + Constants.app + "/" + "volunteer/" + checkID, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            Log.d("response", new String(responseBody));
                            JSONObject json = new JSONObject(new String(responseBody));
                            if (json.has("found")) {
                                if (json.getBoolean("found")) {
//                                    RestAPI send = new RestAPI(checkID, checkPass, getApplicationContext());
//                                    boolean x = send.logIn();
//                                    if(x) {
//                                        Intent i = new Intent(Register.this, Details.class);
//                                        i.putExtra("id",checkID);
//                                        startActivity(i);
//                                    }
                                    json=json.getJSONObject("_source");
                                    String jsonPass = json.getString("password");
                                    if (jsonPass.equals(checkPass)) {

                                        int collected, registrations;
                                        if (json.has("collected")) {
                                            collected = json.getInt("collected");
                                        } else
                                            collected = 0;
                                        if (json.has("registrations")) {
                                            registrations = json.getInt("registrations");
                                        } else
                                            registrations = 0;
                                        String jsonID = checkID;
                                        Log.d("count collect",collected+"");
                                        Log.d("count registrations",registrations+"");
                                        Intent i = new Intent(Register.this, ShowCollections.class);
                                        i.putExtra("id", jsonID);
                                        i.putExtra("registrations",registrations);
                                        i.putExtra("collected",collected);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String checkID = id.getText().toString();
                final String checkPass = pass.getText().toString();
                Log.d("url", Constants.URL + "/" + Constants.app + "/" + "volunteer/" + checkID);
                asyncHttpClient.get(Constants.URL + "/" + Constants.app + "/" + "volunteer/" + checkID, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            Log.d("response", new String(responseBody));
                            JSONObject json = new JSONObject(new String(responseBody));
                            if (json.has("found")) {
                                if (json.getBoolean("found")) {
//                                    RestAPI send = new RestAPI(checkID, checkPass, getApplicationContext());
//                                    boolean x = send.logIn();
//                                    if(x) {
//                                        Intent i = new Intent(Register.this, Details.class);
//                                        i.putExtra("id",checkID);
//                                        startActivity(i);
//                                    }
                                    String jsonPass = json.getJSONObject("_source").getString("password");
                                    if (jsonPass.equals(checkPass)) {
                                        String jsonID = checkID;
                                        Intent i = new Intent(Register.this, Details.class);
                                        i.putExtra("id", jsonID);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
