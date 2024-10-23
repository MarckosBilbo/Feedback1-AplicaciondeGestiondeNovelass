package com.example.feedback1_aplicaciondegestiondenovelass.service;

import android.app.Application;
import android.content.Context;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.NovelRepository;

public class ConnectivityWorker extends Worker {
    public ConnectivityWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {
        NovelRepository repository = new NovelRepository((Application) getApplicationContext());
        repository.fetchAllNovels();
        return Result.success();
    }
}