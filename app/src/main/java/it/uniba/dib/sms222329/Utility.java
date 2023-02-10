package it.uniba.dib.sms222329;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.autofill.AutofillValue;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.time.format.DateTimeFormatter;

import it.uniba.dib.sms222329.activities.LoggedActivity;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;

public class Utility {

    /** Costanti per l'account loggato */
    public static final int GUEST = 0;
    /** Costanti per l'account loggato */
    public static final int TESISTA = 1;
    /** Costanti per l'account loggato */
    public static final int RELATORE = 2;
    /** Costanti per l'account loggato */
    public static final int CORELATORE = 3;

    /** Formato conversione data*/
    public static final DateTimeFormatter convertFromStringDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /** Formato conversione data e ora*/
    public static final DateTimeFormatter convertFromStringDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /** Formato visualizzazione data*/
    public static final DateTimeFormatter showDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    /** Formato visualizzazione data e ora*/
    public static final DateTimeFormatter showDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /** Costante usata per il request code della richiesta dei permessi per lo storage */
    public static final int REQUEST_PERMESSO_STORAGE = 1;
    /** Costante usata per il reuqest code del caricamento dei file */
    public static final int REQUEST_CARICA_FILE = 2;
    /** Costante usata per il reuqest della richiesta dei permessi per la fotocamera */
    public static final int REQUEST_CAMERA = 3;

    /** Account tesista loggato */
    public static Tesista tesistaLoggato;
    /** Account relatore loggato */
    public static Relatore relatoreLoggato;
    /** Account corelatore loggato */
    public static CoRelatore coRelatoreLoggato;
    /** Account utente loggato */
    public static UtenteRegistrato utenteLoggato;
    /** Variabile usata per tener conto di quale account è loggato */
    public static int accountLoggato;

    /**
     * Costruttore
     */
    private Utility() {}

    /**
     * Metodo usato per pulire le variabili di login quando si effettua il logout
     */
    public static void Clear(){
        Utility.tesistaLoggato = null;
        Utility.relatoreLoggato = null;
        Utility.coRelatoreLoggato = null;
        Utility.utenteLoggato = null;
        Utility.accountLoggato = -1;
    }

    /**
     * Metodo usato per l'apertura di un nuovo fragment
     * @param fragmentManager
     * @param containerId
     * @param fragment
     */
    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Metodo usato per tornare alla view precedente
     * @param activity
     */
    public static void goBack(Activity activity){
        activity.onBackPressed();
    }

    /**
     * Metodo usato per verificare se la textbox è vuota
     * @param textbox
     * @return  Restituisce true se è vuota, altrimento false
     */
    public static boolean isEmptyTextbox(EditText textbox){
        if(textbox.getText().toString().trim().compareTo("")==0){
            return true;
        }
        return false;
    }

    /**
     * Metodo usato per riempire la textbox con il value se è vuota
     * @param campo
     * @param value
     */
    public static void fillIfEmpty(EditText campo, String value){
        if(campo.getText().toString().trim().matches("")){
            campo.autofill(AutofillValue.forText(value));
        }
    }

    /**
     * Metodo usato per verificare se la memoria esterna è accessibile e nel caso manchino i permessi li richiede con un altro metodo
     * @param activity
     * @return  Restituisce true se è accessibile, altrimenti false
     */
    public static boolean CheckStorage(Activity activity, Fragment fragment){
        if(isExternalStorageAvailable() && !isExternalStorageReadOnly()){
            if(ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                RichiediPermessiScritturaMemoria(activity, fragment);
            }
        } else {
            Toast.makeText(activity.getApplicationContext(), "Non è possibile accedere ai file", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * Metodo per effettuare la richiesta all'utente dei permessi per lo storage spiegando anche il motivo per cui servono
     * @param activity
     * @return  Restituisce true se i permessi sono concessi, altrimenti false
     */
    public static void RichiediPermessiScritturaMemoria(Activity activity, Fragment fragment){
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(activity)
                    .setTitle("Permesso richiesto")
                    .setMessage("Il permesso è richiesto per la gestione dei file")
                    .setPositiveButton("Accetta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Utility.REQUEST_PERMESSO_STORAGE);

                        }
                    })
                    .setNegativeButton("Rifiuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        } else {
            fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Utility.REQUEST_PERMESSO_STORAGE);
        }
    }

    /**
     * Metodo usato per verificare se la memoria esterna è disponibile
     * @return  Restituisce true se la memoria esterna è disponibile, altrimento false
     */
    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    /**
     * Metodo usato per verificare se la memoria esterna è a sola lettura
     * @return  Restituisce true se la memoria esterno è a sola lettura, altrimento false
     */
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}