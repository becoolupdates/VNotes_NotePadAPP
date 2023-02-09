package com.vikas.notebookpro;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UtiltyToast {
    public static  void toastMessage(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public  static CollectionReference collectionReference(){
        FirebaseUser firebasecurrentUser= FirebaseAuth.getInstance().getCurrentUser();
       return FirebaseFirestore.getInstance().collection("NotesInformation").document(firebasecurrentUser.getUid()).collection("notes");
    }



    public static String timeStrampToString(Timestamp timestamp) {
        return new java.text.SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }


}
