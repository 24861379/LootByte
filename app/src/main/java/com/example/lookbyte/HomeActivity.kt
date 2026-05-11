package com.example.lookbyte

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
    fun abrirProducto(view: View) {
        val intent = Intent(this, ProductActivity::class.java)
        startActivity(intent)
    }
}