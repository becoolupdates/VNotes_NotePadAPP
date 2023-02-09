package com.vikas.notebookpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firestore.v1.Document;

public class NotesWritting extends AppCompatActivity {
     ImageView saveBtn;
     EditText titleBtn,contentBtn;
     TextView pagetitle,deletedNoteId;
     String title,content,docId;
     boolean iseditMod=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_writting);
        saveBtn=findViewById(R.id.save_Btn_id);
        titleBtn=findViewById(R.id.title_id);
        contentBtn=findViewById(R.id.context_id);
        pagetitle=findViewById(R.id.page_title_notes_id);
        deletedNoteId=findViewById(R.id.delete_Note_Btn);
        //recieved data
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("contant");
        docId=getIntent().getStringExtra("docId");
         if( docId!=null && !docId.isEmpty() ){
             iseditMod=true;
         }
         if(iseditMod) {
             pagetitle.setText("Edit your data");
             deletedNoteId.setVisibility(View.VISIBLE);
         }
         titleBtn.setText(title);
         contentBtn.setText(content);
        saveBtn.setOnClickListener(e->saveNotes());
        deletedNoteId.setOnClickListener(e->deletedNotesFromFireBase());
    }
    private  void saveNotes(){
        String title=titleBtn.getText().toString();
        String content=contentBtn.getText().toString();
        if(title==null || title.isEmpty()){
            titleBtn.setError("title is mustAndShould");
            return;
        }
        //With help of Notes class...already created
        Notes notes=new Notes();
        notes.setTitle(title);
        notes.setContent(content);
        notes.setTimestamp(Timestamp.now());
        saveNotesIntoFireBase(notes);
    }
    private void deletedNotesFromFireBase(){
        DocumentReference document;
            document=UtiltyToast.collectionReference().document(docId);
        document.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    UtiltyToast.toastMessage(NotesWritting.this,"Delete Your data is note .");
                    finish();
                }else
                    UtiltyToast.toastMessage(NotesWritting.this,"Fail while delete your note");
            }
        });
    }
    private void saveNotesIntoFireBase(Notes note){
        DocumentReference document;
        if(iseditMod)
                document=UtiltyToast.collectionReference().document(docId);
        else
                document=UtiltyToast.collectionReference().document();
        document.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    UtiltyToast.toastMessage(NotesWritting.this,"Sucessfull Your data is added .");
                    finish();
                }else
                    UtiltyToast.toastMessage(NotesWritting.this,"Sorry Your data not added .Please check onces and Try again.");
            }
        });
    }
}