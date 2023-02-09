package it.uniba.dib.sms222329.fragment.task;

import static android.app.Activity.RESULT_OK;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.MotionLabel;
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
import com.google.android.material.slider.Slider;
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
import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TaskDatabase;
import it.uniba.dib.sms222329.fragment.ricevimento.RicevimentoCreaFragment;

public class TaskDettagliFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Task task;

    //View Items
    private TextView titoloTask;
    private TextView descrizioneTask;
    private TextView dateInizioFine;
    private Slider sliderStato;
    private TextView testoStato;
    private TextView materiale;
    private Button scaricaMateriale;
    private Button caricaMateriale;
    private TextView creaRicevimento;
    private Button modificaTask;
    private MotionLabel label;

    //Firebase
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FileUpload file;

    public TaskDettagliFragment(Task task) {
        this.task = task;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inizializzaFirebase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_dettagli, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetTextAll();

        creaRicevimento.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new RicevimentoCreaFragment(task));
        });

        scaricaMateriale.setOnClickListener(view -> {
            if(file!=null){
                if(Utility.CheckStorage(getActivity())) {
                    downloadFile();
                }
            }
        });

        caricaMateriale.setOnClickListener(view -> {
            if(Utility.CheckStorage(getActivity())) {
                caricaFile();
            }
        });



    }


    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        titoloTask = getView().findViewById(R.id.titolo_task);
        descrizioneTask = getView().findViewById(R.id.descrizione_task);
        dateInizioFine = getView().findViewById(R.id.date_inizio_fine);
        sliderStato = getView().findViewById(R.id.slider);
        testoStato = getView().findViewById(R.id.stato);
        materiale = getView().findViewById(R.id.materiale_nome);
        scaricaMateriale = getView().findViewById(R.id.scarica_materiale);
        caricaMateriale = getView().findViewById(R.id.carica_materiale);
        creaRicevimento = getView().findViewById(R.id.crea_ricevimento);
        modificaTask = getView().findViewById(R.id.modifica_task);
        label = getView().findViewById(R.id.label_ricevimento);

        caricaMateriale.setVisibility(View.GONE);
        modificaTask.setVisibility(View.GONE);
        if(Utility.accountLoggato == Utility.RELATORE || Utility.accountLoggato == Utility.CORELATORE){
            label.setVisibility(View.GONE);
            creaRicevimento.setVisibility(View.GONE);
        }

        sliderStato.setValue((float) (25.0 * task.getStato()));
        sliderStato.setEnabled(false);

        getLastUpload();
    }

    /**
     * Imposta il testo per ogni elemento della view
     */
    private void SetTextAll() {
        titoloTask.setText(task.getTitolo());
        descrizioneTask.setText(task.getDescrizione());
        dateInizioFine.setText("Data inizio: " + task.getDataInizio().format(Utility.showDate) + " - Data fine: " + task.getDataFine().format(Utility.showDate));

        if(sliderStato.getValue()==0){
            testoStato.setText("Assegnato");
        } else if(sliderStato.getValue()==25){
            testoStato.setText("Iniziato");
        } else if(sliderStato.getValue()==50){
            testoStato.setText("In completamento");
        } else if(sliderStato.getValue()==75){
            testoStato.setText("In revisione");
        } else if(sliderStato.getValue()==100){
            testoStato.setText("Completato");
        }
        materiale.setText(String.valueOf(task.getLinkMateriale()));
    }

    private void inizializzaFirebase() {
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance("https://laureapp-f0334-default-rtdb.europe-west1.firebasedatabase.app/").getReference("uploads_materiale");
    }

    private void getLastUpload() {
        file = null;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    if (TaskDatabase.DownloadTask(db, task).compareTo(data.getKey())==0){
                        FileUpload genericFile = data.getValue(FileUpload.class);
                        materiale.setText(genericFile.toString());
                        file = genericFile;
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                materiale.setText("Errore");
            }
        });

        if(file == null){
            materiale.setText("Nessun caricamento");
        }
    }

    /**
     * Permette di aprire il picker di file
     */
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
        //final ProgressDialog progressDialog = new ProgressDialog(getActivity().getApplicationContext());
        //progressDialog.setTitle("Uploading...");
        //progressDialog.show();

        StorageReference reference = storageReference.child("Uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(taskSnapshot -> {
                    com.google.android.gms.tasks.Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    Uri url = uriTask.getResult();
                    String downloadKey;
                    if(file==null){
                        downloadKey = databaseReference.push().getKey();
                    }else{
                        downloadKey = TaskDatabase.DownloadTask(db,task);
                        eliminaFile(file.getUrl());
                    }
                    if(Utility.accountLoggato == Utility.TESISTA) file = new FileUpload(Utility.tesistaLoggato.getIdUtente(), Utility.tesistaLoggato.getNome()+" "+Utility.tesistaLoggato.getCognome(), url.toString(), new Date());
                    if(Utility.accountLoggato == Utility.RELATORE) file = new FileUpload(Utility.relatoreLoggato.getIdUtente(), Utility.relatoreLoggato.getNome()+" "+Utility.relatoreLoggato.getCognome(), url.toString(), new Date());
                    if(Utility.accountLoggato == Utility.CORELATORE) file = new FileUpload(Utility.coRelatoreLoggato.getIdUtente(), Utility.coRelatoreLoggato.getNome()+" "+Utility.coRelatoreLoggato.getCognome(), url.toString(), new Date());
                    materiale.setText(file.toString());
                    databaseReference.child(downloadKey).setValue(file);
                    if(TaskDatabase.UploadTask(db, task,downloadKey)){
                        Toast.makeText(getActivity().getApplicationContext(), "File Uploaded!", Toast.LENGTH_SHORT);
                    }
                    //progressDialog.dismiss();
                }).addOnProgressListener(snapshot -> {
                    //double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    //progressDialog.setMessage("Uploaded:"+(int)progress+"%");
                });

    }

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

    private void downloadFile() {
        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(file.getUrl());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(getContext(), Environment.DIRECTORY_DOWNLOADS, System.currentTimeMillis()+".pdf");
        downloadManager.enqueue(request);
    }
}