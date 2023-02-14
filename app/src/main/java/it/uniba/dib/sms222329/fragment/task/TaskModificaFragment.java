package it.uniba.dib.sms222329.fragment.task;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.MotionLabel;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.FileUpload;
import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TaskDatabase;
import it.uniba.dib.sms222329.database.TesiSceltaDatabase;

public class TaskModificaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Task task;
    private TesiScelta tesiScelta;
    private LocalDate dataSelezionata;

    //View Items
    private TextInputEditText titoloTask;
    private TextInputEditText descrizioneTask;
    private TextInputEditText dataFine;
    private Slider sliderStato;
    private TextView testoStato;
    private TextView materiale;
    private Button caricaMateriale;
    private Button modificaTask;
    private MotionLabel labelDataFine;

    //Firebase
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FileUpload file;

    public TaskModificaFragment(Task task) {
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
        return inflater.inflate(R.layout.fragment_task_crea_modifica, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        FillAllEmpty();

        dataFine.setOnClickListener(view1 -> {
            MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
            materialDateBuilder.setTitleText(getResources().getString(R.string.seleziona_data));
            final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

            materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                dataFine.setText(materialDatePicker.getHeaderText());

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis((Long) selection);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                dataSelezionata  = LocalDate.parse(format.format(calendar.getTime()));
            });
        });

        sliderStato.addOnChangeListener((slider, value, fromUser) -> {
            if(value==0){
                testoStato.setText(getResources().getString(R.string.task_assegnato));
            } else if(value==25){
                testoStato.setText(getResources().getString(R.string.task_iniziato));
            } else if(value==50){
                testoStato.setText(getResources().getString(R.string.task_in_completamento));
            } else if(value==75){
                testoStato.setText(getResources().getString(R.string.task_in_revisione));
            } else if(value==100){
                testoStato.setText(getResources().getString(R.string.task_completato));
            }
        });

        modificaTask.setOnClickListener(view -> {
            FillAllEmpty();
            if(Utility.accountLoggato == Utility.RELATORE){
                if(task.ModificaTask(titoloTask.getText().toString().trim(), descrizioneTask.getText().toString().trim(), dataSelezionata, (int) (sliderStato.getValue()/25), db)){        //settare slider
                    Toast.makeText(getActivity().getApplicationContext(), R.string.task_modifica_successo, Toast.LENGTH_SHORT).show();
                    Utility.goBack(getActivity());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.operazione_fallita, Toast.LENGTH_SHORT).show();
                }
            }

            if(Utility.accountLoggato == Utility.TESISTA){
                if(task.ModificaTask((int) (sliderStato.getValue()/25), db)){        //settare slider
                    Toast.makeText(getActivity().getApplicationContext(), R.string.task_modifica_successo, Toast.LENGTH_SHORT).show();
                    Utility.goBack(getActivity());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.operazione_fallita, Toast.LENGTH_SHORT).show();
                }
            }
        });


        caricaMateriale.setOnClickListener(view -> {
            if(Utility.CheckStorage(getActivity(), this)) {
                caricaFile();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==Utility.REQUEST_PERMESSO_STORAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            caricaFile();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.permesso_negato, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        titoloTask = getView().findViewById(R.id.titolo_task);
        descrizioneTask = getView().findViewById(R.id.descrizione_task);
        dataFine = getView().findViewById(R.id.data_fine);
        sliderStato = getView().findViewById(R.id.slider);
        testoStato = getView().findViewById(R.id.stato);
        materiale = getView().findViewById(R.id.materiale_nome);
        caricaMateriale = getView().findViewById(R.id.carica_materiale);
        modificaTask = getView().findViewById(R.id.salva_task);
        labelDataFine = getView().findViewById(R.id.label_data_fine);

        if(Utility.accountLoggato == Utility.TESISTA){
            titoloTask.setVisibility(View.GONE);
            descrizioneTask.setVisibility(View.GONE);
            labelDataFine.setVisibility(View.GONE);
            dataFine.setVisibility(View.GONE);
        }

        sliderStato.setValue((float) (25.0 * task.getStato()));

        if(sliderStato.getValue()==0){
            testoStato.setText(getResources().getString(R.string.task_assegnato));
        } else if(sliderStato.getValue()==25){
            testoStato.setText(getResources().getString(R.string.task_iniziato));
        } else if(sliderStato.getValue()==50){
            testoStato.setText(getResources().getString(R.string.task_in_completamento));
        } else if(sliderStato.getValue()==75){
            testoStato.setText(getResources().getString(R.string.task_in_revisione));
        } else if(sliderStato.getValue()==100){
            testoStato.setText(getResources().getString(R.string.task_completato));
        }

        getLastUpload();
    }

    /**
     * Riempie i campi vuoti con il giusto valore se sono vuoti
     */
    private void FillAllEmpty() {
        Utility.fillIfEmpty(titoloTask, task.getTitolo());
        Utility.fillIfEmpty(descrizioneTask, task.getDescrizione());
        if(Utility.isEmptyTextbox(dataFine)){
            Utility.fillIfEmpty(dataFine, String.valueOf(task.getDataFine()));
            dataSelezionata  = LocalDate.parse(dataFine.getText().toString(), Utility.convertFromStringDate);
        }
    }

    /**
     * Metodo di inizializzazione di Firebase
     */
    private void inizializzaFirebase() {
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance("https://laureapp-f0334-default-rtdb.europe-west1.firebasedatabase.app/").getReference("uploads_materiale");
    }

    /**
     * Metodo di recuper dell'ultimo file caricato se è stato caricato
     */
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
            materiale.setText(getResources().getString(R.string.no_file));
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
     * Metodo per l'upload di un file su Firebase
     * @param data
     */
    private void uploadFiles(Uri data) {
        final LinearProgressIndicator linearProgressIndicator = getActivity().findViewById(R.id.progress);
        linearProgressIndicator.setVisibility(View.VISIBLE);

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
                    if(Utility.accountLoggato == Utility.TESISTA) file = new FileUpload(Utility.tesistaLoggato.getIdUtente(), Utility.tesistaLoggato.getNome()+" "+Utility.tesistaLoggato.getCognome(), url.toString(), new Date(), getActivity().getApplicationContext());
                    if(Utility.accountLoggato == Utility.RELATORE) file = new FileUpload(Utility.relatoreLoggato.getIdUtente(), Utility.relatoreLoggato.getNome()+" "+Utility.relatoreLoggato.getCognome(), url.toString(), new Date(), getActivity().getApplicationContext());
                    if(Utility.accountLoggato == Utility.CORELATORE) file = new FileUpload(Utility.coRelatoreLoggato.getIdUtente(), Utility.coRelatoreLoggato.getNome()+" "+Utility.coRelatoreLoggato.getCognome(), url.toString(), new Date(), getActivity().getApplicationContext());
                    materiale.setText(file.toString());
                    databaseReference.child(downloadKey).setValue(file);
                    TaskDatabase.UploadTask(db, task, downloadKey);
                    Toast.makeText(getActivity().getApplicationContext(), R.string.file_caricato_successo, Toast.LENGTH_SHORT);
                    linearProgressIndicator.setVisibility(View.GONE);
                }).addOnProgressListener(snapshot -> {
                    double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    linearProgressIndicator.setProgress((int) progress, true);
                });
    }

    /**
     * Metodo per l'eliminazione dell'ultimo file da Firebase per il rimpiazzo di uno nuovo
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
}