package it.uniba.dib.sms222329.fragment.task;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaTaskDatabase;
import it.uniba.dib.sms222329.database.TaskDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaTaskAdapter;

public class ListaTaskFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private TesiScelta tesiScelta;

    //View Items
    private ListView listaTask;
    private FloatingActionButton creaTask;

    public ListaTaskFragment(TesiScelta tesiScelta) {
        this.tesiScelta = tesiScelta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        RefreshList();

        creaTask.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new CreaTaskFragment(tesiScelta));
        });
    }

    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        listaTask = getView().findViewById(R.id.lista_task);
        creaTask = getView().findViewById(R.id.crea_task);
    }

    private void RefreshList(){
        List<Task> lista = ListaTaskDatabase.ListaTask(db, tesiScelta.getIdTesiScelta());
        ListaTaskAdapter adapter = new ListaTaskAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
        listaTask.setAdapter(adapter);
    }
}