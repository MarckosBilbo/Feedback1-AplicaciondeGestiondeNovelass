package com.example.feedback1_aplicaciondegestiondenovelass.modelo;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.feedback1_aplicaciondegestiondenovelass.PreferencesManager;


public class VistaModeloConfiguracion extends AndroidViewModel {
    private PreferencesManager preferencesManager;
    private MutableLiveData<Boolean> isDarkMode;

    public VistaModeloConfiguracion(@NonNull Application application) {
        super(application);
        preferencesManager = new PreferencesManager(application);
        isDarkMode = new MutableLiveData<>(preferencesManager.isDarkMode());
    }

    public LiveData<Boolean> getIsDarkMode() {
        return isDarkMode;
    }

    public void setTheme(boolean isDarkMode) {
        preferencesManager.setTheme(isDarkMode);
        this.isDarkMode.setValue(isDarkMode);
    }
}