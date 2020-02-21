package com.example.nayeemhasan.smartticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText userName,passWord;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.editText4);
        passWord = (EditText) findViewById(R.id.editText6);

        loginButton = (Button) findViewById(R.id.LoginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userName.getText().toString().matches("") || passWord.getText().toString().matches("")){
                    Toast.makeText(LoginActivity.this, "User Name or Password missing", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                    intent.putExtra("user_Name",userName.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
