package com.example.client.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private EditText mEmailField;
    private  EditText mPasswordField;

        Button regButton;
        Button logInButton;
        Button translateButton;
        Button aboutButton;
        private FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            logInButton = findViewById(R.id.buttonLogIn);
            regButton = findViewById(R.id.buttonReg);
            translateButton = findViewById(R.id.buttonTranslate);
            aboutButton = findViewById(R.id.buttonAbout);

            mStatusTextView = (TextView)findViewById(R.id.textViewStatus);
            mDetailTextView = (TextView)findViewById(R.id.textViewDetail);

            mEmailField = (EditText)findViewById(R.id.editTextEmail);
            mPasswordField = (EditText)findViewById(R.id.editTextPassword);

            logInButton.setOnClickListener(this);
            regButton.setOnClickListener(this);
            translateButton.setOnClickListener(this);
            aboutButton.setOnClickListener(this);

            // [START initialize_auth]
            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();
            //[END initialize_auth]
        }

    // [START on_start_check_user]
        @Override
        public  void onStart() {
            super.onStart ();
            // Проверяем, вошел ли пользователь (не ноль) и обновляем ли пользователь соответствующим образом.
            FirebaseUser currentUser = mAuth.getCurrentUser ();
           // toMenu (currentUser);
        }
    // [END on_start_check_user]

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

    // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
              registrationStatus(user);
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                Toast.makeText(MainActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
               registrationStatus(null);
            }

        }
    });
    // [END create_user_with_email]
}

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            toMenu(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            toMenu(null);
                        }
                    }
                });
    }

//    private void signOut() {
//        mAuth.signOut();
//       // updateUI(null);
//    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void toMenu(FirebaseUser user) {
            if (user != null) {
                mStatusTextView.setText("");
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            } else {
                mStatusTextView.setText("User not found!");
            }
    }

    private void registrationStatus(FirebaseUser user) {
        if (user != null) {
            mStatusTextView.setText("Registration is successfull!");
        } else {
            mStatusTextView.setText("Failed registration user!");
        }
    }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonLogIn: {
                    signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                    break;
                }
                case  R.id.buttonReg: {
                    mStatusTextView.setText("");
                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                    break;
                }
                case R.id.buttonTranslate: {
                    Intent intent = new Intent(this, TranslateActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.buttonAbout: {
                    Intent intent = new Intent(this, AboutActivity.class);
                    startActivity(intent);
                    break;
                }
            }
        }
    }
