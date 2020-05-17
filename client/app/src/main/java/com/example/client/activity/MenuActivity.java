package com.example.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.client.R;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonTraining;
    Button buttonTranslate;
    Button buttonOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonTranslate = (Button)findViewById(R.id.buttonTranslate);
        buttonOut = (Button) findViewById(R.id.buttonOut);
        buttonTraining = (Button)findViewById(R.id.buttonTraining);

        mAuth = FirebaseAuth.getInstance();

        buttonTranslate.setOnClickListener(this);
        buttonOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTraining: {
                Intent intent = new Intent(this, TrainingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.buttonTranslate: {
                Intent intent = new Intent(this, TranslateActivity.class);
                startActivity(intent);
                break;
            }
            case  R.id.buttonOut: {
                mAuth.signOut();
                break;
            }
        }
    }
}
