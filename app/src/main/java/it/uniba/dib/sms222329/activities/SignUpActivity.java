package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.uniba.dib.sms222329.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Button studentButton = findViewById(R.id.Studentbutton);
        Button relatoreButton = findViewById(R.id.Relatorebutton);
        Button corelatoreButton = findViewById(R.id.Corelatorebutton);

        studentButton.setOnClickListener(view -> {
            Intent signupStudente = new Intent(getApplicationContext(), SignUp_StudentActivity.class);
            startActivity(signupStudente);
        });

        relatoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupRelatore = new Intent(getApplicationContext(), SignUp_RelatoreActivity.class);
                startActivity(signupRelatore);
            }
        });

        corelatoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupCorelatore = new Intent(getApplicationContext(), SignUp_CorelatoreActivity.class);
                startActivity(signupCorelatore);
            }
        });

    }
}