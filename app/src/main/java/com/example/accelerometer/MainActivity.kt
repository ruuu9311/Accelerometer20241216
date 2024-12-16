package com.example.accelerometer

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.accelerometer.ui.theme.AccelerometerTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.material3.Button

import androidx.compose.ui.platform.LocalContext
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.compose.runtime.LaunchedEffect


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AccelerometerTheme {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                FirstScreen()
            }
        }
    }
}
@Composable
fun FirstScreen() {
    var msg by remember { mutableStateOf("加速感應器實例") }
    var msg2 by remember { mutableStateOf("") }
    var showSecond by remember { mutableStateOf(false) }

    var xTilt by remember { mutableStateOf(0f) }
    var yTilt by remember { mutableStateOf(0f) }
    var zTilt by remember { mutableStateOf(0f) }

    val context = LocalContext.current
    val sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null){
                xTilt = event.values[0]
                yTilt = event.values[1]
                zTilt = event.values[2]
                msg = "加速感應器實例\n" + ("x軸:"+xTilt.toString()
                        +"\ny軸:"+ yTilt.toString() +"\nz軸:"+zTilt.toString())
            }

        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        }

    }

    LaunchedEffect(Unit) { // composable首次載入時，註冊監聽事件
        sensorManager.registerListener(
            sensorEventListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Green),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(msg)
        Text(msg2)
        Button (onClick = {showSecond = true}) {
            Text(text = "跳轉畫面2")
        }
    }
    if (showSecond){
        SecondScreen()
    }
}

    @Composable
    fun SecondScreen() {
        var showfirst by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize().background(Color.Yellow),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Button(onClick = { showfirst = true })
            {
                Text(text = "返回畫面1")
            }
        }
        if (showfirst) {
            FirstScreen()
        }
    }