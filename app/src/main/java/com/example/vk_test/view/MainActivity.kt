package com.example.vk_test.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import com.example.vk_test.R
import com.example.vk_test.databinding.ActivityMainBinding
import com.example.vk_test.view.util.MyAlertDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private var appsStatus = false
    private var cameraStatus = false
    private var micStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomSheet()

        binding.message.setOnClickListener {

        }

        binding.person.setOnClickListener {

        }

        binding.apps.setOnClickListener {
            switchUsers()
        }

        binding.camera.setOnClickListener {
            switchCam()
        }

        binding.mic.setOnClickListener {
            switchMic()
        }

        binding.hand.setOnClickListener {
            sayHello()
        }

        binding.callDown.setOnClickListener {
            finish()
        }

    }

    private fun sayHello() {
        val alert = MyAlertDialog()
        val manager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        alert.show(transaction, "sayHello")
    }

    private fun switchCam() {
        if (cameraStatus) {
            cameraStatus = false
            binding.camera.setImageResource(R.drawable.camera_off_60)
        }
        else {
            cameraStatus = true
            binding.camera.setImageResource(R.drawable.camera_on_60)
        }
    }
    private fun switchUsers() {
        if (appsStatus) {
            binding.userBackground2.setImageResource(R.drawable.user_background2)
            binding.avatar2.setImageResource(R.drawable.avatar2_48)
            binding.name2.text = binding.name1.text
            if (binding.mutedMicro.isVisible) {
                if (binding.mutedMicro2.isVisible) {
                    binding.mutedMicro.visibility = View.VISIBLE
                }
                else binding.mutedMicro.visibility = View.GONE

                binding.mutedMicro2.visibility = View.VISIBLE
            }
            else binding.mutedMicro2.visibility = View.GONE

            binding.userBackground1.setImageResource(R.drawable.user_background1)
            binding.avatar1.setImageResource(R.drawable.avatar1_48)
            binding.name1.setText(R.string.you)

            appsStatus = false
        }
        else {
            binding.userBackground1.setImageResource(R.drawable.user_background2)
            binding.avatar1.setImageResource(R.drawable.avatar2_48)
            binding.name1.text = binding.name2.text
            if (binding.mutedMicro2.isVisible) {
                if (binding.mutedMicro.isVisible) {
                    binding.mutedMicro2.visibility = View.VISIBLE
                }
                else binding.mutedMicro2.visibility = View.GONE

                binding.mutedMicro.visibility = View.VISIBLE
            }
            else binding.mutedMicro.visibility = View.GONE

            binding.userBackground2.setImageResource(R.drawable.user_background1)
            binding.avatar2.setImageResource(R.drawable.avatar1_48)
            binding.name2.setText(R.string.you)

            appsStatus = true
        }
    }

    private fun switchMic() {
        if (micStatus) {
            micStatus = false
            binding.mic.setImageResource(R.drawable.mic_on_60)
            if (appsStatus) {
                binding.mutedMicro2.visibility = View.GONE
            }
            else binding.mutedMicro.visibility = View.GONE
        }
        else {
            micStatus = true
            binding.mic.setImageResource(R.drawable.mic_off_60)
            if (appsStatus) {
                binding.mutedMicro2.visibility = View.VISIBLE
            }
            else binding.mutedMicro.visibility = View.VISIBLE
        }
    }

    private fun bottomSheet(){
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}