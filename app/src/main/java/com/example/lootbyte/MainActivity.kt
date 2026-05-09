package com.example.lootbyte

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lootbyte.databinding.ActivityMainBinding
import com.example.lootbyte.ui.auth.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Forzar Login
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }
    }
}
//        PRUEBAS
//        val btnAdminTest = findViewById<Button>(R.id.btnAdminTest)
//
//        btnAdminTest.setOnClickListener {
//            val intent = Intent(this, AdminActivity::class.java)
//            startActivity(intent)
//        }