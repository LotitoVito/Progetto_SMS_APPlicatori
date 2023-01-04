package it.uniba.dib.sms222329.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.classi.Utente;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;

public class UtenteLoggato extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_loggato);
        Database db = new Database(this);

        UtenteRegistrato Utente = (UtenteRegistrato) getIntent().getSerializableExtra("utentePassato");

        if(Utente.getTipoUtente().compareTo("0") == 0){ //tesista
            Tesista TesistaLoggato = Utente.IstanziaTesista(Utente.getEmail(), Utente.getPassword(), db);
        }
        else if (Utente.getTipoUtente().compareTo("1") == 0){ //relatore
            Relatore RelatoreLoggato = Utente.IstanziaRelatore(Utente.getEmail(), Utente.getPassword(), db);
        }
        else if (Utente.getTipoUtente().compareTo("2") == 0){ //corelatore
            CoRelatore CoRelatoreLoggato = Utente.IstanziaCoRelatore(Utente.getEmail(), Utente.getPassword(), db);
        }
    }
}