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

    //Usato per la tesi scelta
    public Tesi(int id, String titolo, String argomenti, int idRelatore) {
        this.id = id;
        this.titolo = titolo;
        this.argomenti = argomenti;
        this.idRelatore = idRelatore;
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

    public void ModificaTesi(String titolo, String argomenti, boolean statoDisponibilita, int tempistiche,
                                float mediaVotiMinima, int esamiMancantiNecessari, String capacitaRichieste){
        this.titolo = titolo;
        this.argomenti = argomenti;
        this.tempistiche = tempistiche;
        this.mediaVotiMinima = mediaVotiMinima;
        this.esamiMancantiNecessari = esamiMancantiNecessari;
        this.capacitaRichieste = capacitaRichieste;
        this.statoDisponibilita = statoDisponibilita;
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

    public void incrementaVisualizzazioni(){
        this.numeroVisualizzazioni++;
    }
}
