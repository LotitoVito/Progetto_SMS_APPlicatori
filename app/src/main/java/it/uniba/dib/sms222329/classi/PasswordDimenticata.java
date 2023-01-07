package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.database.Database;

public class PasswordDimenticata {


    public boolean ControlloMail(String email1, String email2, Database dbClass){     //ritorna vero se le email corrispondono e se c'è corrispondenza nel db
        if (email1 == email2){
            String query = "SELECT Email FROM Utenti WHERE Email = '" + email1 + "';";
            SQLiteDatabase db = dbClass.getReadableDatabase();
            Cursor cursore = db.rawQuery(query, null);

            if (cursore.getCount()== 1){
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    } // fine controllomail

    public boolean ResetPassword(String password1, String password2, Database dbClass){
        // trova come aggiornare db query on update on cascade
        if(password1 == password2){
            //String aggiornaPassword = "Cerca nei documenti";
            SQLiteDatabase db = dbClass.getWritableDatabase();
            //db.execSQL(aggiornaPassword);

            return true; // se true messaggio modifica effettuata di rit
        }
        return false;
    }


}
