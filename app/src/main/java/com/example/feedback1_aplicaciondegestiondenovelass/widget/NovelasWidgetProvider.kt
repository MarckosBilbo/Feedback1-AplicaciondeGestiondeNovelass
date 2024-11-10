package com.example.feedback1_aplicaciondegestiondenovelass.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.FrameLayout
import com.example.feedback1_aplicaciondegestiondenovelass.MainActivity

class NovelasWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            // Define the layout programmatically
            val views = RemoteViews(context.packageName, android.R.layout.simple_list_item_1).apply {
                setOnClickPendingIntent(android.R.id.text1, pendingIntent)
                setTextViewText(android.R.id.text1, "Novelas Favoritas")
                setTextColor(android.R.id.text1, context.resources.getColor(android.R.color.black, null))
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}