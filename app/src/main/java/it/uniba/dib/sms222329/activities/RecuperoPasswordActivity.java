package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.UtenteRegistratoDatabase;

public class RecuperoPasswordActivity extends AppCompatActivity {

    //Variabili o Oggetti
    private Database db;

    //View Items
    private Button signInButton;
    private EditText emailIns1;
    private EditText emailIns2;
    private EditText passswordIns1;
    private EditText passswordIns2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupero_password);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Init();

        signInButton.setOnClickListener(view -> {
            if(!IsEmpty(emailIns1, emailIns2, passswordIns1, passswordIns2))  {
                if (UtenteRegistratoDatabase.ControlloMail(emailIns1.getText().toString().trim(), emailIns2.getText().toString().trim(), db)) {
                    if (UtenteRegistratoDatabase.ResetPassword(passswordIns1.getText().toString().trim(), passswordIns2.getText().toString().trim(), emailIns1.getText().toString().trim(), db)) {
                        Toast.makeText(this, R.string.recupero_password_riuscito, Toast.LENGTH_SHORT).show();
                        Utility.goBack(this);
                    } else {
                        Toast.makeText(this, R.string.recupero_password_password_error, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.recupero_password_email_error, Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(this, R.string.campi_vuoti_errore, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metodo che verifica se i campi obbligatori sono vuoti, nel caso siano vuoti sono contrassegnati;
     * @param emailIns1
     * @param emailIns2
     * @param passswordIns1
     * @param passswordIns2
     * @return Il metodo restituisce true se almeno un campo è vuoto, restituisce false se tutti i campi non sono vuoti
     */
    private boolean IsEmpty(EditText emailIns1, EditText emailIns2, EditText passswordIns1, EditText passswordIns2){
        boolean risultato = false;

        if(Utility.isEmptyTextbox(emailIns1)){
            emailIns1.setError(this.getResources().getString(R.string.campo_obbligatorio));
            risultato = true;
        }
        if(Utility.isEmptyTextbox(emailIns2)){
            emailIns2.setError(this.getResources().getString(R.string.campo_obbligatorio));
            risultato = true;
        }
        if(Utility.isEmptyTextbox(passswordIns1)){
            passswordIns1.setError(this.getResources().getString(R.string.campo_obbligatorio));
            risultato = true;
        }
        if(Utility.isEmptyTextbox(passswordIns2)){
            passswordIns2.setError(this.getResources().getString(R.string.campo_obbligatorio));
            risultato = true;
        }
        return risultato;
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init(){
        db = new Database(this);
        signInButton = findViewById(R.id.recoverPasswordButton);
        emailIns1 = findViewById(R.id.email);
        emailIns2 = findViewById(R.id.email2);
        passswordIns1 = findViewById(R.id.password);
        passswordIns2 = findViewById(R.id.password2);
    }
}