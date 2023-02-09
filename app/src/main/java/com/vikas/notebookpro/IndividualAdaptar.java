package com.vikas.notebookpro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class IndividualAdaptar extends FirestoreRecyclerAdapter<Notes,IndividualAdaptar.NotesViewHolde>{

  Context context;
    public IndividualAdaptar(@NonNull FirestoreRecyclerOptions<Notes> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NotesViewHolde holder, int position, @NonNull Notes model) {
        holder.title.setText(model.getTitle());
        holder.contant.setText(model.getContent());
        holder.timeStamp.setText(UtiltyToast.timeStrampToString(model.getTimestamp()));
        holder.itemView.setOnClickListener(e->{
           Intent intent= new Intent(context,NotesWritting.class);
           intent.putExtra("title",model.getTitle());
           intent.putExtra("contant",model.getContent());
           String docId=this.getSnapshots().getSnapshot(position).getId();
           intent.putExtra("docId",docId);
           context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public NotesViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.individualnotes,parent,false);
        return  new NotesViewHolde(view);
    }

    class NotesViewHolde extends RecyclerView.ViewHolder{
            TextView title,contant,timeStamp;
        public NotesViewHolde( View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_individual_id);
            contant=itemView.findViewById(R.id.contant_individual_id);
            timeStamp=itemView.findViewById(R.id.individual_timeStamp);
        }
    }
}
