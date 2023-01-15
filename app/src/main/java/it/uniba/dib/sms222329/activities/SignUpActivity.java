package it.uniba.dib.sms222329.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.signUp.SignUpFragment;

public class SignUpActivity extends AppCompatActivity {
    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.signUpcontainer, new SignUpFragment(db));
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(R.string.accedi);
    }
}