/*package com.example.feedback1_aplicaciondegestiondenovelass.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class SyncJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        // Realiza la sincronización de datos aquí
        Log.d("SyncJobService", "Sincronización de datos iniciada");
        // Indica que el trabajo ha terminado
        jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // Maneja la finalización del trabajo
        return true;
    }
}*/