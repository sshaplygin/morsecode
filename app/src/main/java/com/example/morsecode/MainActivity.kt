package com.example.morsecode

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.morsecode.menu.EducationFragment
import com.example.morsecode.menu.HomeFragment
import com.example.morsecode.menu.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : ComponentActivity() {

    private var isFlashlightOn = false
    private lateinit var drawerLayout: DrawerLayout

    private fun toggleFlashlight(context: Context) {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager;
        val cameraId = cameraManager.cameraIdList.getOrNull(0);
        if (cameraId == null) {
            Toast.makeText(context, "Устройство не поддерживает фонарик", Toast.LENGTH_SHORT).show()
            return;
        }

        try {
            cameraManager.setTorchMode(cameraId, !isFlashlightOn);
            isFlashlightOn = !isFlashlightOn;
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        AppCompatActivity().setSupportActionBar(toolbar)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_education ->  loadFragment(EducationFragment())
                R.id.nav_settings ->loadFragment(SettingsFragment())
            }
            true
        }

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        AppCompatActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

//    override fun onBackPressed() {
//        val view: View = findViewById(R.id.bottom_navigation)
//        if (drawerLayout.isDrawerOpen(view)) {
//            drawerLayout.closeDrawers()
//        } else {
//            super.onBackPressed()
//        }
//    }/
}