package it.uniba.dib.sms222329.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.CoRelatoreDatabase;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RelatoreDatabase;
import it.uniba.dib.sms222329.database.TesistaDatabase;
import it.uniba.dib.sms222329.fragment.ImpostazioniFragment;
import it.uniba.dib.sms222329.fragment.ProfiloFragment;
import it.uniba.dib.sms222329.fragment.VisualizzaTesiFragment;
import it.uniba.dib.sms222329.fragment.relatore.HomeFragment;
import it.uniba.dib.sms222329.fragment.relatore.MessaggiFragment;
import it.uniba.dib.sms222329.fragment.relatore.TesiFragment;
import it.uniba.dib.sms222329.fragment.relatore.TesistiRelatoreFragment;

public class UtenteLoggato extends AppCompatActivity {

    private Database db = new Database(this);
    private UtenteRegistrato utenteLoggato;
    private Tesista tesistaLoggato;
    private Relatore relatoreLoggato;
    private CoRelatore coRelatoreLoggato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_loggato);

        UtenteRegistrato utente = (UtenteRegistrato) getIntent().getSerializableExtra("utentePassato");
        utenteLoggato = utente;

        try{
            if(utente.getTipoUtente() == Utility.TESISTA){ //tesista
                tesistaLoggato = TesistaDatabase.IstanziaTesista(utente, db);
            }
            else if (utente.getTipoUtente() == Utility.RELATORE){ //relatore
                relatoreLoggato = RelatoreDatabase.IstanziaRelatore(utente, db);
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new HomeFragment(relatoreLoggato));
                setBottomNavigation(new TesiFragment(relatoreLoggato), new MessaggiFragment(relatoreLoggato), new HomeFragment(relatoreLoggato), new TesistiRelatoreFragment());
            }
            else if (utente.getTipoUtente() == Utility.CORELATORE){ //corelatore
                coRelatoreLoggato = CoRelatoreDatabase.IstanziaCoRelatore(utente, db);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }

    @SuppressLint("NonConstantResourceId")
    private void setBottomNavigation(Fragment thesisFragment, Fragment messagesFragment, Fragment homeFragment, Fragment studentFragment) {
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
                    scanQR();
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
                if(utenteLoggato.getTipoUtente() == Utility.TESISTA){
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new ProfiloFragment(db, utenteLoggato.getTipoUtente(), tesistaLoggato));
                } else if (utenteLoggato.getTipoUtente() == Utility.RELATORE){
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new ProfiloFragment(db, utenteLoggato.getTipoUtente(), relatoreLoggato));
                }else if(utenteLoggato.getTipoUtente() == Utility.CORELATORE){
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new ProfiloFragment(db, utenteLoggato.getTipoUtente(), coRelatoreLoggato));
                }
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

    private void scanQR(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->{
        if(result.getContents()!=null)
        {
            Database db = new Database(this);
            Cursor cursor = db.RicercaDato("SELECT * FROM " + Database.TESI + " WHERE " + Database.TESI_ID + "=" + result.getContents() + ";");
            cursor.moveToNext();
            String messaggio = cursor.getString(1) + "\n" + cursor.getString(2);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tesi");
            builder.setMessage(messaggio);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tesi tesi = new Tesi(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                            cursor.getFloat(4), cursor.getInt(5), cursor.getString(6), cursor.getInt(7)==1,
                            cursor.getInt(8), cursor.getInt(9));
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new VisualizzaTesiFragment(tesi));
                }
            }).show();
        }
    });

}