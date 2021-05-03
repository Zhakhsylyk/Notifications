package com.example.notifications

import android.app.*
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.getSystemService
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}

private fun setTimePickerDialogShow(){
    val timePickerDialog =
        TimePickerDialog(this, { _, hour, minute ->
            this.hour = hour
            this.minute = minute

            calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR,hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND,0)

            timeTv.test = DateFormat.format("hh:mm",calendar)

            setNotification()
        },12,0,true)
    timePickerDialog.updateTime(hour,minute)
    timePickerDialog.show()
}
private fun setNotification() {
    val channel = NotificationChannel(
        "timeNotificationId",
        "TimePickerReminderChannel",
        NotificationManager.IMPORTANCE_DEFAULT
    )

    val notificationManager =
        getSystemService(NotificationManager::class.java) as NotificationManager
    notificationManager.createNotificationChannel(channel)


    val intent = Intent(this, ReminderBroadcast::class.java)
    val pendingIntent = PendingIntent.getBroadcast(this, 200, intent, 0)
    val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager


    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
}

