package com.test.weather.Repository


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.test.weather.R
import com.test.weather.ui.MainActivity


class WeatherWorker(_context: Context, workerParameters: WorkerParameters)
    : Worker(_context, workerParameters)
{
    private val context = _context

    override fun doWork(): Result {
        //TimeUnit.MINUTES.sleep(60)
        notificationTemp()
        return Result.success()
    }

    private fun notificationTemp(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val weatherResponse = Weather(context, Location(context).location).getLocationWeatherNotification()
            val NOTIFICATION_ID = 234
            val notificationManager:NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val CHANNEL_ID = context.getString(R.string.weather_channel_id)
            val name: CharSequence = context.getString(R.string.weather_char_sequence)
            val Description = context.getString(R.string.weather_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = Description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mChannel.setShowBadge(false)
            notificationManager.createNotificationChannel(mChannel)


            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.temp_now))
                .setContentText((weatherResponse.value?.temperature?.temp?:"" + ""))


            val resultIntent = Intent(context, MainActivity::class.java)
            val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(MainActivity::class.java)
            stackBuilder.addNextIntent(resultIntent)
            val resultPendingIntent: PendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            builder.setContentIntent(resultPendingIntent)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }
    }
}