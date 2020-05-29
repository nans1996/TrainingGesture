package com.example.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.client.R;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonSetting;
    Button buttonTraining;
    Button buttonTranslate;
    Button buttonHistory;
    Button buttonAbout;
    Button buttonOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonSetting = (Button)findViewById(R.id.buttonSettingMenu);
        buttonHistory = (Button)findViewById(R.id.buttonHistoryMenu);
        buttonAbout =(Button)findViewById(R.id.buttonAboutMenu);
        buttonTranslate = (Button)findViewById(R.id.buttonTranslateMenu);
        buttonOut = (Button) findViewById(R.id.buttonOut);
        buttonTraining = (Button)findViewById(R.id.buttonTrainingMenu);

        mAuth = FirebaseAuth.getInstance();

        buttonSetting.setOnClickListener(this);
        buttonHistory.setOnClickListener(this);
        buttonAbout.setOnClickListener(this);
        buttonTraining.setOnClickListener(this);
        buttonTranslate.setOnClickListener(this);
        buttonOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.buttonSettingMenu: {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.buttonTrainingMenu: {
                Intent intent1 = new Intent(this, TrainingActivity.class);
                startActivity(intent1);
                break;
            }
            case R.id.buttonTranslateMenu: {
                Intent intent2 = new Intent(this, TranslateActivity.class);
                startActivity(intent2);
                break;
            }
            case R.id.buttonHistoryMenu: {
                Intent intent3 = new Intent(this, HistoryActivity.class);
                startActivity(intent3);
                break;
            }
            case R.id.buttonAboutMenu: {
                Intent intent4 = new Intent(this, AboutActivity.class);
                startActivity(intent4);
                break;
            }
            case  R.id.buttonOut: {
                mAuth.signOut();
                Intent intent5 = new Intent(this, MainActivity.class);
                startActivity(intent5);
                break;
            }
        }
    }
}
