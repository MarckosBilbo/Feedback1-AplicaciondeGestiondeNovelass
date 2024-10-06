package com.example.feedback1_aplicaciondegestiondenovelass.modelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NovelRepository {
    private DatabaseReference databaseReference;
    private MutableLiveData<List<Novel>> allNovels;
    private final ExecutorService executorService;

    public NovelRepository(Application application) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("novels");
        allNovels = new MutableLiveData<>();
        executorService = Executors.newFixedThreadPool(2);
        fetchAllNovels();
    }

    private void fetchAllNovels() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Novel> novels = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Novel novel = snapshot.getValue(Novel.class);
                    novels.add(novel);
                }
                allNovels.postValue(novels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }

    public void insert(Novel novel) {
        executorService.execute(() -> databaseReference.push().setValue(novel));
    }

    public void delete(Novel novel) {
        executorService.execute(() -> databaseReference.child(String.valueOf(novel.getId())).removeValue());
    }

    public void update(Novel novel) {
        executorService.execute(() -> databaseReference.child(String.valueOf(novel.getId())).setValue(novel));
    }

    public LiveData<List<Novel>> getAllNovels() {
        return allNovels;
    }
}