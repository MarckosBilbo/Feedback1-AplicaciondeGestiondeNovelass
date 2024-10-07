/*
package com.example.feedback1_aplicaciondegestiondenovelass.service;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.Novel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CargaNovelas extends AsyncTaskLoader<List<Novel>> {

    public CargaNovelas(Context context) {
        super(context);
    }

    @Override
    public List<Novel> loadInBackground() {
        List<Novel> novels = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("novels");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Novel novel = snapshot.getValue(Novel.class);
                    novels.add(novel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Este por ahora nada
            }
        });
        return novels;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
*/