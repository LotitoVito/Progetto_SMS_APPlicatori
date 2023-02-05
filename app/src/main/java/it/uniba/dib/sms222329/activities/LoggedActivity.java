package it.uniba.dib.sms222329.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import it.uniba.dib.sms222329.fragment.GuestTesiFragment;
import it.uniba.dib.sms222329.fragment.ImpostazioniFragment;
import it.uniba.dib.sms222329.fragment.ProfiloFragment;
import it.uniba.dib.sms222329.fragment.VisualizzaTesiFragment;
import it.uniba.dib.sms222329.fragment.relatore.HomeFragment;
import it.uniba.dib.sms222329.fragment.relatore.SegnalazioneChatFragment;
import it.uniba.dib.sms222329.fragment.relatore.ListaTesiFragment;
import it.uniba.dib.sms222329.fragment.relatore.TesistiRelatoreFragment;
import it.uniba.dib.sms222329.fragment.tesista.MiaTesiFragment;
import it.uniba.dib.sms222329.fragment.tesista.TesiSceltaTesistaFragment;

public class LoggedActivity extends AppCompatActivity {

    //Variabili e Oggetti
    private Database db = new Database(this);
    public static UtenteRegistrato utenteLoggato;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_loggato);

        utenteLoggato = (UtenteRegistrato) getIntent().getSerializableExtra("utentePassato");

        try{
            if(utenteLoggato.getTipoUtente() == Utility.TESISTA){
                Utility.tesistaLoggato = TesistaDatabase.IstanziaTesista(utenteLoggato, db);
                Utility.accountLoggato = Utility.TESISTA;
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new HomeFragment());
                setBottomNavigation(new ListaTesiFragment(), new SegnalazioneChatFragment(), new HomeFragment(), new MiaTesiFragment());
            }
            else if (utenteLoggato.getTipoUtente() == Utility.RELATORE){
                Utility.relatoreLoggato = RelatoreDatabase.IstanziaRelatore(utenteLoggato, db);
                Utility.accountLoggato = Utility.RELATORE;
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new HomeFragment());
                setBottomNavigation(new ListaTesiFragment(), new SegnalazioneChatFragment(), new HomeFragment(), new TesistiRelatoreFragment());
            }
            else if (utenteLoggato.getTipoUtente() == Utility.CORELATORE){
                Utility.coRelatoreLoggato = CoRelatoreDatabase.IstanziaCoRelatore(utenteLoggato, db);
                Utility.accountLoggato = Utility.CORELATORE;
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new HomeFragment());
                setBottomNavigation(new ListaTesiFragment(), new SegnalazioneChatFragment(), new HomeFragment(), new TesistiRelatoreFragment());
            }else if(utenteLoggato.getEmail().compareTo("guest")==0) {
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new GuestTesiFragment());
                setGuestBottomNavigation(new GuestTesiFragment());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setBottomNavigation(Fragment thesisFragment, Fragment messagesFragment, Fragment homeFragment, Fragment studentFragment) {

        bottomNavigationView = findViewById(R.id.navigation);

        if(utenteLoggato.getTipoUtente() == Utility.TESISTA) {
           bottomNavigationView.getMenu().clear();
           bottomNavigationView.inflateMenu(R.menu.nav_student);
        }

        // Set a listener for item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Handle item selection
            switch (item.getItemId()) {
                case R.id.navigation_thesis:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, thesisFragment);
                    setTitle(R.string.tesi);
                    return true;
                case R.id.navigation_messages:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, messagesFragment);
                    setTitle(R.string.title_messages);
                    return true;
                case R.id.navigation_home:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, homeFragment);
                    setTitle(R.string.title_home);
                    return true;
                case R.id.navigation_student:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, studentFragment);
                    setTitle(R.string.mystudent);
                    if(utenteLoggato.getTipoUtente() == Utility.TESISTA) setTitle(R.string.mythesis);
                    return true;
                case R.id.navigation_camera:
                    scanQR();
                    return true;
            }
            return false;
        });

    }

    private void setGuestBottomNavigation(Fragment thesisFragment) {
        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.nav_guest);

        // Set a listener for item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Handle item selection
            switch (item.getItemId()) {
                case R.id.navigation_thesis:
                   Utility.replaceFragment(getSupportFragmentManager(), R.id.container, thesisFragment);
                    setTitle(R.string.tesi);
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
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new ProfiloFragment());
                } else if (utenteLoggato.getTipoUtente() == Utility.RELATORE){
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new ProfiloFragment());
                }else if(utenteLoggato.getTipoUtente() == Utility.CORELATORE){
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new ProfiloFragment());
                }
                setTitle(R.string.title_profile);
                return true;
            case R.id.navigation_settings:
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new ImpostazioniFragment());
                setTitle(R.string.title_settings);
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
        options.setPrompt("Volume up per usare il flash");
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
            String messaggio = cursor.getString(cursor.getColumnIndexOrThrow(Database.TESI_TITOLO)) + "\n" + cursor.getString(cursor.getColumnIndexOrThrow(Database.TESI_ARGOMENTO));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.tesi);
            builder.setMessage(messaggio);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tesi tesi = new Tesi(cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Database.TESI_TITOLO)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Database.TESI_ARGOMENTO)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_TEMPISTICHE)),
                            cursor.getFloat(cursor.getColumnIndexOrThrow(Database.TESI_MEDIAVOTOMINIMA)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_ESAMINECESSARI)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Database.TESI_SKILLRICHIESTE)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_STATO))==1,
                            cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_VISUALIZZAZIONI)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_RELATOREID)));
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new VisualizzaTesiFragment(tesi));
                }
            }).show();
        }
    });
}