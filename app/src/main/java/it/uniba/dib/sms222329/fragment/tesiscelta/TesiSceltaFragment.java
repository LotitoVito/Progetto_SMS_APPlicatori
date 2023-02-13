package it.uniba.dib.sms222329.fragment.tesiscelta;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.MotionLabel;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.FileUpload;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesiSceltaDatabase;
import it.uniba.dib.sms222329.fragment.task.TaskCreaFragment;
import it.uniba.dib.sms222329.fragment.task.TaskListaFragment;

public class TesiSceltaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private TesiScelta tesiScelta;
    private boolean richiesta;  //Usata per determinare se la view deve essere aprte come richiesta da accettare da parte del corelatore
    private boolean operazioneDownload;

    //View Items
    private TextView tesista;
    private TextView titoloTesi;
    private TextView argomentoTesi;
    private TextView universita;
    private TextView corso;
    private TextView riassunto;
    private TextView tempistiche;
    private TextView esamiMancanti;
    private TextView media;
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
    private MotionLabel labelDataConsegna;
    private MotionLabel labelaAbstract;

    //Firebase
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private  FileUpload file;

    public TesiSceltaFragment(TesiScelta tesiScelta) {
        this.tesiScelta = tesiScelta;
    }

    public TesiSceltaFragment(TesiScelta tesiScelta, boolean richiesta) {
        this.tesiScelta = tesiScelta;
        this.richiesta = richiesta;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inizializzaFirebase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesi_scelta, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetTextAll();

        aggiungiCorelatore.setOnClickListener(view -> {
            if(Utility.accountLoggato == Utility.CORELATORE){
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.conferma)
                        .setMessage(R.string.richiesta_collaboarzione_richiesta_accetta)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AccettaCollaborazione();
                            }
                        })
                        .setNegativeButton(R.string.indietro, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            } else {
                if(db.VerificaDatoEsistente("SELECT " + Database.UTENTI_EMAIL + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_EMAIL + "='" + richiestaCorelatore.getText().toString().trim() + "';")){
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.conferma)
                            .setMessage(R.string.richiesta_collaboarzione_richiesta)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AggiungiCorelatore();
                                }
                            })
                            .setNegativeButton(R.string.indietro, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.corelatore_errore, Toast.LENGTH_SHORT).show();
                }
            }
        });

        rimuoviCorelatore.setOnClickListener(view -> {
            if(Utility.accountLoggato == Utility.CORELATORE){
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.conferma)
                        .setMessage(R.string.richiesta_collaboarzione_richiesta_rifiuta)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RifiutaCollaborazione();
                            }
                        })
                        .setNegativeButton(R.string.indietro, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            } else {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.conferma)
                        .setMessage(R.string.corelatore_rimosso_richiesta)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RimuoviCorelatore();
                            }
                        })
                        .setNegativeButton(R.string.indietro, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        creaTask.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TaskCreaFragment(tesiScelta));
        });

        mostraTask.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TaskListaFragment(tesiScelta));
        });

        scaricaTesi.setOnClickListener(view -> {
            if(file!=null){
                if(Utility.CheckStorage(getActivity(), this)) {
                    downloadFile();
                } else {
                    operazioneDownload = true;
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.no_file, Toast.LENGTH_SHORT).show();
            }
        });

        caricaTesi.setOnClickListener(view -> {
            if(Utility.CheckStorage(getActivity(), this)){
                caricaFile();
            } else {
                operazioneDownload = false;
            }
        });
    }

    /**
     * Metodo per la richiesta di collaborazione ad un corelatore
     */
    private void AggiungiCorelatore(){
        if(tesiScelta.AggiungiCorelatore(db, richiestaCorelatore.getText().toString().trim())){
            Toast.makeText(getActivity().getApplicationContext(), R.string.richiesta_collaboarzione_inviata, Toast.LENGTH_SHORT).show();
            this.onResume();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.richiesta_collaboarzione_errore, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo per rimuovere un corelatore dalla tesi scelta o per rimuovere la richiesta di collaborazione
     */
    private void RimuoviCorelatore(){
        if(tesiScelta.RimuoviCorelatore(db)){
            Toast.makeText(getActivity().getApplicationContext(), R.string.corelatore_rimosso_successo, Toast.LENGTH_SHORT).show();
            this.onResume();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.operazione_fallita, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo per accettare la richiesta di collaborazione
     */
    private void AccettaCollaborazione(){
        if(tesiScelta.AccettaRichiesta(db)){
            Toast.makeText(getActivity().getApplicationContext(), R.string.richiesta_collaboarzione_accettata, Toast.LENGTH_SHORT).show();
            Utility.goBack(getActivity());
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.operazione_fallita, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo per rifiutare la richiesta di collaborazione
     */
    private void RifiutaCollaborazione(){
        if(tesiScelta.RifiutaRichiesta(db)){
            Toast.makeText(getActivity().getApplicationContext(), R.string.richiesta_collaboarzione_rifiutata, Toast.LENGTH_SHORT).show();
            Utility.goBack(getActivity());
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.operazione_fallita, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==Utility.REQUEST_PERMESSO_STORAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (operazioneDownload){
                downloadFile();
            } else {
                caricaFile();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.permesso_negato, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        tesista = getView().findViewById(R.id.nomeStudente);
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomentoTesi = getView().findViewById(R.id.argomentoTesi);
        universita = getView().findViewById(R.id.universita);
        corso = getView().findViewById(R.id.corso);
        riassunto = getView().findViewById(R.id.riassunto);
        tempistiche = getView().findViewById(R.id.tempistiche);
        esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        media = getView().findViewById(R.id.media);
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
        labelDataConsegna= getView().findViewById(R.id.label_data_consegna);
        labelaAbstract = getView().findViewById(R.id.label_abstract);

        //Se manca il file togli pulsante download
        if(getLastUpload()){
            scaricaTesi.setVisibility(View.GONE);
        }

        //Se la tesi è stata consegnata toglio pulsante crea task
        if(tesiScelta.getDataPubblicazione() != null){
            creaTask.setVisibility(View.GONE);
        }

        //Tipi di setting
        if (Utility.accountLoggato == Utility.GUEST){
            SettaPerGuest();
        } else if(Utility.accountLoggato == Utility.CORELATORE){
            SettaPerCorelatore();
        } else {
            SettaGenerale();
        }
    }

    /**
     * Imposta il testo per ogni elemento in base ai casi
     */
    private void SetTextAll(){
        //Tesista
        Cursor cursorTesista = db.RicercaDato("SELECT u." + Database.UTENTI_NOME + ", u." + Database.UTENTI_COGNOME + ", t." + Database.TESISTA_ESAMIMANCANTI + ", t." + Database.TESISTA_MEDIAVOTI + ", t." + Database.TESISTA_MATRICOLA +
                " FROM " + Database.UTENTI + " u, " + Database.TESISTA + " t " +
                " WHERE u." + Database.UTENTI_ID + "=t." + Database.TESISTA_UTENTEID + " AND t." + Database.TESISTA_ID + "=" + tesiScelta.getIdTesista() + ";");
        cursorTesista.moveToFirst();
        tesista.setText(cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.UTENTI_NOME)) + " " + cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.TESISTA_MATRICOLA)));

        //Tesi
        Cursor cursorTesi = db.RicercaDato("SELECT t." + Database.TESI_TITOLO + ", t." + Database.TESI_ARGOMENTO + ", t." + Database.TESI_TEMPISTICHE + ", t." + Database.TESI_ESAMINECESSARI + ", t." + Database.TESI_SKILLRICHIESTE + ", t." + Database.TESI_MEDIAVOTOMINIMA + ", t." + Database.TESI_UNIVERSITACORSOID +
                " FROM " + Database.TESI + " t WHERE t." + Database.TESI_ID + "=" + tesiScelta.getIdTesi() + ";");
        cursorTesi.moveToFirst();
        titoloTesi.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_TITOLO)));
        argomentoTesi.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_ARGOMENTO)));
        tempistiche.setText(String.valueOf(cursorTesi.getInt(cursorTesi.getColumnIndexOrThrow(Database.TESI_TEMPISTICHE))));
        esamiMancanti.setText(getActivity().getApplicationContext().getResources().getString(R.string.requisito_richiesto) + " : " + cursorTesi.getInt(cursorTesi.getColumnIndexOrThrow(Database.TESI_ESAMINECESSARI)) +
                "\n" + getActivity().getApplicationContext().getResources().getString(R.string.tesista) + " : " + cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.TESISTA_ESAMIMANCANTI)));
        media.setText(getActivity().getApplicationContext().getResources().getString(R.string.requisito_richiesto) + " : " + cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_MEDIAVOTOMINIMA)) +
                "\n" + getActivity().getApplicationContext().getResources().getString(R.string.tesista) + " : " + cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.TESISTA_MEDIAVOTI)));
        capacitaRichiesta.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_SKILLRICHIESTE)));

        //Corelatore
        Cursor cursoreCorelatore = db.RicercaDato("SELECT " + Database.UTENTI_NOME + ", " + Database.UTENTI_COGNOME + ", " + Database.UTENTI_EMAIL + " FROM " + Database.CORELATORE + " c, " + Database.UTENTI + " u " +
                "WHERE u." + Database.UTENTI_ID + "=c." + Database.CORELATORE_UTENTEID + " AND c." + Database.CORELATORE_ID + "=" + tesiScelta.getIdCorelatore() + ";");
        if(cursoreCorelatore.moveToFirst()){
            if(tesiScelta.getStatoCorelatore()==TesiScelta.ACCETTATO){
                corelatore.setText(cursoreCorelatore.getString(cursoreCorelatore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursoreCorelatore.getString(cursoreCorelatore.getColumnIndexOrThrow(Database.UTENTI_NOME)));
                emailCorelatore.setText(cursoreCorelatore.getString(cursoreCorelatore.getColumnIndexOrThrow(Database.UTENTI_EMAIL)));
            } else if(tesiScelta.getStatoCorelatore()==TesiScelta.IN_ATTESA) {
                corelatore.setText(getActivity().getApplicationContext().getResources().getString(R.string.attesa_approvazione));
                emailCorelatore.setText(cursoreCorelatore.getString(cursoreCorelatore.getColumnIndexOrThrow(Database.UTENTI_EMAIL)));
            }
        }

        //TesiScelta
        if(tesiScelta.getRiassunto() != null){
            riassunto.setText(tesiScelta.getRiassunto());
        } else {
            riassunto.setVisibility(View.GONE);
            labelaAbstract.setVisibility(View.GONE);
        }

        //Tesi completata
        if(tesiScelta.getDataPubblicazione() != null){
            dataConsegna.setText(tesiScelta.getDataPubblicazione().format(Utility.showDate));
        } else {
            dataConsegna.setVisibility(View.GONE);
            labelDataConsegna.setVisibility(View.GONE);
        }

        //Capacita
        capacitaEffettive.setText(tesiScelta.getCapacitàStudente());

        //Universita e Corso
        Cursor cursorUniversita = db.RicercaDato("SELECT u." + Database.UNIVERSITA_NOME +
                " FROM " + Database.UNIVERSITACORSO + " uc, " + Database.UNIVERSITA + " u " +
                " WHERE uc." + Database.UNIVERSITACORSO_UNIVERSITAID + "=u." + Database.UNIVERSITA_ID +
                " AND uc." + Database.UNIVERSITACORSO_ID + "=" + cursorTesi.getInt(cursorTesi.getColumnIndexOrThrow(Database.TESI_UNIVERSITACORSOID)) + ";");
        cursorUniversita.moveToFirst();
        universita.setText(cursorUniversita.getString(cursorUniversita.getColumnIndexOrThrow(Database.UNIVERSITA_NOME)));
        Cursor cursorCorso = db.RicercaDato("SELECT cs." + Database.CORSOSTUDI_NOME +
                " FROM " + Database.UNIVERSITACORSO + " uc, " + Database.CORSOSTUDI + " cs " +
                " WHERE uc." + Database.UNIVERSITACORSO_CORSOID + "=cs." + Database.CORSOSTUDI_ID +
                " AND uc." + Database.UNIVERSITACORSO_ID + "=" + cursorTesi.getInt(cursorTesi.getColumnIndexOrThrow(Database.TESI_UNIVERSITACORSOID)) + ";");
        cursorCorso.moveToFirst();
        corso.setText(cursorCorso.getString(cursorCorso.getColumnIndexOrThrow(Database.CORSOSTUDI_NOME)));
    }

    /**
     * Setta la view per un utente loggato non specifico
     */
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

    /**
     * Setta la view per un corelatore loggato
     */
    private void SettaPerCorelatore(){
        labelCorelatore.setVisibility(View.GONE);
        corelatore.setVisibility(View.GONE);
        emailCorelatore.setVisibility(View.GONE);
        richiestaCorelatore.setVisibility(View.GONE);
        if (richiesta) {
            aggiungiCorelatore.setText(getActivity().getApplicationContext().getResources().getString(R.string.accetta));
            rimuoviCorelatore.setText(getActivity().getApplicationContext().getResources().getString(R.string.rifiuta));
            creaTask.setVisibility(View.GONE);
            mostraTask.setVisibility(View.GONE);
            scaricaTesi.setVisibility(View.GONE);
            caricaTesi.setVisibility(View.GONE);
        } else {
            aggiungiCorelatore.setVisibility(View.GONE);
            rimuoviCorelatore.setVisibility(View.GONE);
        }
    }

    /**
     * Setta la view per un guest loggato
     */
    private void SettaPerGuest(){
        richiestaCorelatore.setVisibility(View.GONE);
        aggiungiCorelatore.setVisibility(View.GONE);
        rimuoviCorelatore.setVisibility(View.GONE);
        creaTask.setVisibility(View.GONE);
        mostraTask.setVisibility(View.GONE);
        caricaTesi.setVisibility(View.GONE);
    }

    /**
     * Metodo per inizializzare Firebase
     */
    private void inizializzaFirebase() {
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance("https://laureapp-f0334-default-rtdb.europe-west1.firebasedatabase.app/").getReference("uploads");
    }

    /**
     * Metodo per recuperare l'ultimo file caricato su Firebase se è stato caricato
     * @return  true se trova un file, altrimenti false
     */
    private boolean getLastUpload() {
        file = null;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    if (TesiSceltaDatabase.DownloadTesiScelta(db, tesiScelta).compareTo(data.getKey())==0){
                        FileUpload genericFile = data.getValue(FileUpload.class);
                        tesiUpload.setText(genericFile.toString());
                        file = genericFile;
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tesiUpload.setText("Errore");
            }
        });

        if(file == null){
            tesiUpload.setText("Nessun caricamento");
            return false;
        }
        return true;
    }

    /**
     * Permette di aprire il picker di file
     */
    private void caricaFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getActivity().getApplicationContext().getResources().getString(R.string.seleziona_pdf)), Utility.REQUEST_CARICA_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Utility.REQUEST_CARICA_FILE && resultCode== RESULT_OK && data!= null && data.getData()!=null){
            uploadFiles(data.getData());
        }
    }

    /**
     * Metodo per l'upload di file su Firebase
     * @param data
     */
    private void uploadFiles(Uri data) {
        //final ProgressDialog progressDialog = new ProgressDialog(getActivity().getApplicationContext());
        //progressDialog.setTitle("Uploading...");
        //progressDialog.show();

        StorageReference reference = storageReference.child("Uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    Uri url = uriTask.getResult();
                    String downloadKey;
                    if(file==null){
                        downloadKey = databaseReference.push().getKey();
                    }else{
                        downloadKey = TesiSceltaDatabase.DownloadTesiScelta(db,tesiScelta);
                        eliminaFile(file.getUrl());
                    }
                    if(Utility.accountLoggato == Utility.TESISTA) file = new FileUpload(Utility.tesistaLoggato.getIdUtente(), Utility.tesistaLoggato.getNome()+" "+Utility.tesistaLoggato.getCognome(), url.toString(), new Date(), getActivity().getApplicationContext());
                    if(Utility.accountLoggato == Utility.RELATORE) file = new FileUpload(Utility.relatoreLoggato.getIdUtente(), Utility.relatoreLoggato.getNome()+" "+Utility.relatoreLoggato.getCognome(), url.toString(), new Date(), getActivity().getApplicationContext());
                    if(Utility.accountLoggato == Utility.CORELATORE) file = new FileUpload(Utility.coRelatoreLoggato.getIdUtente(), Utility.coRelatoreLoggato.getNome()+" "+Utility.coRelatoreLoggato.getCognome(), url.toString(), new Date(), getActivity().getApplicationContext());
                    tesiUpload.setText(file.toString());
                    databaseReference.child(downloadKey).setValue(file);
                    TesiSceltaDatabase.UploadTesiScelta(db, tesiScelta,downloadKey);
                    scaricaTesi.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity().getApplicationContext(), R.string.file_caricato_successo, Toast.LENGTH_SHORT);
                    //progressDialog.dismiss();
                }).addOnProgressListener(snapshot -> {
                    //double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    //progressDialog.setMessage("Uploaded:"+(int)progress+"%");
                });

    }

    /**
     * Metodo per eliminare l'ultimo file caricato su Firebase per rimpiazzarlo con uno nuovo
     * @param url
     */
    private void eliminaFile(String url) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        storageReference.delete().addOnSuccessListener(aVoid -> {
            // File deleted successfully
            Log.e("firebasestorage", "onSuccess: deleted file");
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                Log.e("firebasestorage", "onFailure: did not delete file");
            }
        });
    }

    /**
     * Metodo per effettuare il download dell'ultimo file caricato su Firebase
     */
    private void downloadFile() {
        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(file.getUrl());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(getContext(), Environment.DIRECTORY_DOWNLOADS, System.currentTimeMillis()+".pdf");
        downloadManager.enqueue(request);
    }
}