package com.example.rps_calorietracker

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.rps_calorietracker.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var app:MyApplication
    val br: BroadcastReceiver = MyNotificationReceiver()

    companion object {
        const val CHANNEL_ID = "com.example.rps_calorietracker"
        private var notificationId = 0
        fun getNotificationUniqueID(): Int {
            return notificationId++ // fix
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MyApplication
    }

    override fun onStart() {
        super.onStart()
        sendNotification()
        myAlarm()
    }

    fun sendNotification(){
        val intent = Intent(applicationContext, MyNotificationReceiver::class.java)
        intent.putExtra("Suggestion", getSuggestion())
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
           // PendingIntent.FLAG_UPDATE_CURRENT,
           // PendingIntent.FLAG_ONE_SHOT
            // PendingIntent.FLAG_IMMUTABLE,
            PendingIntent.FLAG_MUTABLE  //Niam blage veze,samo vem da mi edino toti fflag delo
        )

        sendBroadcast(intent)

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun myAlarm() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 17) // Alarm se prozi ob 17.00 vsak dan
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        if (calendar.getTime().compareTo(Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1)
        val intent = Intent(applicationContext, MyNotificationReceiver::class.java)
        intent.putExtra("Suggestion", getSuggestion())
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent
        )
    }

    fun getSuggestion() : String
    {
        var caloriesBurned = 0
        for(activity in app.dataActivity.list)
            caloriesBurned += activity.burnedCalories.toInt()
        var calorieIntake = 0
        for(meal in app.dataFoods.listOfFoods)
            calorieIntake += meal.cal
        val suggestionData = calorieIntake - caloriesBurned

        // Tukaj pride nabor priporocil, odzivanje na trenutno stanje:

        if(suggestionData > app.calorieGoal)
        {
            // TODO dodaj priporocila
        } else if (caloriesBurned < app.caloriesBurnedGoal)
        {
            // Primer:
            return "Manjka vam se " + (app.caloriesBurnedGoal - caloriesBurned).toString()
        }

        return getString(R.string.notifications_doing_great)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}