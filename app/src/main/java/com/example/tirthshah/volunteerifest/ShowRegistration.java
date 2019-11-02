package com.example.tirthshah.volunteerifest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ShowRegistration extends AppCompatActivity {
    TextView nameView, collegeView, phoneView, emailView,idView,daiictIDView,IEEEMemberView,IEEENumberView,dateView,collectedByView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_registration);

        idView= (TextView) findViewById(R.id.id1);
        nameView = (TextView) findViewById(R.id.name1);
        collegeView = (TextView) findViewById(R.id.college1);
        phoneView = (TextView) findViewById(R.id.phone1);
        emailView = (TextView) findViewById(R.id.email1);
        IEEEMemberView= (TextView) findViewById(R.id.IEEEMember1);
        IEEENumberView= (TextView) findViewById(R.id.IEEENumber1);
        daiictIDView= (TextView) findViewById(R.id.daiictID1);
        dateView= (TextView) findViewById(R.id.date1);
        collectedByView= (TextView) findViewById(R.id.collectedBy1);
        Intent i = getIntent();
        String response = new String(i.getByteArrayExtra("response"));
        try {
            JSONObject participant = new JSONObject(response);
            if (participant.has("_source"))
                participant = participant.getJSONObject("_source");
            String name = participant.getString("name");
            String college = participant.getString("college");
            String phone = participant.getString("phone");
            String email = participant.getString("email");
            String collectedBy=participant.getString("volunteer");
            String date=participant.getString("date");
            boolean IEEEMember=participant.getBoolean("IEEEMember");

            if(IEEEMember){
                String IEEENumber=participant.getString("IEEENumber");
                IEEEMemberView.setText("YES");
                IEEENumberView.setText(IEEENumber);
            }else{
                IEEEMemberView.setText("NO");
                IEEENumberView.setText("-");
            }
            if(participant.has("daiictID")){
                String daiictID=participant.getString("daiictID");
                daiictIDView.setText(daiictID);
            }else{
                daiictIDView.setText("-");
            }
            String id=i.getIntExtra("id",0)+"";
            idView.setText(id);
            nameView.setText(name);
            collegeView.setText(college);
            emailView.setText(email);
            phoneView.setText(phone);
            dateView.setText(date);
            collectedByView.setText(collectedBy);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_SHORT);
        }

    }
}
