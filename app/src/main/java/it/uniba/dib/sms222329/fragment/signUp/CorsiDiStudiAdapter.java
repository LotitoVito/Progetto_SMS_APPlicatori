package it.uniba.dib.sms222329.fragment.signUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;

import java.util.List;

import it.uniba.dib.sms222329.R;

public class CorsiDiStudiAdapter extends BaseAdapter {

    private List<String> corsi;
    private LayoutInflater inflater;

    public CorsiDiStudiAdapter(Context context, List<String> corsi) {
        this.corsi = corsi;
        this.inflater = LayoutInflater.from(context);
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

        return convertView;
    }

}
