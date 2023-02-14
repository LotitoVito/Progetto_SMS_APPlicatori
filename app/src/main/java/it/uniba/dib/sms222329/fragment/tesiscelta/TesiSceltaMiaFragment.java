package it.uniba.dib.sms222329.fragment.tesiscelta;

import static android.app.Activity.RESULT_OK;

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
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.FileUpload;
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaRichiesteTesiDatabase;
import it.uniba.dib.sms222329.database.TesiSceltaDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaRichiesteTesiAdapter;
import it.uniba.dib.sms222329.fragment.task.TaskListaFragment;

public class TesiSceltaMiaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private TesiScelta tesiScelta;
    private boolean operazioneDownload; //usata per gestire le operazioni quando vengono accettati i permessi

    //View Items
    private LinearLayout richieste;
    private ListView listaRichieste;
    private ScrollView tesiMia;
    private TextView relatore;
    private TextView titoloTesi;
    private TextView argomentoTesi;
    private TextInputEditText abTesi;
    private TextView tempistiche;
    private TextView dataConsegna;
    private TextView nomeCorelatore;
    private TextView emailCorelatore;
    private TextView visualizzaTask;
    private TextView ultimoCaricamento;
    private Button scarica;
    private Button carica;
    private Button salvaModifica;

    //Firebase
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FileUpload file;

    public TesiSceltaMiaFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inizializzaFirebase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tesi_scelta_mia, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        if(richieste.getVisibility()!=View.GONE){
            RefreshList();
        } else {
            SetTextAll();

            visualizzaTask.setOnClickListener(view -> {
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TaskListaFragment(tesiScelta));
            });

            scarica.setOnClickListener(view -> {
                if(file!=null){
                    if(Utility.CheckStorage(getActivity(), this)) {
                        downloadFile();
                    } else {
                        operazioneDownload = true;
                    }
                }
            });

            carica.setOnClickListener(view -> {
                if(Utility.CheckStorage(getActivity(), this)) {
                    caricaFile();
                } else {
                    operazioneDownload = false;
                }
            });

            salvaModifica.setOnClickListener(view -> {
                if(!IsEmpty(abTesi)){
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.conferma)
                            .setMessage(R.string.tesi_consegna_richiesta)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ConsegnaTesi();
                                }
                            })
                            .setNegativeButton(R.string.indietro, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.campi_vuoti_errore, Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    /**
     * Metodo per consegnare la tesi completata
     */
    private void ConsegnaTesi(){
        if(tesiScelta.ConsegnaTesiScelta(db, abTesi.getText().toString().trim())){
            Toast.makeText(getActivity().getApplicationContext(), R.string.tesi_consegna_successo, Toast.LENGTH_SHORT).show();
            this.onResume();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.operazione_fallita, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==Utility.REQUEST_PERMESSO_STORAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(operazioneDownload){
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
        richieste = getView().findViewById(R.id.richieste);
        listaRichieste = getView().findViewById(R.id.tesistiList);
        tesiMia = getView().findViewById(R.id.mia_tesi);

        if(VerificaTesiScelta()){
            richieste.setVisibility(View.GONE);
            tesiMia.setVisibility(View.VISIBLE);
        }

        relatore = getView().findViewById(R.id.nomeRelatore);
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomentoTesi = getView().findViewById(R.id.argomentoTesi);
        abTesi = getView().findViewById(R.id.ab_tesi);
        tempistiche = getView().findViewById(R.id.tempistiche);
        dataConsegna = getView().findViewById(R.id.dataConsegna);
        nomeCorelatore = getView().findViewById(R.id.nome_corelatore);
        emailCorelatore = getView().findViewById(R.id.email_corelatore);
        visualizzaTask = getView().findViewById(R.id.visualizza_task);
        ultimoCaricamento = getView().findViewById(R.id.ultimo_caricamento);
        scarica = getView().findViewById(R.id.scarica);
        carica = getView().findViewById(R.id.carica);
        salvaModifica = getView().findViewById(R.id.salvaModifica);

        if(richieste.getVisibility() == View.GONE){
            if(getLastUpload()){
                scarica.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Ricarica la lista delle richieste in base al tesista loggato
     */
    private void RefreshList(){
        List<RichiestaTesi> lista = ListaRichiesteTesiDatabase.ListaRichiesteTesiTesista(db, Utility.tesistaLoggato.getIdTesista());
        ListaRichiesteTesiAdapter adapter = new ListaRichiesteTesiAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
        listaRichieste.setAdapter(adapter);
    }

    /**
     * Verifica se il tesista loggato ha già una tesi scelta, nel caso sia vero imposta la view della tesi scelta
     * @return  Restituisce true se il tesista loggato ha una tesi scelta, altrimenti false
     */
    private boolean VerificaTesiScelta(){
        Cursor cursor = db.RicercaDato("SELECT * FROM " + Database.TESISCELTA + " WHERE " + Database.TESISCELTA_TESISTAID + "=" + Utility.tesistaLoggato.getIdTesista() + ";");
        if(cursor.moveToNext()){
            String data = cursor.getString(cursor.getColumnIndexOrThrow(Database.TESISCELTA_DATAPUBBLICAZIONE));
            LocalDate dataPubblicazione = null;
            if(data != null){
                Log.d("test", data);
                dataPubblicazione = LocalDate.parse(data, Utility.convertFromStringDate);
            }
            TesiScelta tesi = new TesiScelta(cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESISCELTA_TESIID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESISCELTA_ID)), cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESISCELTA_TESISTAID)), cursor.getString(cursor.getColumnIndexOrThrow(Database.TESISCELTA_CAPACITATESISTA)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESISCELTA_CORELATOREID)), cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESISCELTA_STATOCORELATORE)),
                    dataPubblicazione, cursor.getString(cursor.getColumnIndexOrThrow(Database.TESISCELTA_ABSTRACT)));
            tesiScelta = tesi;
            return true;
        }
        return false;
    }

    /**
     * Imposta il testo per ogni elemento della view in base ai casi
     */
    private void SetTextAll() {
        //Relatore
        Cursor cursorRelatore = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", u." + Database.UTENTI_NOME + " " +
                "FROM " + Database.UTENTI + " u, " + Database.RELATORE + " r, " + Database.TESI + " t, " + Database.TESISCELTA + " ts " +
                "WHERE ts." + Database.TESISCELTA_TESIID + "=t." + Database.TESI_ID + " AND t." + Database.TESI_RELATOREID + "=r." + Database.RELATORE_ID + " AND r." + Database.RELATORE_UTENTEID + "=u." + Database.UTENTI_ID + " " +
                "AND " + Database.TESISCELTA_TESIID + "=" + tesiScelta.getIdTesi() + ";");
        cursorRelatore.moveToFirst();
        relatore.setText(cursorRelatore.getString(cursorRelatore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursorRelatore.getString(cursorRelatore.getColumnIndexOrThrow(Database.UTENTI_NOME)));

        //Tesi
        Cursor cursorTesi = db.RicercaDato("SELECT t." + Database.TESI_TITOLO + ", t." + Database.TESI_ARGOMENTO + ", t." + Database.TESI_TEMPISTICHE + " " +
                "FROM " + Database.TESI + " t, " + Database.TESISCELTA +" ts " +
                "WHERE ts." + Database.TESISCELTA_TESIID + "=t." + Database.TESI_ID + " AND " + Database.TESISCELTA_TESIID + "=" + tesiScelta.getIdTesi() + ";");
        cursorTesi.moveToFirst();
        titoloTesi.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_TITOLO)));
        argomentoTesi.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_ARGOMENTO)));
        tempistiche.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_TEMPISTICHE)));

        //Abstract e DataPubblicazione
        abTesi.setText(tesiScelta.getRiassunto());
        if(tesiScelta.getDataPubblicazione() != null){
            dataConsegna.setText(tesiScelta.getDataPubblicazione().format(Utility.showDate));
        } else {
            dataConsegna.setText("Tesi non ancora consegnata");
        }

        //Corelatore
        Cursor cursorCorelatore = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", u." + Database.UTENTI_NOME + ", u." + Database.UTENTI_EMAIL + " " +
                "FROM " + Database.UTENTI + " u, " + Database.CORELATORE + " cr, " + Database.TESISCELTA + " ts " +
                "WHERE ts." + Database.TESISCELTA_CORELATOREID + "=cr." + Database.CORELATORE_ID + " AND cr." + Database.CORELATORE_UTENTEID+ "=u." + Database.UTENTI_ID + " " +
                "AND " + Database.TESISCELTA_CORELATOREID + "=" + tesiScelta.getIdCorelatore() + ";");
        if(cursorCorelatore.moveToNext()){
            nomeCorelatore.setText(cursorCorelatore.getString(cursorCorelatore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursorCorelatore.getString(cursorCorelatore.getColumnIndexOrThrow(Database.UTENTI_NOME)));
            emailCorelatore.setText(cursorCorelatore.getString(cursorCorelatore.getColumnIndexOrThrow(Database.UTENTI_EMAIL)));
        } else {
            nomeCorelatore.setText(R.string.corelatore_non_disponibile);
            emailCorelatore.setVisibility(View.GONE);
        }
    }

    /**
     * Metodo che verifica se i campi obbligatori sono vuoti, nel caso siano vuoti sono contrassegnati;
     * @param abTesi
     * @return  Il metodo restituisce true se almeno un campo è vuoto, restituisce false se tutti i campi non sono vuoti
     */
    private boolean IsEmpty(EditText abTesi){
        boolean risultato = false;

        if(Utility.isEmptyTextbox(abTesi)){
            risultato = true;
            abTesi.setError(getResources().getString(R.string.campo_obbligatorio));
        }
        return risultato;
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
                        ultimoCaricamento.setText(genericFile.toString());
                        file = genericFile;
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ultimoCaricamento.setText(R.string.errore);
            }
        });

        if(file == null){
            ultimoCaricamento.setText(R.string.no_file);
            return false;
        }else{
            return true;
        }
    }

    /**
     * Permette di aprire il picker di file
     */
    private void caricaFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.seleziona_pdf)), Utility.REQUEST_CARICA_FILE);
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
                    ultimoCaricamento.setText(file.toString());
                    databaseReference.child(downloadKey).setValue(file);
                    TesiSceltaDatabase.UploadTesiScelta(db, tesiScelta,downloadKey);
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