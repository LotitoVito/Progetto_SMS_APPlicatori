package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import it.uniba.dib.sms222329.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Nasconde l'action bar per l'homepage
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onResume() {
        View signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(view -> {
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(login);
        });

        View signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(view -> {
            Intent signUp = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(signUp);
        });

        super.onResume();
    }
}