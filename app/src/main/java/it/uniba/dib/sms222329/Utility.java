package it.uniba.dib.sms222329;

import android.view.autofill.AutofillValue;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;

import java.time.format.DateTimeFormatter;

import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;

public class Utility {

    public static final int TESISTA = 1;
    public static final int RELATORE = 2;
    public static final int CORELATORE = 3;
    public static final DateTimeFormatter convertFromStringDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter convertFromStringDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter showDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter showDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static Tesista tesistaLoggato;
    public static Relatore relatoreLoggato;
    public static CoRelatore coRelatoreLoggato;
    public static int accountLoggato;

    private Utility() {}

    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
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

    public static Tesista getTesistaLoggato() {
        return tesistaLoggato;
    }

    public static void setTesistaLoggato(Tesista tesistaLoggato) {
        Utility.tesistaLoggato = tesistaLoggato;
        Utility.accountLoggato = TESISTA;
    }

    public static Relatore getRelatoreLoggato() {
        return relatoreLoggato;
    }

    public static void setRelatoreLoggato(Relatore relatoreLoggato) {
        Utility.relatoreLoggato = relatoreLoggato;
        Utility.accountLoggato = RELATORE;
    }

    public static CoRelatore getCoRelatoreLoggato() {
        return coRelatoreLoggato;
    }

    public static void setCoRelatoreLoggato(CoRelatore coRelatoreLoggato) {
        Utility.coRelatoreLoggato = coRelatoreLoggato;
        Utility.accountLoggato = CORELATORE;
    }

    public static int getAccountLoggato() {
        return accountLoggato;
    }
}