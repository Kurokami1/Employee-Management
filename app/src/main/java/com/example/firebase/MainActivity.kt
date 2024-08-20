package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebase.databinding.ActivityMainBinding

private lateinit var binding : ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       binding.btnInsert.setOnClickListener {
           val intent = Intent(this, Insert_Activity::class.java)
           startActivity(intent)
       }
        binding.btnFetch.setOnClickListener {
           val intent = Intent(this, Fetching_Activity::class.java)
           startActivity(intent)
       }
    }
}