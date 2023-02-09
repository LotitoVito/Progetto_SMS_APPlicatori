package it.uniba.dib.sms222329.fragment.task;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.datepicker.MaterialDatePicker;
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

public class TaskCreaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private TesiScelta tesiScelta;
    private LocalDate dataSelezionata;

    //View Items
    private TextInputEditText titoloTask;
    private TextInputEditText descrizioneTask;
    private TextInputEditText dataFine;
    private MotionLabel labelStato;
    private Slider slider;
    private TextView materiale;
    private Button caricaMateriale;
    private Button creaTask;

    //Firebase
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private  FileUpload file;
    private String downloadKey;

    public TaskCreaFragment(TesiScelta tesiScelta) {
        this.tesiScelta = tesiScelta;
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

        dataFine.setOnClickListener(view1 -> {
            MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
            materialDateBuilder.setTitleText("SELECT A DATE");
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

        creaTask.setOnClickListener(view -> {
            if(!IsEmpty(titoloTask, descrizioneTask, dataFine)){
                Task task = new Task(titoloTask.getText().toString().trim(), descrizioneTask.getText().toString().trim(), dataSelezionata, downloadKey, tesiScelta.getIdTesiScelta());
                if(TaskDatabase.CreaTask(db, task)){
                    Toast.makeText(getActivity().getApplicationContext(), "Task creata con successo", Toast.LENGTH_SHORT).show();
                    Utility.goBack(getActivity());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Compilare i campi obbligatori", Toast.LENGTH_SHORT).show();
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
        dataFine = getView().findViewById(R.id.data_fine);
        labelStato = getView().findViewById(R.id.label_stato);
        slider = getView().findViewById(R.id.slider);
        materiale = getView().findViewById(R.id.materiale_nome);
        caricaMateriale = getView().findViewById(R.id.carica_materiale);
        creaTask = getView().findViewById(R.id.salva_task);

        materiale.setText("Nessun caricamento");

        labelStato.setVisibility(View.GONE);
        slider.setVisibility(View.GONE);
    }

    /**
     * Metodo che verifica se i campi obbligatori sono vuoti, nel caso siano vuoti sono contrassegnati;
     * @param titolo
     * @param argomento
     * @param dataFine
     * @return  Il metodo restituisce true se almeno un campo è vuoto, restituisce false se tutti i campi non sono vuoti
     */
    private boolean IsEmpty(EditText titolo, EditText argomento, EditText dataFine){
        boolean risultato = false;

        if(Utility.isEmptyTextbox(titolo)){
            risultato = true;
            titolo.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(argomento)){
            risultato = true;
            argomento.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(dataFine)){
            risultato = true;
            dataFine.setError("Obbligatorio");
        }
        return risultato;
    }

    private void inizializzaFirebase() {
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance("https://laureapp-f0334-default-rtdb.europe-west1.firebasedatabase.app/").getReference("uploads_materiale");
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

                    if(file==null){
                        downloadKey = databaseReference.push().getKey();
                    }else{
                        downloadKey = TesiSceltaDatabase.DownloadTesiScelta(db,tesiScelta);
                        eliminaFile(file.getUrl());
                    }
                    if(Utility.accountLoggato == Utility.TESISTA) file = new FileUpload(Utility.tesistaLoggato.getIdUtente(), Utility.tesistaLoggato.getNome()+" "+Utility.tesistaLoggato.getCognome(), url.toString(), new Date());
                    if(Utility.accountLoggato == Utility.RELATORE) file = new FileUpload(Utility.relatoreLoggato.getIdUtente(), Utility.relatoreLoggato.getNome()+" "+Utility.relatoreLoggato.getCognome(), url.toString(), new Date());
                    if(Utility.accountLoggato == Utility.CORELATORE) file = new FileUpload(Utility.coRelatoreLoggato.getIdUtente(), Utility.coRelatoreLoggato.getNome()+" "+Utility.coRelatoreLoggato.getCognome(), url.toString(), new Date());
                    materiale.setText(file.toString());
                    databaseReference.child(downloadKey).setValue(file);
                    Toast.makeText(getActivity().getApplicationContext(), "File Uploaded!", Toast.LENGTH_SHORT);
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
}