package com.example.nayeemhasan.smartticket;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BusSeatActivity extends AppCompatActivity {

    TextView busNameText, busTimeText, busFareText;
    public CheckBox[][] checkBox;
    int ticketCnt;

    private ArrayList<String> checkedSeats;

    RequestQueue requestQueue;

    BusSeatTask busSeatTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_seat);

        busNameText = (TextView) findViewById(R.id.textView10);
        busTimeText = (TextView) findViewById(R.id.busTime);
        busFareText = (TextView) findViewById(R.id.busFare);

        final Intent intent = getIntent();
        final String userName = intent.getStringExtra("user_Name");
        final String date = intent.getStringExtra("date");
        final String time = intent.getStringExtra("bus_Time");
        final String routeId = intent.getStringExtra("route_id");
        int route_id = Integer.parseInt(routeId);

        busNameText.setText(intent.getStringExtra("bus_Name"));
        busTimeText.setText(intent.getStringExtra("bus_Time"));
        final String busFare = intent.getStringExtra("bus_Fare");
        busFareText.setText("Fare: " + intent.getStringExtra("bus_Fare"));

        checkBox = new CheckBox[9][5];

        for (int i=1; i<=8; i++){
            for (int j=1; j<=4; j++){
                if (i==1){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.A1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.A2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.A3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.A4);
                }

                else if (i==2){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.B1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.B2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.B3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.B4);
                }

                else if (i==3){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.C1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.C2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.C3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.C4);
                }

                else if (i==4){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.D1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.D2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.D3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.D4);
                }

                else if (i==5){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.E1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.E2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.E3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.E4);
                }

                else if (i==6){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.F1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.F2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.F3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.F4);
                }

                else if (i==7){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.G1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.G2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.G3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.G4);
                }

                else if (i==8){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.H1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.H2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.H3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.H4);
                }
            }
        }
        checkedSeats = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        busSeatTask = new BusSeatTask();
        busSeatTask.execute(routeId, date, time);

        final ArrayList<String> tickets = new ArrayList<>();
        ticketCnt = 0;
        for (int i=1; i<=8; i++) {
            for (int j = 1; j <= 4; j++) {
                final int a = i;
                final int b = j;
                checkBox[i][j].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true ) {
                            ticketCnt++;
                            tickets.add(checkBox[a][b].getText().toString());
                        }
                        else {
                            ticketCnt--;
                            tickets.remove(checkBox[a][b].getText().toString());
                        }
                    }
                });
            }
        }

        Button seatSubmit = (Button) findViewById(R.id.SeatSubmit);

        seatSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ticketCnt >0 && ticketCnt <=4) {
                    Intent intent1 = new Intent(BusSeatActivity.this, PaymentActivity.class);
                    intent1.putExtra("bus_Name", busNameText.getText().toString());
                    intent1.putExtra("ticket_Fare", busFare);
                    intent1.putExtra("ticket_Cnt", String.valueOf(ticketCnt));

                    intent1.putExtra("user_Name",userName);
                    intent1.putExtra("date",date);
                    intent1.putExtra("time",time);
                    intent1.putExtra("route_id",routeId);

                    for(int i=1; i<= ticketCnt; i++){
                        if (i==1) intent1.putExtra("t1",tickets.get(0).toString());
                        else if (i==2) intent1.putExtra("t2",tickets.get(1).toString());
                        else if (i==3) intent1.putExtra("t3",tickets.get(2).toString());
                        else if (i==4) intent1.putExtra("t4",tickets.get(3).toString());
                    }
                    startActivity(intent1);
                }
                else if (ticketCnt == 0){
                    Toast.makeText(BusSeatActivity.this, "Select at least one ticket", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(BusSeatActivity.this, "Select at most 4 tickets", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleCheckedSeats(){
        for (int i=1; i<=8; i++) {
            for (int j = 1; j <= 4; j++) {
                for (int k=0; k < checkedSeats.size(); k++) {
                    if (checkBox[i][j].getText().toString().matches(checkedSeats.get(k))) {
                        checkBox[i][j].setBackgroundColor(Color.LTGRAY);
                        checkBox[i][j].setClickable(false);
                    }
                }
            }
        }
    }


    private class BusSeatTask extends AsyncTask<String,Void,Void>
    {
        @Override
        protected Void doInBackground(String... params) {
            final String routeId_ = params[0];
            final String date_ = params[1];
            final String time_ = params[2];

            StringRequest jor = new StringRequest(Request.Method.POST,
                    "http://192.168.43.94:1234/SmartTicket/viewCheckedSeats.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                checkedSeats.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject j = jsonArray.getJSONObject(i);
                                    checkedSeats.add(j.getString("seat_no"));
                                }
                                handleCheckedSeats();
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(BusSeatActivity.this, "Unknown Error" , Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> p = new HashMap<>();
                    p.put("id", routeId_);
                    p.put("journeyDate", date_);
                    p.put("journeyTime", time_);
                    return p;
                }
            };

            requestQueue.add(jor);
            return null;
        }
    }
}
