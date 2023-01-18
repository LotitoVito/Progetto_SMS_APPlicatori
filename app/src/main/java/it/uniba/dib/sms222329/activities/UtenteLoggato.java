package it.uniba.dib.sms222329.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.ImpostazioniFragment;
import it.uniba.dib.sms222329.fragment.relatore.HomeFragment;
import it.uniba.dib.sms222329.fragment.relatore.MessaggiFragment;
import it.uniba.dib.sms222329.fragment.relatore.ProfiloFragment;
import it.uniba.dib.sms222329.fragment.relatore.TesiFragment;
import it.uniba.dib.sms222329.fragment.signUp.ModificaProfiloRelatoreFragment;

public class UtenteLoggato extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_loggato);
        Database db = new Database(this);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        //actionBar.setDisplayHomeAsUpEnabled(true);

        UtenteRegistrato Utente = (UtenteRegistrato) getIntent().getSerializableExtra("utentePassato");

        if(Utente.getTipoUtente().compareTo("1") == 0){ //tesista
            Tesista TesistaLoggato = Utente.IstanziaTesista(db);
        }
        else if (Utente.getTipoUtente().compareTo("2") == 0){ //relatore
            Relatore RelatoreLoggato = Utente.IstanziaRelatore(db);
            Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new HomeFragment(RelatoreLoggato));
            setBottomNavigation(new TesiFragment(RelatoreLoggato), new MessaggiFragment(), new HomeFragment(RelatoreLoggato), new ProfiloFragment());
        }
        else if (Utente.getTipoUtente().compareTo("3") == 0){ //corelatore
            CoRelatore CoRelatoreLoggato = Utente.IstanziaCoRelatore(db);
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void setBottomNavigation(Fragment thesisFragment, Fragment messagesFragment, Fragment homeFragment, Fragment profileFragment) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        // Set a listener for item selection
        bottomNavigationView.setOnItemSelectedListener((BottomNavigationView.OnItemSelectedListener) item -> {
            // Handle item selection
            switch (item.getItemId()) {
                case R.id.navigation_thesis:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, thesisFragment);
                    return true;
                case R.id.navigation_messages:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, messagesFragment);
                    return true;
                case R.id.navigation_home:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, homeFragment);
                    return true;
                case R.id.navigation_profile:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new ModificaProfiloRelatoreFragment());
                    return true;
                case R.id.navigation_settings:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new ImpostazioniFragment());
                    return true;
            }
            return false;
        });

    }


}