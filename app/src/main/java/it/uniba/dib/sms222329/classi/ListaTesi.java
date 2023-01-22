package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Comparator;

import it.uniba.dib.sms222329.database.Database;

public class ListaTesi {

    private ArrayList<Tesi> lista;

    public ListaTesi(Database dbClass) {
        String query = "SELECT * FROM tesi;";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()){
            Tesi tesiEstratta = new Tesi();

            tesiEstratta.setId(cursore.getInt(0));
            tesiEstratta.setTitolo(cursore.getString(1));
            tesiEstratta.setArgomenti(cursore.getString(2));
            tesiEstratta.setTempistiche(cursore.getInt(3));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(4));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(5));
            tesiEstratta.setCapacitaRichieste(cursore.getString(6));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(7) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(8));
            tesiEstratta.setIdRelatore(cursore.getInt(9));

            /*Log.d("Tesi ID", String.valueOf(tesiEstratta.getId()));
            Log.d("tesi Titolo", tesiEstratta.getTitolo());
            Log.d("Tesi Argomento", tesiEstratta.getArgomenti());
            Log.d("Tesi Tempistiche", String.valueOf(tesiEstratta.getTempistiche()));
            Log.d("Tesi Mediamin", String.valueOf(tesiEstratta.getMediaVotiMinima()));
            Log.d("Tesi esami mancanti", String.valueOf(tesiEstratta.getEsamiMancantiNecessari()));
            Log.d("Tesi capacità", tesiEstratta.getCapacitaRichieste());
            Log.d("Tesi Stato disp", String.valueOf(tesiEstratta.isStatoDisponibilita()));
            Log.d("Tesi visualizz", String.valueOf(tesiEstratta.getNumeroVisualizzazioni()));
            Log.d("Tesi id Rel", String.valueOf(tesiEstratta.getIdRelatore()));
            Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");*/

            listaTesiEstratte.add(tesiEstratta);
        }
        listaTesiEstratte.sort(Comparator.comparing(Tesi::getNumeroVisualizzazioni));
        this.lista = listaTesiEstratte;
    }



    public ArrayList<Tesi> getLista() {
        return lista;
    }



//ordinamenti vari
    public ArrayList<Tesi> OrdinaPerTitolo(){
        this.lista.sort(Comparator.comparing(Tesi::getTitolo));
        return this.lista;
    }

    public ArrayList<Tesi> OrdinaPerArgomento(){
        this.lista.sort(Comparator.comparing(Tesi::getArgomenti));
        return this.lista;
    }

    public ArrayList<Tesi> OrdinaPerTempistiche(){
        this.lista.sort(Comparator.comparing(Tesi::getTempistiche));
        return this.lista;
    }



//vincoli vari
    public ArrayList<Tesi> vincoloVotoTesi(ArrayList<Tesi> listaTesi, int vincoloVotoMin){

        ArrayList<Tesi> listaRisultato = new ArrayList<Tesi>();

        for (int i=0; i<listaTesi.size(); i++){
            if(listaTesi.get(i).getMediaVotiMinima() > vincoloVotoMin){
                listaRisultato.add(listaTesi.get(i));
            }
        }

        return listaRisultato;
    }


    public ArrayList<Tesi> vincoloEsamiMancanti(ArrayList<Tesi> listaTesi, int vincoloEsamiMancanti){

        ArrayList<Tesi> listaRisultato = new ArrayList<Tesi>();

        for (int i=0; i<listaTesi.size(); i++){
            if(listaTesi.get(i).getEsamiMancantiNecessari() < vincoloEsamiMancanti){
                listaRisultato.add(listaTesi.get(i));
            }
        }

        return listaRisultato;
    }

    public ArrayList<Tesi> vincoloDisponibilità(ArrayList<Tesi> listaTesi, int disponibilità){ //visualizza solo tesi disponibili

        ArrayList<Tesi> listaRisultato = new ArrayList<Tesi>();

        for (int i=0; i<listaTesi.size(); i++){
            if(listaTesi.get(i).getStatoDisponibilita() == true){
                listaRisultato.add(listaTesi.get(i));
            }
        }

        return listaRisultato;
    }


    public ArrayList<Tesi> vincoloRelatore(int idRelatore){ //visualizza solo tesi disponibili

        ArrayList<Tesi> listaRisultato = new ArrayList<>();

        for (int i = 0; lista.size() > i; i++){
            if(idRelatore == lista.get(i).getIdRelatore()){
                listaRisultato.add(lista.get(i));
            }
        }

        return listaRisultato;
    }



}
