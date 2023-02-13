package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Locale;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isChecked = prefs.getBoolean("dark_mode", false);
        this.runOnUiThread(() -> AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO));


    }

    @Override
    protected void onResume() {
        super.onResume();
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

        View signAsGuestButton = findViewById(R.id.signAsGuestButton);
        signAsGuestButton.setOnClickListener(view -> {
            UtenteRegistrato Utente = new UtenteRegistrato("guest", "guest");
            Intent guest = new Intent(getApplicationContext(), LoggedActivity.class);
            guest.putExtra("utentePassato", Utente);
            startActivity(guest);
        });
    }

    public void setLocal(String lingua){
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(new Locale(lingua));
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        onConfigurationChanged(config);
    }

}