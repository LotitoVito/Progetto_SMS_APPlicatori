package it.uniba.dib.sms222329.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.fragment.relatore.TesistaRelatoreFragment;
import it.uniba.dib.sms222329.fragment.task.DettagliTaskFragment;
import it.uniba.dib.sms222329.fragment.task.ModificaTaskFragment;

public class ListaTaskAdapter extends BaseAdapter {

    //Variabili e Oggetti
    private List<Task> task;
    private LayoutInflater inflater;
    private FragmentManager manager;

    public ListaTaskAdapter(Context context, List<Task> task, FragmentManager manager) {
        this.task = task;
        this.inflater = LayoutInflater.from(context);
        this.manager = manager;
    }

    @Override
    public int getCount() {
        return task.size();
    }

    @Override
    public Object getItem(int i) {
        return task.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.generic_item_with_buttons, viewGroup, false);
        }

        //Titolo
        TextView titolo = convertView.findViewById(R.id.titolo);
        titolo.setText(String.valueOf(task.get(i).getTitolo()));

        //Data Inizio
        TextView dataInizio = convertView.findViewById(R.id.descrizione);
        dataInizio.setText("Data Assegnazione: " + task.get(i).getDataInizio());

        //Stato
        TextView stato = convertView.findViewById(R.id.sottotitolo);
        if(task.get(i).getStato() == Task.ASSEGNATO){
            stato.setText("Assegnato");
        } else if(task.get(i).getStato() == Task.INIZIATO){
            stato.setText("Iniziato");
        } else if(task.get(i).getStato() == Task.IN_COMPLEMAMENTO){
            stato.setText("In completamento");
        } else if(task.get(i).getStato() == Task.IN_REVISIONE){
            stato.setText("In revisione");
        } else if(task.get(i).getStato() == Task.COMPLETATO){
            stato.setText("Completato");
        }

        //EditButton
        Button modifica = convertView.findViewById(R.id.modifica);
        modifica.setOnClickListener(view -> {
            Utility.replaceFragment(manager, R.id.container, new ModificaTaskFragment(task.get(i)));
        });

        //DetailButton
        Button dettagli = convertView.findViewById(R.id.visualizza);
        dettagli.setOnClickListener(view -> {
            Utility.replaceFragment(manager, R.id.container, new DettagliTaskFragment(task.get(i)));
        });

        return convertView;
    }
}
