package com.example.rps_calorietracker

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.rps_calorietracker.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var app: MyApplication

    companion object {
        const val CHANNEL_ID = "com.example.rps_calorietracker"
        private var notificationId = 0
        fun getNotificationUniqueID(): Int {
            return notificationId++
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

    fun sendNotification() {
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

        // Enako kot zgoraj
        val intent = Intent(applicationContext, MyNotificationReceiver::class.java)
        // Pridobi suggestion
        intent.putExtra("Suggestion", getSuggestion())

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        // Prozi se vsak dan -> posiljamo pendingIntent
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent
        )
    }

    fun getSuggestion(): String {
        var caloriesBurned = 0

        for (activity in app.dataActivity.list)
            caloriesBurned += activity.burnedCalories.toInt()
        var calorieIntake = 0
        for (meal in app.dataFoods.listOfFoods)
            calorieIntake += meal.cal
        val suggestionData = calorieIntake - caloriesBurned

        // Tukaj pride nabor priporocil, odzivanje na trenutno stanje:
        println(" Priporocljivo: $suggestionData Zauzito: $calorieIntake Skurjeno: $caloriesBurned AppCalories: ${app.caloriesBurnedGoal} CalorieIntakeGoal: ${app.calorieGoal}")

        if (calorieIntake < app.calorieGoal && caloriesBurned < app.caloriesBurnedGoal) {
            return "Manjka vam se " + (app.caloriesBurnedGoal - caloriesBurned).toString() + "kalorij do minimalnega dnevnega cilja pokurjenih kalorij" +
                    "manjka vam tudi" + (app.calorieGoal - calorieIntake).toString() + " zaužitih kalorij"
        } else if (caloriesBurned < app.caloriesBurnedGoal) {
            // Primer:
            return "Manjka vam se " + (app.caloriesBurnedGoal - caloriesBurned).toString() + "kalorij do minimalnega dnevnega cilja pokurjenih kalorij"
        } else if (calorieIntake < app.calorieGoal) {
                if (app.calorieGoal - calorieIntake >= 1500){
                    return "Manjka vam se " + (app.calorieGoal - calorieIntake).toString() + " zaužitih kalorij. Priporačamo vam zdrav večji obrok: okoli 650 do 800 kalorij(Kosilo ali večerja)."
                }else if (app.calorieGoal - calorieIntake >= 1250){
                    return "Manjka vam se " + (app.calorieGoal - calorieIntake).toString() + " zaužitih kalorij. Priporačamo vam zdrav srednji obrok: okoli 450 do 650 kalorij(Malica)."
                }else if (app.calorieGoal - calorieIntake >= 1000){
                    return "Manjka vam se " + (app.calorieGoal - calorieIntake).toString() + " zaužitih kalorij. Priporačamo vam zdrav manjši obrok: okoli 400 do 500 kalorij(Manjši obrok ali zajtrk)."
                }else{
                    return "Manjka vam se " + (app.calorieGoal - calorieIntake).toString() + " zaužitih kalorij. Priporačamo vam več majhnih obrokov ki se gibljejo med 100 do 300 kalorij(Sadje, hrana z veliko vlaken(fibers))."
                }

        } else if (suggestionData > app.calorieGoal) {
            if ((suggestionData - app.calorieGoal) <= 200) return "Priporocamo vsaj kratek sprehod, saj Imate: " + (suggestionData - app.calorieGoal).toString() + "več kalorij za porabiti od zeljenih"
            else if ((suggestionData - app.calorieGoal) <= 400) return "Priporocamo igro tenisa/kosarke/nogometa, saj Imate: " + (suggestionData - app.calorieGoal).toString() + "več kalorij za porabiti od zeljenih"
            else if ((suggestionData - app.calorieGoal) <= 700) return "Priporocamo Fittness, saj Imate: " + (suggestionData - app.calorieGoal).toString() + "več kalorij za porabiti od zeljenih"
            else return "Priporocamo daljšo aktivnost, saj Imate kar: " + (suggestionData - app.calorieGoal).toString() + "več kalorij za porabiti od zeljenih"
        }

        return getString(R.string.notifications_doing_great)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}