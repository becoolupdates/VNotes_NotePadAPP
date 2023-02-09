package com.vikas.notebookpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionBtn;
    RecyclerView recyclerView;
    ImageView menuBtn;
    IndividualAdaptar individualAdaptar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionBtn=findViewById(R.id.float_btn);
        menuBtn=findViewById(R.id.menu_Btn_id);
        recyclerView=findViewById(R.id.recyle_view);
        menuBtn.setOnClickListener(e->showMenu());
        floatingActionBtn.setOnClickListener((e)->startActivity(new Intent(MainActivity.this,NotesWritting.class)));
        setupRecycleView();
    }
    private  void showMenu(){
        PopupMenu popupMenu=new PopupMenu(MainActivity.this,menuBtn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this,LoginPage.class));
                    finish();
                    return  true;
                }
                return false;
            }
        });
    }

    private void setupRecycleView() {
        Query query=UtiltyToast.collectionReference().orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Notes> options=new FirestoreRecyclerOptions.Builder<Notes>().setQuery(query,Notes.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        individualAdaptar=new IndividualAdaptar(options,this);
        recyclerView.setAdapter(individualAdaptar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        individualAdaptar.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        individualAdaptar.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        individualAdaptar.notifyDataSetChanged();
    }
}