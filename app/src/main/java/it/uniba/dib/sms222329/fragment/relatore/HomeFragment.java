package it.uniba.dib.sms222329.fragment.relatore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Relatore;

public class HomeFragment extends Fragment {

    private Relatore RelatoreLoggato;

    public  HomeFragment(){}
    public HomeFragment(Relatore relatore) {
        this.RelatoreLoggato = relatore;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_relatore, container, false);
        TextView text = view.findViewById(R.id.welcomeHome);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if(selectedItemId != R.id.navigation_home) {
            bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
        }

        if(this.RelatoreLoggato != null) text.setText(this.RelatoreLoggato.getCognome());
        return view;
    }




}