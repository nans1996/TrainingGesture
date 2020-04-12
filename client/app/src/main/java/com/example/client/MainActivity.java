package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

    public class MainActivity extends AppCompatActivity implements View.OnClickListener {

        Button regButton;
        Button logInButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            logInButton = (Button)findViewById(R.id.buttonLogIn);
            regButton = (Button) findViewById(R.id.buttonReg);

            logInButton.setOnClickListener(this);
            regButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonLogIn: {
                    Intent intent = new Intent(this, MenuActivity.class);
                    startActivity(intent);
                    break;
                }
                case  R.id.buttonReg: {
                    Intent intent = new Intent(this, RegistrationActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
