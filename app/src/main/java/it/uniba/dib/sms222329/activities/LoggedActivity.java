package it.uniba.dib.sms222329.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.CoRelatoreDatabase;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RelatoreDatabase;
import it.uniba.dib.sms222329.database.TesistaDatabase;
import it.uniba.dib.sms222329.fragment.tesiscelta.TesiSceltaListaGuestFragment;
import it.uniba.dib.sms222329.fragment.ImpostazioniFragment;
import it.uniba.dib.sms222329.fragment.utente.UtenteProfiloFragment;
import it.uniba.dib.sms222329.fragment.tesi.TesiVisualizzaFragment;
import it.uniba.dib.sms222329.fragment.ricevimento.RicevimentiCalendarioFragment;
import it.uniba.dib.sms222329.fragment.segnalazione.SegnalazioneChatListaFragment;
import it.uniba.dib.sms222329.fragment.tesi.TesiListaFragment;
import it.uniba.dib.sms222329.fragment.tesiscelta.TesiSceltaListaFragment;
import it.uniba.dib.sms222329.fragment.tesiscelta.TesiSceltaMiaFragment;

public class LoggedActivity extends AppCompatActivity {

    //Variabili e Oggetti
    private Database db = new Database(this);
    /**
     * Variabile usata verificare quale account istanziare
     */
    public static UtenteRegistrato utenteLoggato;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);

        utenteLoggato = (UtenteRegistrato) getIntent().getSerializableExtra("utentePassato");

        try{
            if(utenteLoggato.getTipoUtente() == Utility.TESISTA){
                Utility.utenteLoggato = utenteLoggato;
                Utility.tesistaLoggato = TesistaDatabase.IstanziaTesista(utenteLoggato, db);
                Utility.accountLoggato = Utility.TESISTA;
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new RicevimentiCalendarioFragment());
                setBottomNavigation(new TesiListaFragment(), new SegnalazioneChatListaFragment(), new RicevimentiCalendarioFragment(), new TesiSceltaMiaFragment());
            }
            else if (utenteLoggato.getTipoUtente() == Utility.RELATORE){
                Utility.utenteLoggato = utenteLoggato;
                Utility.relatoreLoggato = RelatoreDatabase.IstanziaRelatore(utenteLoggato, db);
                Utility.accountLoggato = Utility.RELATORE;
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new RicevimentiCalendarioFragment());
                setBottomNavigation(new TesiListaFragment(), new SegnalazioneChatListaFragment(), new RicevimentiCalendarioFragment(), new TesiSceltaListaFragment());
            }
            else if (utenteLoggato.getTipoUtente() == Utility.CORELATORE){
                Utility.utenteLoggato = utenteLoggato;
                Utility.coRelatoreLoggato = CoRelatoreDatabase.IstanziaCoRelatore(utenteLoggato, db);
                Utility.accountLoggato = Utility.CORELATORE;
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new RicevimentiCalendarioFragment());
                setBottomNavigation(new TesiListaFragment(), new SegnalazioneChatListaFragment(), new RicevimentiCalendarioFragment(), new TesiSceltaListaFragment());
            }
            else if(utenteLoggato.getEmail().compareTo("guest")==0) {
                Utility.accountLoggato = Utility.GUEST;
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new TesiListaFragment());
                setGuestBottomNavigation(new TesiListaFragment(), new TesiSceltaListaGuestFragment());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo usato per impostare la nav bar corretta a seconda dell'utente che logga
     * @param thesisFragment    fragment per la tab lista delle tesi
     * @param messagesFragment  fragment per la tab messaggi
     * @param homeFragment      fragment per la tab home
     * @param studentFragment   fragment per la tab studenti
     */
    private void setBottomNavigation(Fragment thesisFragment, Fragment messagesFragment, Fragment homeFragment, Fragment studentFragment) {

        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.inflateMenu(R.menu.navigation);

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
                    setTitle(R.string.mythesis);
                    return true;
                case R.id.navigation_camera:
                    scanQR();
                    return true;
            }
            return false;
        });
    }

    /**
     * Metodo usato per settare la nav bar del guest
     * @param thesisFragment
     * @param studentFragment
     */
    private void setGuestBottomNavigation(Fragment thesisFragment, Fragment studentFragment) {
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
                case R.id.navigation_student:
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container, studentFragment);
                    setTitle(R.string.tesi_completate);
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
        if(utenteLoggato.getEmail().compareTo("guest")==0) {
            menu.removeItem(R.id.navigation_profile);
            return true;
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.navigation_profile:
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new UtenteProfiloFragment());
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

    /**
     * Metodo usato per lo scanQR, permette di mostrare la tesi trovata tramite QR
     * se l'utente conferma la richiesta di visualizzazione
     */
    private void scanQR(){
        if(CheckPermessiFotocamera()){
            ScanOptions options = new ScanOptions();
            options.setPrompt(getResources().getString(R.string.scan_qr_flash));
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            options.setCaptureActivity(CaptureAct.class);
            barLauncher.launch(options);
        }
    }

    /**
     * Avvia l'activity di scanning ddl QRCode
     */
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->{
        if(result.getContents()!=null)
        {
            Database db = new Database(this);
            Cursor cursor = db.RicercaDato("SELECT * FROM " + Database.TESI + " WHERE " + Database.TESI_ID + "=" + result.getContents() + ";");
            if(cursor.moveToNext()){
                Tesi tesi = new Tesi(cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Database.TESI_TITOLO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Database.TESI_ARGOMENTO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_TEMPISTICHE)),
                        cursor.getFloat(cursor.getColumnIndexOrThrow(Database.TESI_MEDIAVOTOMINIMA)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_ESAMINECESSARI)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Database.TESI_SKILLRICHIESTE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_STATO))==1,
                        cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_VISUALIZZAZIONI)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_RELATOREID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESI_UNIVERSITACORSOID)));
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container, new TesiVisualizzaFragment(tesi));
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.tesi);
                builder.setMessage(R.string.scan_qr_error_qr);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                    }
                });
            }
        }
    });

    /**
     * Il metodo verifica i permessi per l'utilizzo della fotocamera, se non sono ancora stati concessi li chiede all'utente
     * @return  restituisce true se i concessi sono già stati concessi, altrimenti false
     */
    private boolean CheckPermessiFotocamera() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                new AlertDialog.Builder(this)
                        .setTitle(R.string.permesso_richiesta)
                        .setMessage(R.string.permesso_camera)
                        .setPositiveButton(R.string.accetta, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(LoggedActivity.this, new String[]{Manifest.permission.CAMERA}, Utility.REQUEST_CAMERA);

                            }
                        })
                        .setNegativeButton(R.string.rifiuta, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Utility.REQUEST_CAMERA);
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==Utility.REQUEST_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            scanQR();
        } else {
            Toast.makeText(this, R.string.permesso_negato, Toast.LENGTH_SHORT).show();
        }
    }
}