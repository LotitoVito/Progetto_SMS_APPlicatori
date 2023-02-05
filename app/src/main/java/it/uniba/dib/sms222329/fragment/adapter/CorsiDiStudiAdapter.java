package it.uniba.dib.sms222329.fragment.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Spinner;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.database.Database;

public class CorsiDiStudiAdapter extends BaseAdapter {

    //Variabili e Oggetti
    private List<String> corsi;
    private Context context;
    private LayoutInflater inflater;
    private boolean modifica;
    private boolean oldCheckBox;        //Serve per capire se settare attive le checkbox dei corsi già registrati

    public CorsiDiStudiAdapter(Context context, List<String> corsi) {
        this.corsi = corsi;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.modifica = false;
    }

    public CorsiDiStudiAdapter(Context context, List<String> corsi, boolean oldCheckBox) {
        this.corsi = corsi;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.modifica = true;
        this.oldCheckBox = oldCheckBox;
    }

    @Override
    public int getCount() {
        return corsi.size();
    }

    @Override
    public Object getItem(int i) {
        return corsi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.checkbox_corsi_item, viewGroup, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        checkBox.setText(corsi.get(i));

        //Se è un'operazione di modifica e devo settare i corsi registrati attivi
        if(this.modifica && this.oldCheckBox){
            Database db = new Database(this.context);
            for(int j = 0; j< Utility.relatoreLoggato.getCorsiRelatore().size(); j++){
                Cursor cursor = db.getReadableDatabase().rawQuery("SELECT cs." + Database.CORSOSTUDI_NOME + " FROM " + Database.CORSOSTUDI + " cs, " + Database.UNIVERSITACORSO + " uc WHERE uc." + Database.UNIVERSITACORSO_CORSOID + " = cs." + Database.CORSOSTUDI_ID + " AND uc." + Database.UNIVERSITACORSO_ID + "='"+ Utility.relatoreLoggato.getCorsiRelatore().get(j) +"';", null);
                while(cursor.moveToNext()){
                    if(checkBox.getText().toString().compareTo(cursor.getString(cursor.getColumnIndexOrThrow(Database.CORSOSTUDI_NOME)))==0){
                        checkBox.setChecked(true);
                        break;
                    }
                }
            }
        }

        return convertView;
    }

}
