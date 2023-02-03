package it.uniba.dib.sms222329;

import android.view.autofill.AutofillValue;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;

import java.time.format.DateTimeFormatter;

public class Utility {

    public static final int TESISTA = 1;
    public static final int RELATORE = 2;
    public static final int CORELATORE = 3;
    public static DateTimeFormatter convertFromStringDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter convertFromStringDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter showDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DateTimeFormatter showDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

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
}