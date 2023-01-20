package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import it.uniba.dib.sms222329.database.Database;

public class PasswordDimenticata {


    public boolean ControlloMail(String email1, String email2, Database dbClass){     //ritorna vero se le email corrispondono e se c'è corrispondenza nel db
        String query = "SELECT email FROM utenti WHERE email = '" + email1 + "';";
        if (email1.equals(email2) && dbClass.VerificaDatoEsistente(query)){
            Log.d("email1", email1.toString());
            Log.d("email2", email2.toString());

                return true;
            }
                return false;
        }


    public boolean ResetPassword(String password1, String password2, String email, Database dbClass){
        if(password1.equals(password2)){
            String aggiornaPassword = "UPDATE utenti SET password = '" + password1 + "' WHERE email = '" + email + "';";
            SQLiteDatabase db = dbClass.getWritableDatabase();
            db.execSQL(aggiornaPassword);
            return true; // se true messaggio modifica effettuata di rit
        }
        return false;
    }
}
