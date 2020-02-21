package com.example.nayeemhasan.smartticket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
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

public class BusListActivity extends AppCompatActivity {

    private ArrayList<Transport> transports;
    private ListView busList;
    BusAdapter busAdapter;
    BusListTask busListTask;

    TextView textView;
    RequestQueue requestQueue;

    String date , userName, routeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        transports = new ArrayList<>();

        busList = (ListView) findViewById(R.id.BusList);
        textView = (TextView) findViewById(R.id.textView8);

        busAdapter = new BusAdapter();
        busList.setAdapter(busAdapter);

        Intent intent = getIntent();
        final String from = intent.getStringExtra("from");
        final String to = intent.getStringExtra("to");
        date = intent.getStringExtra("date");
        userName = intent.getStringExtra("user_Name");

        requestQueue = Volley.newRequestQueue(this);

        busListTask = new BusListTask();
        busListTask.execute(from,to);


    }

    public class BusAdapter extends ArrayAdapter<Transport>{

        public BusAdapter(){
            super(getApplicationContext(), R.layout.bus_row, transports);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            if (v == null){
                v = getLayoutInflater().inflate(R.layout.bus_row, parent, false);
            }

            final EditText busName = (EditText) v.findViewById(R.id.BusName);
            final EditText busTime = (EditText) v.findViewById(R.id.TimeText);
            final EditText fareText = (EditText) v.findViewById(R.id.FareText);
            RatingBar busRating = (RatingBar) v.findViewById(R.id.BusRating);
            final Spinner timeSpinner = (Spinner) v.findViewById(R.id.TimeSpinner);
            final ImageButton imageButton = (ImageButton) v.findViewById(R.id.imageButton);

            busName.setText(transports.get(position).getName());
            busRating.setRating(transports.get(position).getRatings());
            final int transportFare = transports.get(position).getFare();
            fareText.setText(transportFare+" \\-");

            timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    busTime.setText(timeSpinner.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i=0; i<transports.size(); i++){
                        if (transports.get(i).getName().matches(busName.getText().toString())){
                            routeId = String.valueOf(transports.get(i).getId());
                        }
                    }
                    Intent intent1 = new Intent(BusListActivity.this, BusSeatActivity.class);
                    intent1.putExtra("bus_Name",busName.getText().toString());
                    intent1.putExtra("bus_Time",busTime.getText().toString());
                    intent1.putExtra("bus_Fare",String.valueOf(transportFare));

                    intent1.putExtra("date",date);
                    intent1.putExtra("user_Name",userName);
                    intent1.putExtra("route_id",routeId);
                    startActivity(intent1);
                }
            });
            return v;
        }
    }


    private class BusListTask extends AsyncTask<String,Void,Void>
    {
        @Override
        protected Void doInBackground(String... params) {
            String from = params[0];
            String to = params[1];
            JsonArrayRequest jor = new JsonArrayRequest(Request.Method.GET,
                    "http://192.168.43.94:1234/SmartTicket/viewTransport.php?start=" + from + "&dest=" + to,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            transports.clear();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject j = response.getJSONObject(i);
                                    int id = j.getInt("id");
                                    String busName = j.getString("bus_name");
                                    float busRating = (float) j.getDouble("rating");
                                    int fare = j.getInt("fare");
                                    transports.add(new Transport(id, busName, busRating,fare));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                busAdapter.notifyDataSetChanged();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(BusListActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                        }
                    });

            requestQueue.add(jor);
            return null;
        }
    }
}
