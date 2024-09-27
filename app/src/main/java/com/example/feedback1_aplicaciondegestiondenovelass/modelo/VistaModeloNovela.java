// VistaModeloNovela.java
package com.example.feedback1_aplicaciondegestiondenovelass.modelo;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class VistaModeloNovela extends AndroidViewModel {
    private NovelRepository repository;
    private LiveData<List<Novel>> allNovels;

    public VistaModeloNovela(@NonNull Application application) {
        super(application);
        repository = new NovelRepository(application);
        allNovels = repository.getAllNovels();
    }

    public LiveData<List<Novel>> getAllNovels() {
        return allNovels;
    }

    public void insert(Novel novel) {
        repository.insert(novel);
    }

    public void delete(Novel novel) {
        repository.delete(novel);
    }

    public void update(Novel novel) {
        repository.update(novel);
    }

    public void toggleFavorite(Novel novel) {
        novel.setFavorite(!novel.isFavorite());
        repository.update(novel);
    }
}