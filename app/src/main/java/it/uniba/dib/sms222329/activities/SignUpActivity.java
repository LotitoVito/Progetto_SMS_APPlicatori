package it.uniba.dib.sms222329.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.fragment.utente.UtenteRegistraFragment;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Utility.replaceFragment(getSupportFragmentManager(), R.id.signUpcontainer, new UtenteRegistraFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(R.string.accedi);
    }
}