/* com.example.feedback1_aplicaciondegestiondenovelass.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.Novel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;



public class SyncJobService extends JobService {

    private static final String TAG = "SyncJobService";

    @Override
    public boolean onStartJob(JobParameters params) {
        new SyncTask().execute(params);
        return true; // Si sincroniza los datos correctamente, se llama a jobFinished() con el parámetro false
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true; // Si se cancela la sincronización, se llama a jobFinished() con el parámetro true
    }

    private class SyncTask extends AsyncTask<JobParameters, Void, JobParameters> {
        @Override
        protected JobParameters doInBackground(JobParameters... params) {

            Log.d(TAG, "Synchronizing data...");
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("novels");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Novel> novels = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Novel novel = snapshot.getValue(Novel.class);
                        novels.add(novel);
                    }
                    Log.d(TAG, "Data synchronization complete. Fetched " + novels.size() + " novels.");
                    NotificationHelper.showNotification(getApplicationContext(), "Sync Complete", "Data synchronization is complete.");
                    jobFinished(params[0], false);
                }



                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "Data synchronization failed: " + databaseError.getMessage());
                    jobFinished(params[0], true);
                }
            });

            return params[0];
        }


        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            // No hace nada de momento
        }
    }
}*/