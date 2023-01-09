package it.uniba.dib.sms222329.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.uniba.dib.sms222329.R;
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

public class UtenteLoggato extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_loggato);
        Database db = new Database(this);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        UtenteRegistrato Utente = (UtenteRegistrato) getIntent().getSerializableExtra("utentePassato");

        if(Utente.getTipoUtente().compareTo("0") == 0){ //tesista
            Tesista TesistaLoggato = Utente.IstanziaTesista(Utente.getEmail(), Utente.getPassword(), db);

        }
        else if (Utente.getTipoUtente().compareTo("1") == 0){ //relatore
            replaceFragment(new HomeFragment());
            Relatore RelatoreLoggato = Utente.IstanziaRelatore(Utente.getEmail(), Utente.getPassword(), db);
            setBottomNavigation(new TesiFragment(), new MessaggiFragment(), new HomeFragment(), new ProfiloFragment());
        }
        else if (Utente.getTipoUtente().compareTo("2") == 0){ //corelatore
            CoRelatore CoRelatoreLoggato = Utente.IstanziaCoRelatore(Utente.getEmail(), Utente.getPassword(), db);
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
                    replaceFragment(thesisFragment);
                    return true;
                case R.id.navigation_messages:
                    replaceFragment(messagesFragment);
                    return true;
                case R.id.navigation_home:
                    replaceFragment(homeFragment);
                    return true;
                case R.id.navigation_profile:
                    replaceFragment(profileFragment);
                    return true;
                case R.id.navigation_settings:
                    replaceFragment(new ImpostazioniFragment());
                    return true;
            }
            return false;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public Tesista GetTesista(Tesista account){
        return account;
    }

    public Relatore GetRelatore(Relatore account){
        return account;
    }

    public CoRelatore GetCoRelatore(CoRelatore account){
        return account;
    }
}