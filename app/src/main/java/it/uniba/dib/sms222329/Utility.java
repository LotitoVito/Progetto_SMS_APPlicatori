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

import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;

public class Utility {

    public static final int GUEST = 0;
    public static final int TESISTA = 1;
    public static final int RELATORE = 2;
    public static final int CORELATORE = 3;
    public static final DateTimeFormatter convertFromStringDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter convertFromStringDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter showDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter showDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static final int PERMESSO_STORAGE = 1;

    public static Tesista tesistaLoggato;
    public static Relatore relatoreLoggato;
    public static CoRelatore coRelatoreLoggato;
    public static int accountLoggato;

    private Utility() {}

    public static void Clear(){
        Utility.tesistaLoggato = null;
        Utility.relatoreLoggato = null;
        Utility.coRelatoreLoggato = null;
        Utility.accountLoggato = -1;
    }

    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void goBack(Activity activity){
        activity.onBackPressed();
    }

    public static boolean isEmptyTextbox(EditText textbox){
        if(textbox.getText().toString().trim().compareTo("")==0){
            return true;
        }
        return false;
    }

    public static void fillIfEmpty(EditText campo, String value){
        if(campo.getText().toString().trim().matches("")){
            campo.autofill(AutofillValue.forText(value));
        }
    }

    public static boolean CheckStorage(Activity activity){
        if(isExternalStorageAvailable() && !isExternalStorageReadOnly()){
            if (Utility.CheckPermessi(activity)){
                return true;
            } else{
                Toast.makeText(activity.getApplicationContext(), "Permessi bloccati", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(activity.getApplicationContext(), "Non è possibile accedere ai file", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public static boolean CheckPermessi(Activity activity){
        if(ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission_group.STORAGE)){
                new AlertDialog.Builder(activity.getApplicationContext())
                        .setTitle("Permesso richiesto")
                        .setMessage("Il permesso è richiestoper la gestione dei file")
                        .setPositiveButton("Accetta", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, Utility.PERMESSO_STORAGE);

                            }
                        })
                        .setNegativeButton("Rifiuta", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, Utility.PERMESSO_STORAGE);
            }
        }
        return true;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}