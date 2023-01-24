package it.uniba.dib.sms222329.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.activities.LoginActivity;
import it.uniba.dib.sms222329.activities.MainActivity;


public class ImpostazioniFragment extends Fragment {


    public ImpostazioniFragment() {
        // Required empty public constructor
    }


    SwitchMaterial toggleDarkMode;
    MaterialButton logOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_impostazioni, container, false);

        toggleDarkMode = view.findViewById(R.id.toggle_dark_mode);
        logOut = view.findViewById(R.id.log_out);

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


    private void saveDarkModePref(boolean isChecked) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("dark_mode", isChecked);
        editor.commit();
    }

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