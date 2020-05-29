package com.example.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

import com.example.client.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        email = (EditText)findViewById(R.id.editTextEditEmail);
        password = (EditText)findViewById(R.id.editTextEditPassword);

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        email.setText(firebaseUser.getEmail());

    }
}
