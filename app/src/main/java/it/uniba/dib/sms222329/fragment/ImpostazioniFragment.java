package it.uniba.dib.sms222329.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Locale;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.activities.LoginActivity;
import it.uniba.dib.sms222329.activities.MainActivity;


public class ImpostazioniFragment extends Fragment {

    //Variabili e Oggetti
    private SwitchMaterial toggleDarkMode;
    private MaterialButton logOut;
    private Spinner spinner;
    private String[] languages;

    public ImpostazioniFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_impostazioni, container, false);

        toggleDarkMode = view.findViewById(R.id.toggle_dark_mode);
        logOut = view.findViewById(R.id.log_out);
        spinner = view.findViewById(R.id.lingua);

        impostaLingua();

        toggleDarkMode.setChecked(loadDarkModePref());

        toggleDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> getActivity().runOnUiThread(() -> {
        AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        saveDarkModePref(isChecked);
        getActivity().recreate();
        }));

        logOut.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        });

        return view;
    }

    private void impostaLingua() {
        languages = new String[]{getResources().getString(R.string.impostaLingua), getResources().getString(R.string.italiano), getResources().getString(R.string.inglese)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedLang = adapterView.getItemAtPosition(i).toString();
                if(selectedLang.equals(getResources().getString(R.string.italiano))){
                    setLocal("it");
                    saveLangPref("it");
                    Intent intent = getActivity().getIntent();
                    getActivity().finish();
                    startActivity(intent);
                }else if(selectedLang.equals(getResources().getString(R.string.inglese))){
                    setLocal("en");
                    saveLangPref("en");
                    Intent intent = getActivity().getIntent();
                    getActivity().finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void saveLangPref(String lingua) {
        // Saving the language preference
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LanguagePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Language", lingua);
        editor.apply();
    }

    public void setLocal(String lingua){
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(new Locale(lingua));
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        onConfigurationChanged(config);
    }

    /**
     * Il metodo salva la preferenza della modalità scura selezionata dall'utente
     * @param isChecked
     */
    private void saveDarkModePref(boolean isChecked) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("dark_mode", isChecked);
        editor.commit();
    }

    /**
     * Il metodo carica la preferenza della modalità scura
     * @return  Restituisce la preferenza della modalità scura
     */
    private boolean loadDarkModePref() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        return prefs.getBoolean("dark_mode", false);
    }

    @Override
    public void onStop() {
        super.onStop();
        saveDarkModePref(toggleDarkMode.isChecked());
    }

}