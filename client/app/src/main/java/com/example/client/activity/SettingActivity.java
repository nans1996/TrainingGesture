package com.example.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.client.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button updateButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        email = (EditText)findViewById(R.id.editTextEditEmail);
        password = (EditText)findViewById(R.id.editTextEditPassword);
        updateButton = findViewById(R.id.buttonEdit);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        email.setText(firebaseUser.getEmail());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    firebaseUser.updateEmail(email.getText().toString());
                    resultDialog("Successful");
                } catch (Exception e) {
                    System.out.println("Ошибка смены email: "+ e.getMessage());
                    resultDialog("Oops... error");
                }
            }
        });
    }

    public void resultDialog(String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("Result")
                .setIcon(R.drawable.zhest_icon)
                .setMessage("Successful")
                .setPositiveButton("Ok", null)
                .create().show();
    }
}
