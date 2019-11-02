package com.example.tirthshah.volunteerifest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Details extends AppCompatActivity {
    EditText name, email, phone, college, daiictID, IEEENumber;
    Switch IEEESwitch;
    String passwordGenerated;
    Button register;
    AsyncHttpClient a;
    String id;
    int newId;
    boolean btnlock = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        IEEESwitch = (Switch) findViewById(R.id.IEEESwitch);
        IEEENumber = (EditText) findViewById(R.id.IEEENumber);
        daiictID = (EditText) findViewById(R.id.daiictID);
        register = (Button) findViewById(R.id.register);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        college = (EditText) findViewById(R.id.college);
        IEEESwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IEEENumber.setEnabled(true);
                } else {
                    IEEENumber.setEnabled(false);
                }
            }
        });
        a = new AsyncHttpClient();
        a.setBasicAuth(Constants.user, Constants.password);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!btnlock) {
                    btnlock = true;
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            Details.this);

// Setting Dialog Title
                    alertDialog2.setTitle("Confirm New Participant...");

// Setting Dialog Message
                    alertDialog2.setMessage("Complete New Participant Details?");


// Setting Positive "Yes" Btn
                    alertDialog2.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (isOnline()) {
                                        final JSONObject participant = new JSONObject();
                                        a.get(Constants.getURL() + "count/1", new AsyncHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                try {
                                                    newId = new JSONObject(new String(responseBody)).getJSONObject("_source").getInt("count");
                                                    Log.d("new Id", newId + "");
                                                    try {

                                                        Calendar c = Calendar.getInstance();
                                                        System.out.println("Current time => " + c.getTime());

                                                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                        final String formattedDate = df.format(c.getTime());
                                                        participant.put("date",formattedDate);
                                                        participant.put("name", name.getText().toString());
                                                        participant.put("email", email.getText().toString());
                                                        participant.put("phone", 0+phone.getText().toString());
                                                        participant.put("college", college.getText().toString());
                                                        participant.put("volunteer", id);
                                                        passwordGenerated=generatePassword();
                                                        participant.put("password",passwordGenerated);
                                                        if (IEEESwitch.isChecked()) {
                                                            participant.put("IEEEMember", true);
                                                            participant.put("IEEENumber", IEEENumber.getText().toString());
                                                        } else {
                                                            participant.put("IEEEMember", false);
                                                        }
                                                        if (!daiictID.getText().toString().equals("")) {
                                                            participant.put("daiictID", daiictID.getText().toString());
                                                        }

                                                        try {
                                                            a.post(getApplicationContext(), Constants.URL + "/" + Constants.app + "/count/1", new StringEntity("{\"count\":"+(newId+1)+"}"), "application/json", new AsyncHttpResponseHandler() {
                                                                @Override
                                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                                                }

                                                                @Override
                                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                                                }
                                                            });
                                                        } catch (UnsupportedEncodingException e) {
                                                            e.printStackTrace();
                                                        }
                                                        a.put(getApplicationContext(), Constants.URL + "/" + Constants.app + "/participant/" + newId, new StringEntity(participant.toString()), "application/json", new AsyncHttpResponseHandler() {
                                                            @Override
                                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                                                                Toast.makeText(getApplicationContext(), "Participant ID " + newId, Toast.LENGTH_LONG).show();
                                                                Log.d("id", id);
                                                                a.get(Constants.getURL() + "volunteer/" + id, new AsyncHttpResponseHandler() {
                                                                    @Override
                                                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                                        try {

                                                                            Log.d("Response volunteer", new String(responseBody));
                                                                            JSONObject count = new JSONObject(new String(responseBody));
                                                                            count = count.getJSONObject("_source");
                                                                            int num = count.getInt("registrations");
                                                                            int collected;
                                                                            if (count.has("collected")) {
                                                                                collected = count.getInt("collected");

                                                                                count.remove("collected");
                                                                            } else {
                                                                                collected = 0;
                                                                            }
                                                                            if (IEEESwitch.isChecked()) {
                                                                                collected += Constants.IEEEfee;
                                                                            } else {
                                                                                collected += Constants.NonIEEEfee;
                                                                            }
                                                                            Log.d("count", collected + "");
                                                                            num++;
                                                                            count.remove("registrations");
                                                                            count.put("collected", collected);
                                                                            Toast.makeText(getApplicationContext(), num + " registrations " + id, Toast.LENGTH_SHORT).show();
                                                                            count.put("registrations", num);
                                                                            a.put(getApplicationContext(), Constants.URL + "/" + Constants.app + "/volunteer/" + id, new StringEntity(count.toString()), "application/json", new AsyncHttpResponseHandler() {
                                                                                @Override
                                                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                                                    String mail = "Hello " + name.getText() + ",\nWe are pleased to inform you that you have successfully registered for i.Fest'16.\n" +
                                                                                            "Please Login to our website and register for events of your interest.\n\nWebsite : ieee.daiict.ac.in/ifest\n\n" +
                                                                                            "Registration ID : " + newId + "\n" +
                                                                                            "Your Password is "+passwordGenerated +"\n\n"+
                                                                                            "Your Registration Details are as follows\n" +
                                                                                            "Name : " + name.getText().toString() + "\n" +
                                                                                            "Email : " + email.getText().toString() + "\n" +
                                                                                            "Phone Number : 0" + phone.getText().toString() + "\n" +
                                                                                            "College : " + college.getText().toString() + "\n" +
                                                                                            "Date : " +formattedDate+"\n"+
                                                                                            "Collected by : "+id +"\n";


                                                                                    if (IEEESwitch.isChecked()) {
                                                                                        mail += "IEEE Member : Yes\nIEEE Number : " + IEEENumber.getText().toString() + "\n";
                                                                                    } else {
                                                                                        mail += "IEEEMember : No\n";
                                                                                    }
                                                                                    if (!daiictID.getText().toString().equals("")) {
                                                                                        mail +="DAIICT ID : "+ daiictID.getText().toString() + "\n";
                                                                                    }
                                                                                    mail+="For any queries contact us.\n" +
                                                                                            "ifest2016daiict@gmail.com\n\n\n\n" +
                                                                                            "Greetings,\n" +
                                                                                            "Team i.Fest'16";
                                                                                    //Creating SendMail object
                                                                                    SendMail sm = new SendMail(Details.this, email.getText().toString(), "Registered for i.Fest'16", mail);
                                                                                    sm.execute();


                                                                                    Log.d("response body", new String(responseBody));
                                                                                    Intent i = new Intent(Details.this, ShowRegistration.class);

                                                                                    i.putExtra("response", participant.toString().getBytes());
                                                                                    i.putExtra("id", newId);
                                                                                    setBlank();
                                                                                    btnlock = false;
                                                                                    startActivity(i);

                                                                                }

                                                                                @Override
                                                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                                                    Toast.makeText(getApplicationContext(), "Failure not added in volunteer", Toast.LENGTH_SHORT).show();
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
                                                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                                                        btnlock = false;
                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                                                Toast.makeText(getApplicationContext(), "Failure participant", Toast.LENGTH_SHORT).show();
                                                                btnlock = false;
                                                            }
                                                        });
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    } catch (UnsupportedEncodingException e) {
                                                        e.printStackTrace();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                Toast.makeText(getApplicationContext(), "Could not connect", Toast.LENGTH_SHORT).show();
                                                btnlock = false;
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
                                        btnlock = false;
                                    }

                                }
                            });

// Setting Negative "NO" Btn
                    alertDialog2.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    btnlock = false;
                                    dialog.cancel();
                                }
                            });

// Showing Alert Dialog
                    alertDialog2.show();

                }
            }
        });


    }

    private String generatePassword() {
        Random r=new Random();
        int i=r.nextInt(899998)+100000;
        return i+"";
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void setBlank() {
        name.setText("");
        email.setText("");
        phone.setText("");
        college.setText("");
        IEEENumber.setText("");
        daiictID.setText("");
        IEEESwitch.setChecked(false);
        IEEENumber.setEnabled(false);
    }
}
