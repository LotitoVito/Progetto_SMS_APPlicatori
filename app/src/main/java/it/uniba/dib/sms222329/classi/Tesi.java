package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.sql.Date;
import java.util.ArrayList;

import it.uniba.dib.sms222329.activities.MainActivity;
import it.uniba.dib.sms222329.database.Database;

public class Tesi {

    private int id;
    private String titolo;
    private String argomenti;
    private int tempistiche;
    private float mediaVotiMinima;
    private int esamiMancantiNecessari;
    private String capacitaRichieste;
    private boolean statoDisponibilita;
    private int numeroVisualizzazioni;
    private int idRelatore;

    public Tesi(int id, String titolo, String argomenti, int tempistiche, float mediaVotiMinima, int esamiMancantiNecessari, String capacitaRichieste, boolean statoDisponibilita, int numeroVisualizzazioni, int idRelatore) {
        this.id = id;
        this.titolo = titolo;
        this.argomenti = argomenti;
        this.tempistiche = tempistiche;
        this.mediaVotiMinima = mediaVotiMinima;
        this.esamiMancantiNecessari = esamiMancantiNecessari;
        this.capacitaRichieste = capacitaRichieste;
        this.statoDisponibilita = statoDisponibilita;
        this.numeroVisualizzazioni = numeroVisualizzazioni;
        this.idRelatore = idRelatore;
    }

    public Tesi() {
    }

    //Usato per al registrazione
    public Tesi(String titolo, String argomenti, boolean statoDisponibilita, int idRelatore, int tempistiche,
                float mediaVotiMinima, int esamiMancantiNecessari, String capacitaRichieste) {
        this.titolo = titolo;
        this.argomenti = argomenti;
        this.statoDisponibilita = statoDisponibilita;
        this.numeroVisualizzazioni = 0;
        this.idRelatore = idRelatore;
        this.tempistiche = tempistiche;
        this.mediaVotiMinima = mediaVotiMinima;
        this.esamiMancantiNecessari = esamiMancantiNecessari;
        this.capacitaRichieste = capacitaRichieste;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {return titolo;}

    public void setTitolo(String titolo) {this.titolo = titolo;}

    public String getArgomenti() {
        return argomenti;
    }

    public void setArgomenti(String argomenti) {
        this.argomenti = argomenti;
    }

    public boolean isStatoDisponibilita() {
        return statoDisponibilita;
    }

    public void setStatoDisponibilita(boolean statoDisponibilita) {this.statoDisponibilita = statoDisponibilita;}
    public boolean getStatoDisponibilita() {return statoDisponibilita;}

    public int getNumeroVisualizzazioni() {
        return numeroVisualizzazioni;
    }

    public void setNumeroVisualizzazioni(int numeroVisualizzazioni) {this.numeroVisualizzazioni = numeroVisualizzazioni;}

    public int getIdRelatore() {
        return idRelatore;
    }

    public void setIdRelatore(int idRelatore) {
        this.idRelatore = idRelatore;
    }

    public int getTempistiche() {return tempistiche;}

    public void setTempistiche(int tempistiche) {this.tempistiche = tempistiche;}

    public float getMediaVotiMinima() {return mediaVotiMinima;}

    public void setMediaVotiMinima(float mediaVotiMinima) {this.mediaVotiMinima = mediaVotiMinima;}

    public int getEsamiMancantiNecessari() {return esamiMancantiNecessari;}

    public void setEsamiMancantiNecessari(int esamiMancantiNecessari) {this.esamiMancantiNecessari = esamiMancantiNecessari;}

    public String getCapacitaRichieste() {return capacitaRichieste;}

    public void setCapacitaRichieste(String capacitaRichieste) {this.capacitaRichieste = capacitaRichieste;}

    public boolean RegistrazioneTesi(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesi = new ContentValues();

        cvTesi.put("titolo", this.titolo);
        cvTesi.put("argomento", this.argomenti);
        cvTesi.put("tempistiche", this.tempistiche);
        cvTesi.put("media_voto_minima", this.mediaVotiMinima);
        cvTesi.put("esami_necessari", this.esamiMancantiNecessari);
        cvTesi.put("skill_richieste", this.capacitaRichieste);
        cvTesi.put("stato", this.statoDisponibilita);
        cvTesi.put("visualizzazioni", this.numeroVisualizzazioni);
        cvTesi.put("relatore_id", this.idRelatore);

        try{
            long insertTesi = db.insert("tesi", null, cvTesi);
            if(insertTesi != -1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean ModificaTesi(Database dbClass, String argomenti, boolean statoDisponibilita, int tempistiche,
                                float mediaVotiMinima, int esamiMancantiNecessari, String capacitaRichieste){
        this.argomenti = argomenti;
        this.statoDisponibilita = statoDisponibilita;
        this.tempistiche = tempistiche;
        this.mediaVotiMinima = mediaVotiMinima;
        this.esamiMancantiNecessari = esamiMancantiNecessari;
        this.capacitaRichieste = capacitaRichieste;

        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesi = new ContentValues();

        cvTesi.put("titolo", this.titolo);
        cvTesi.put("argomento", this.argomenti);
        cvTesi.put("stato", this.statoDisponibilita);
        cvTesi.put("tempistiche", this.tempistiche);
        cvTesi.put("media_voto_minima", this.mediaVotiMinima);
        cvTesi.put("esami_necessari", this.esamiMancantiNecessari);
        cvTesi.put("skill_richieste", this.capacitaRichieste);

        try{
            long updateTesi = db.update("Tesi", cvTesi, "IDTesi = " + this.id, null);
            if(updateTesi != -1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Bitmap QRGenerator(){
        MultiFormatWriter writer = new MultiFormatWriter();
        Bitmap bitmap = null;
        try{
            BitMatrix  matrix = writer. encode(String.valueOf(this.id), BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder encoder = new BarcodeEncoder();
            bitmap = encoder.createBitmap(matrix);
        }catch(WriterException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public void incrementaVisualizzazioni(Database dbClass){
        this.numeroVisualizzazioni++;
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("visualizzazioni", this.numeroVisualizzazioni);
        long risultato = db.update("tesi", cv, "id = '"+ this.id +"';", null);
    }

    public Cursor visualizzaTesi(Database dbClass, int id){
        SQLiteDatabase db=dbClass.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tesi WHERE id='"+id+"';", null);
        return cursor;
    }
}
