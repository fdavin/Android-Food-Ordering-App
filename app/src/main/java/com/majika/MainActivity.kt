package com.majika

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.majika.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager : SensorManager
    private var tempSensor : Sensor? = null
    private var temperature : Float = Float.NaN
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (tempSensor == null){
            Toast.makeText(this , "Temperature sensor is not available",
                Toast.LENGTH_LONG).show()
        }
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_twibbon, R.id.navigation_restoran, R.id.navigation_menu, R.id.navigation_keranjang
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Temporary QR Code Scanner Button (Pembayaran)
        /*
        val bayarBtn = findViewById<FloatingActionButton>(R.id.qr_code_scanner_btn)
        bayarBtn.setOnClickListener {
            val i = Intent(this, PembayaranActivity::class.java)
            startActivity(i)
        }*/
    }
    private fun loadAmbientTemperature() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        } else {

        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val temp_text: TextView = findViewById<TextView>(R.id.temperature)
        if (sensorEvent.values.size > 0){
            temperature = sensorEvent.values[0]
            temp_text.text = "${temperature}°C"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}