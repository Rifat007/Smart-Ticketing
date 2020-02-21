package com.example.nayeemhasan.smartticket;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    TextView busNameText, busFareText, ticketCnt, totalFareText;
    Button confirmPay, cancelPay;

    String userName, date, time, routeId;
    String t1, t2, t3, t4;
    String url;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        busNameText = (TextView) findViewById(R.id.BusNameText);
        busFareText = (TextView) findViewById(R.id.FarePerTicket);
        ticketCnt = (TextView) findViewById(R.id.CountTicket);
        totalFareText = (TextView) findViewById(R.id.TotalText);
        confirmPay = (Button) findViewById(R.id.ConfirmPayment);
        cancelPay = (Button) findViewById(R.id.CancelPayment);

        Intent intent = getIntent();
        userName = intent.getStringExtra("user_Name");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        routeId = intent.getStringExtra("route_id");
        final int count = Integer.parseInt(intent.getStringExtra("ticket_Cnt"));

        busNameText.setText(intent.getStringExtra("bus_Name"));
        busFareText.setText(intent.getStringExtra("ticket_Fare"));
        ticketCnt.setText(intent.getStringExtra("ticket_Cnt"));

        int ticket_fare = Integer.parseInt(intent.getStringExtra("ticket_Fare"));
        int ticket_cnt =  Integer.parseInt(intent.getStringExtra("ticket_Cnt"));
        totalFareText.setText(String.valueOf(ticket_fare*ticket_cnt));

        requestQueue = Volley.newRequestQueue(this);

        url = "http://192.168.43.94:1234/SmartTicket/insertUser.php";
        if (count == 1){
            t1 = intent.getStringExtra("t1");
            //url = url + "&t1=" + t1;
        }
        else if (count == 2){
            t1 = intent.getStringExtra("t1");
            t2 = intent.getStringExtra("t2");
            //url = url +"&t1="+t1+"&t2="+t2;
        }
        else if (count == 3){
            t1 = intent.getStringExtra("t1");
            t2 = intent.getStringExtra("t2");
            t3 = intent.getStringExtra("t3");
            //url = url +"&t1="+t1+"&t2="+t2+"&t3="+t3;
        }
        else if (count == 4){
            t1 = intent.getStringExtra("t1");
            t2 = intent.getStringExtra("t2");
            t3 = intent.getStringExtra("t3");
            t4 = intent.getStringExtra("t4");
            //url = url +"&t1="+t1+"&t2="+t2+"&t3="+t3+"&t4="+t4;
        }

        confirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest jor = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(PaymentActivity.this, "Your request is being processed", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(PaymentActivity.this, "Unknown Error.Try Again", Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> p = new HashMap<>();
                        p.put("userName",userName);
                        p.put("routeId",routeId);
                        p.put("date",date);
                        p.put("time",time);

                        if (count == 1){
                            p.put("t1",t1);
                        }
                        else if (count == 2){
                            p.put("t1",t1);
                            p.put("t2",t2);
                        }
                        else if (count == 3){
                            p.put("t1",t1);
                            p.put("t2",t2);
                            p.put("t3",t3);
                        }
                        else if (count == 4){
                            p.put("t1",t1);
                            p.put("t2",t2);
                            p.put("t3",t3);
                            p.put("t4",t4);
                        }
                        return p;
                    }
                };
                requestQueue.add(jor);
            }
        });

        cancelPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelDialogFragment cancelDialogFragment = new CancelDialogFragment();
                DialogFragment dialogFragment = cancelDialogFragment;
                dialogFragment.show(getFragmentManager(),"CancelFirst");
            }
        });
    }

}
