package com.example.vk_test.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vk_test.R
import com.example.vk_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}