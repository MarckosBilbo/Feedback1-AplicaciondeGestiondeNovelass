package com.example.feedback1_aplicaciondegestiondenovelass

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feedback1_aplicaciondegestiondenovelass.ui.theme.Feedback1AplicacióndeGestióndeNovelassTheme
import com.example.feedback1_aplicaciondegestiondenovelass.vista.PantallaPrincipal
import com.example.feedback1_aplicaciondegestiondenovelass.service.SyncJobService
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Llama a setPersistenceEnabled antes de cualquier otra operación con Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Feedback1AplicacióndeGestióndeNovelassTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "main") {
                    composable("main") { PantallaPrincipal() }
                }
            }
        }

        scheduleJob(this)
        scheduleAlarm()
    }

    companion object {
        @JvmStatic
        fun scheduleJob(context: Context) {
            val componentName = ComponentName(context, SyncJobService::class.java)
            val jobInfo = JobInfo.Builder(1, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000) // Con quince minutitos creo que bien
                .build()

            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(jobInfo)
        }
    }

    private fun scheduleAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, SyncJobService::class.java)
        val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, interval, pendingIntent
        )
    }
}