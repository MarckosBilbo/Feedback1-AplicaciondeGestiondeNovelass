package com.example.feedback1_aplicaciondegestiondenovelass.modelo;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
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
    private String userId;

    public NovelRepository(Application application) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://app1234-c23ca-default-rtdb.europe-west1.firebasedatabase.app");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = database.getReference("APP").child("users").child(userId).child("novels");
        allNovels = new MutableLiveData<>();
        executorService = Executors.newFixedThreadPool(2);
        fetchAllNovels();
    }

    public void fetchAllNovels() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Novel> novels = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Novel novel = snapshot.getValue(Novel.class);
                    if (novel != null) {
                        novel.setKey(snapshot.getKey());
                        novels.add(novel);
                    }
                }
                allNovels.postValue(novels);
                Log.d("NovelRepository", "Novels fetched: " + novels.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("NovelRepository", "Error fetching novels", databaseError.toException());
            }
        });
    }

    public void insert(Novel novel) {
        executorService.execute(() -> {
            DatabaseReference newRef = databaseReference.push();
            novel.setKey(newRef.getKey());
            newRef.setValue(novel);
        });
    }

    public void delete(Novel novel) {
        executorService.execute(() -> databaseReference.child(novel.getKey()).removeValue());
    }

    public void update(Novel novel) {
        executorService.execute(() -> databaseReference.child(novel.getKey()).setValue(novel));
    }

    public LiveData<List<Novel>> getAllNovels() {
        return allNovels;
    }
}