package it.uniba.dib.sms222329.fragment.relatore;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.MotionLabel;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.FileUpload;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesiSceltaDatabase;
import it.uniba.dib.sms222329.fragment.task.CreaTaskFragment;
import it.uniba.dib.sms222329.fragment.task.ListaTaskFragment;

public class TesistaRelatoreFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private TesiScelta tesiScelta;
    private boolean richiesta;  //Usata per determinare se la view deve essere aprte come richiesta da accettare da parte del corelatore

    //View Items
    private TextView tesista;
    private TextView titoloTesi;
    private TextView argomentoTesi;
    private TextView riassunto;
    private TextView tempistiche;
    private TextView esamiMancanti;
    private TextView capacitaRichiesta;
    private TextView capacitaEffettive;
    private TextView dataConsegna;
    private TextView corelatore;
    private TextView emailCorelatore;
    private TextInputEditText richiestaCorelatore;
    private Button aggiungiCorelatore;
    private Button rimuoviCorelatore;
    private TextView creaTask;
    private TextView tesiUpload;
    private TextView mostraTask;
    private Button scaricaTesi;
    private Button caricaTesi;
    private MotionLabel labelCorelatore;
    //Firebase
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    public TesistaRelatoreFragment(TesiScelta tesiScelta) {
        this.tesiScelta = tesiScelta;
    }

    public TesistaRelatoreFragment(TesiScelta tesiScelta, boolean richiesta) {
        this.tesiScelta = tesiScelta;
        this.richiesta = richiesta;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesi, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetTextAll();

        aggiungiCorelatore.setOnClickListener(view -> {
            if(Utility.accountLoggato == Utility.CORELATORE){
                if(tesiScelta.AccettaRichiesta(db)){
                    Toast.makeText(getActivity().getApplicationContext(), "Richiesta accettata", Toast.LENGTH_SHORT).show();
                    Utility.closeFragment(getActivity());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), " Operazione fallita", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(db.VerificaDatoEsistente("SELECT " + Database.UTENTI_EMAIL + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_EMAIL + "='" + richiestaCorelatore.getText().toString().trim() + "';")){
                    if(tesiScelta.AggiungiCorelatore(db, richiestaCorelatore.getText().toString().trim())){
                        Toast.makeText(getActivity().getApplicationContext(), "Richiesta inviata con successo", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Il corelatore inserito non esiste o errore imprevisto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Il corelatore inserito non esiste", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rimuoviCorelatore.setOnClickListener(view -> {
            if(Utility.accountLoggato == Utility.CORELATORE){
                if(tesiScelta.RifiutaRichiesta(db)){
                    Toast.makeText(getActivity().getApplicationContext(), "Richiesta rifiutata", Toast.LENGTH_SHORT).show();
                    Utility.closeFragment(getActivity());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(tesiScelta.RimuoviCorelatore(db)){
                    Toast.makeText(getActivity().getApplicationContext(), "Corelatore rimosso con successo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                }
            }
        });

        creaTask.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new CreaTaskFragment(tesiScelta));
        });

        mostraTask.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ListaTaskFragment(tesiScelta));
        });

        scaricaTesi.setOnClickListener(view -> {

        });

        caricaTesi.setOnClickListener(view -> {
            caricaFile();
        });
    }

    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        tesista = getView().findViewById(R.id.nomeStudente);
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomentoTesi = getView().findViewById(R.id.argomentoTesi);
        riassunto = getView().findViewById(R.id.riassunto);
        tempistiche = getView().findViewById(R.id.tempistiche);
        esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        capacitaEffettive = getView().findViewById(R.id.capacitaEffettive);
        dataConsegna = getView().findViewById(R.id.dataConsegna);
        corelatore = getView().findViewById(R.id.corelatore);
        emailCorelatore = getView().findViewById(R.id.email_corelatore);
        richiestaCorelatore = getView().findViewById(R.id.email_co);
        aggiungiCorelatore = getView().findViewById(R.id.aggiungi_corelatore);
        rimuoviCorelatore = getView().findViewById(R.id.rimuovi_corelatore);
        creaTask = getView().findViewById(R.id.crea_task);
        mostraTask = getView().findViewById(R.id.visualizza_task);
        scaricaTesi = getView().findViewById(R.id.scarica);
        caricaTesi = getView().findViewById(R.id.carica);
        labelCorelatore = getView().findViewById(R.id.label_corelatore);
        tesiUpload = getView().findViewById(R.id.tesi_upload);

        if (Utility.accountLoggato == Utility.GUEST){
            SettaPerGuest();
        } else if(Utility.accountLoggato != Utility.CORELATORE){
            SettaPerCorelatore();
        } else {
            SettaGenerale();
        }
    }

    private void SetTextAll(){
        //Corelatore
        Cursor cursore = db.RicercaDato("SELECT " + Database.UTENTI_NOME + ", " + Database.UTENTI_COGNOME + ", " + Database.UTENTI_EMAIL + " FROM " + Database.CORELATORE + " c, " + Database.UTENTI + " u " +
                "WHERE u." + Database.UTENTI_ID + "=c." + Database.CORELATORE_UTENTEID + " AND c." + Database.CORELATORE_ID + "=" + tesiScelta.getIdCorelatore() + ";");
        Log.d("test", String.valueOf(tesiScelta.getIdCorelatore()));
        if(cursore.moveToFirst()){
            if(tesiScelta.getStatoCorelatore()==TesiScelta.ACCETTATO){
                corelatore.setText(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_NOME)));
                emailCorelatore.setText(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_EMAIL)));
            } else if(tesiScelta.getStatoCorelatore()==TesiScelta.IN_ATTESA) {
                corelatore.setText("In attesa di approvazione");
                emailCorelatore.setText(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_EMAIL)));
            }
        }
    }

    private void SettaGenerale(){
        if(tesiScelta.getStatoCorelatore() == TesiScelta.ACCETTATO || tesiScelta.getStatoCorelatore() == TesiScelta.IN_ATTESA){
            aggiungiCorelatore.setVisibility(View.GONE);
            richiestaCorelatore.setVisibility(View.GONE);
            rimuoviCorelatore.setVisibility(View.VISIBLE);
        } else{
            aggiungiCorelatore.setVisibility(View.VISIBLE);
            rimuoviCorelatore.setVisibility(View.GONE);
            corelatore.setVisibility(View.GONE);
            emailCorelatore.setVisibility(View.GONE);
        }
    }

    private void SettaPerCorelatore(){
        labelCorelatore.setVisibility(View.GONE);
        corelatore.setVisibility(View.GONE);
        emailCorelatore.setVisibility(View.GONE);
        richiestaCorelatore.setVisibility(View.GONE);
        if (richiesta) {
            aggiungiCorelatore.setText("Accetta");
            rimuoviCorelatore.setText("Rifiuta");
            creaTask.setVisibility(View.GONE);
            mostraTask.setVisibility(View.GONE);
            scaricaTesi.setVisibility(View.GONE);
            caricaTesi.setVisibility(View.GONE);
        } else {
            aggiungiCorelatore.setVisibility(View.GONE);
            rimuoviCorelatore.setVisibility(View.GONE);
        }
    }

    private void SettaPerGuest(){
        richiestaCorelatore.setVisibility(View.GONE);
        aggiungiCorelatore.setVisibility(View.GONE);
        rimuoviCorelatore.setVisibility(View.GONE);
        creaTask.setVisibility(View.GONE);
        mostraTask.setVisibility(View.GONE);
        caricaTesi.setVisibility(View.GONE);
    }

    private void caricaFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF Files..."), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode== RESULT_OK && data!= null && data.getData()!=null){
            uploadFiles(data.getData());
        }
    }

    private void uploadFiles(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity().getApplicationContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("Uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    Uri url = uriTask.getResult();
                    FileUpload file = new FileUpload(Utility.relatoreLoggato.getIdUtente(), Utility.relatoreLoggato.getNome(), url.toString(), LocalDate.now());
                    tesiUpload.setText(file.toString());
                    databaseReference.child(databaseReference.push().getKey()).setValue(file);

                    Toast.makeText(getActivity().getApplicationContext(), "File Uploaded!", Toast.LENGTH_SHORT);
                    progressDialog.dismiss();

                }).addOnProgressListener(snapshot -> {
                    double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded:"+(int)progress+"%");
                });

    }
}