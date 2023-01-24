package it.uniba.dib.sms222329.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
import it.uniba.dib.sms222329.database.CoRelatoreDatabase;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RelatoreDatabase;
import it.uniba.dib.sms222329.database.TesistaDatabase;
import it.uniba.dib.sms222329.fragment.CameraFragment;
import it.uniba.dib.sms222329.fragment.ImpostazioniFragment;
import it.uniba.dib.sms222329.fragment.relatore.HomeFragment;
import it.uniba.dib.sms222329.fragment.relatore.MessaggiFragment;
import it.uniba.dib.sms222329.fragment.relatore.ModificaProfiloRelatoreFragment;
import it.uniba.dib.sms222329.fragment.relatore.ProfiloFragment;
import it.uniba.dib.sms222329.fragment.relatore.StudentiRelatoreFragment;
import it.uniba.dib.sms222329.fragment.relatore.TesiFragment;

public class UtenteLoggato extends AppCompatActivity {

    private Database db = new Database(this);
    private Relatore RelatoreLoggato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_loggato);

        UtenteRegistrato Utente = (UtenteRegistrato) getIntent().getSerializableExtra("utentePassato");

        try{
            if(Utente.getTipoUtente() == 1){ //tesista
                Tesista TesistaLoggato = TesistaDatabase.IstanziaTesista(Utente, db);
            }
            else if (Utente.getTipoUtente() == 2){ //relatore
                RelatoreLoggato = RelatoreDatabase.IstanziaRelatore(Utente, db);
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new HomeFragment(RelatoreLoggato));
                setBottomNavigation(new TesiFragment(RelatoreLoggato), new MessaggiFragment(RelatoreLoggato), new HomeFragment(RelatoreLoggato), new StudentiRelatoreFragment(),new CameraFragment());
            }
            else if (Utente.getTipoUtente() == 3){ //corelatore
                CoRelatore CoRelatoreLoggato = CoRelatoreDatabase.IstanziaCoRelatore(Utente, db);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }

    @SuppressLint("NonConstantResourceId")
    private void setBottomNavigation(Fragment thesisFragment, Fragment messagesFragment, Fragment homeFragment, Fragment studentFragment, Fragment cameraFragment) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        // Set a listener for item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
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
                case R.id.navigation_student:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, studentFragment);
                    return true;
                case R.id.navigation_camera:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, cameraFragment);
                    return true;
            }
            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.navigation_profile:
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new ModificaProfiloRelatoreFragment(db, RelatoreLoggato));
                return true;
            case R.id.navigation_settings:
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new ImpostazioniFragment());
                return true;
            case R.id.navigation_logout:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}