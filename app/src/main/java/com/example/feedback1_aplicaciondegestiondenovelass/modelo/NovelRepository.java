package com.example.feedback1_aplicaciondegestiondenovelass.modelo;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.feedback1_aplicaciondegestiondenovelass.util.RedOptima;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

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
                    try {
                        String compressedData = snapshot.getValue(String.class);
                        byte[] decompressedData = RedOptima.descomprimirDatos(Base64.getDecoder().decode(compressedData));
                        Novel novel = new Gson().fromJson(new String(decompressedData), Novel.class);
                        if (novel != null) {
                            novel.setKey(snapshot.getKey());
                            novels.add(novel);
                        }
                    } catch (IOException e) {
                        Log.e("NovelRepository", "Error al descomprimir los datos", e);
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
            try {
                byte[] datosComprimidos = RedOptima.comprimirDatos(novel.toJson().getBytes());
                String encodedData = Base64.getEncoder().encodeToString(datosComprimidos);
                newRef.setValue(encodedData).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("NovelRepository", "Novela insertada con éxito");
                    } else {
                        Log.e("NovelRepository", "Error al insertar la novela", task.getException());
                    }
                });
            } catch (IOException e) {
                Log.e("NovelRepository", "Error al comprimir los datos", e);
            }
        });
    }

    public void delete(Novel novel) {
        executorService.execute(() -> databaseReference.child(novel.getKey()).removeValue());
    }

    public void update(Novel novel) {
        executorService.execute(() -> {
            try {
                byte[] datosComprimidos = RedOptima.comprimirDatos(novel.toJson().getBytes());
                String encodedData = Base64.getEncoder().encodeToString(datosComprimidos);
                databaseReference.child(novel.getKey()).setValue(encodedData);
            } catch (IOException e) {
                Log.e("NovelRepository", "Error al comprimir los datos", e);
            }
        });
    }

    public LiveData<List<Novel>> getAllNovels() {
        return allNovels;
    }
}


/*


import com.example.feedback1_aplicaciondegestiondenovelass.utils.RedOptima;

// ...

public void insert(Novel novel) {
    executorService.execute(() -> {
        DatabaseReference newRef = databaseReference.push();
        novel.setKey(newRef.getKey());
        try {
            byte[] datosComprimidos = RedOptima.comprimirDatos(novel.toString().getBytes());
            newRef.setValue(new String(datosComprimidos)).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("NovelRepository", "Novela insertada con éxito");
                } else {
                    Log.e("NovelRepository", "Error al insertar la novela", task.getException());
                }
            });
        } catch (IOException e) {
            Log.e("NovelRepository", "Error al comprimir los datos", e);
        }
    });
}

public void update(Novel novel) {
    executorService.execute(() -> {
        try {
            byte[] datosComprimidos = RedOptima.comprimirDatos(novel.toString().getBytes());
            databaseReference.child(novel.getKey()).setValue(new String(datosComprimidos));
        } catch (IOException e) {
            Log.e("NovelRepository", "Error al comprimir los datos", e);
        }
    });
}


 */