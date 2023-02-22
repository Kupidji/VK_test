package com.example.vk_test.view

import android.content.Intent
import android.icu.text.ListFormatter.Width
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import com.example.vk_test.R
import com.example.vk_test.data.Contact
import com.example.vk_test.databinding.ActivityCallBinding
import com.example.vk_test.util.Constants
import com.example.vk_test.util.MyAlertDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior


class CallActivity : AppCompatActivity() {

    lateinit var binding : ActivityCallBinding
    private var appsStatus = false
    private var currentAvatar = R.drawable.avatar2_48
    private var cameraStatus = false
    private var micStatus = false
    private var launcher : ActivityResultLauncher<Intent>? = null
    private var doubleBackToExitPressedOnce = false
    private var uiStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        bottomSheet()

        hideUI()

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val tempContact = (result.data?.getSerializableExtra(Constants.CONTACT) as Contact)
                currentAvatar = tempContact.avatar
                if (appsStatus) {
                    binding.avatar1.setImageResource(tempContact.avatar)
                    binding.name1.text = tempContact.name
                }
                else {
                    binding.avatar2.setImageResource(tempContact.avatar)
                    binding.name2.text = tempContact.name
                }

            }
            if (result.resultCode == Constants.RESULT_OK_CAMERA) {
                val tempContact = (result.data?.getSerializableExtra(Constants.CONTACT) as Contact)
                currentAvatar = tempContact.avatar
                cameraStatus = true
                binding.camera.setImageResource(R.drawable.camera_on_60)
                if (appsStatus) {
                    binding.avatar1.setImageResource(tempContact.avatar)
                    binding.name1.text = tempContact.name

                    binding.userBackground2.setImageResource(R.drawable.user_background_loading)
                    binding.avatar2.visibility = View.GONE
                    binding.progressBar2.visibility = View.VISIBLE
                    binding.name2.setText(R.string.you)
                }
                else {
                    binding.avatar2.setImageResource(tempContact.avatar)
                    binding.name2.text = tempContact.name

                    binding.userBackground1.setImageResource(R.drawable.user_background_loading)
                    binding.avatar1.visibility = View.GONE
                    binding.progressBar1.visibility = View.VISIBLE
                    binding.name1.setText(R.string.you)
                }

            }
        }

        binding.root.setOnClickListener {
            if (uiStatus) {
                val mLoadAnimation: Animation = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_in)
                mLoadAnimation.duration = 400
                binding.message.startAnimation(mLoadAnimation)
                binding.person.startAnimation(mLoadAnimation)
                binding.apps.startAnimation(mLoadAnimation)
                binding.groupUpperButtons.visibility = View.VISIBLE
                BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
                uiStatus = false
            }
            else {
                hideUI()
                val mLoadAnimation: Animation = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_out)
                mLoadAnimation.duration = 400
                binding.message.startAnimation(mLoadAnimation)
                binding.person.startAnimation(mLoadAnimation)
                binding.apps.startAnimation(mLoadAnimation)
                binding.groupUpperButtons.visibility = View.INVISIBLE
                BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
                uiStatus = true
            }

        }

        binding.arrowDown.setOnClickListener {
            Toast.makeText(this, "Приложение свернулось в миниатюру", Toast.LENGTH_SHORT).show()
        }

        binding.message.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.data = Uri.parse("sms:")
            startActivity(sendIntent)
        }

        binding.person.setOnClickListener {
            var i = Intent(this, ContactsActivity::class.java)
            launcher?.launch(i)
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

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Свернуть вызов?", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 3000)
    }

    private fun hideUI() {
        Handler().postDelayed(
            Runnable {
                val mLoadAnimation: Animation = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_out)
                mLoadAnimation.duration = 400
                binding.message.startAnimation(mLoadAnimation)
                binding.person.startAnimation(mLoadAnimation)
                binding.apps.startAnimation(mLoadAnimation)
                BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
                binding.groupUpperButtons.visibility = View.INVISIBLE
                uiStatus = true
            },
            4000
        )
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
            if (appsStatus) {
                binding.userBackground2.setImageResource(R.drawable.user_background1)
                binding.avatar2.visibility = View.VISIBLE
                binding.progressBar2.visibility = View.GONE
                binding.avatar2.setImageResource(R.drawable.avatar1_48)
            }
            else {
                binding.userBackground1.setImageResource(R.drawable.user_background1)
                binding.avatar1.visibility = View.VISIBLE
                binding.progressBar1.visibility = View.GONE
                binding.avatar1.setImageResource(R.drawable.avatar1_48)
            }
        }
        else {
            cameraStatus = true
            binding.camera.setImageResource(R.drawable.camera_on_60)
            if (appsStatus) {
                binding.userBackground2.setImageResource(R.drawable.user_background_loading)
                binding.avatar2.visibility = View.GONE
                binding.progressBar2.visibility = View.VISIBLE
            }
            else {
                binding.userBackground1.setImageResource(R.drawable.user_background_loading)
                binding.avatar1.visibility = View.GONE
                binding.progressBar1.visibility = View.VISIBLE

            }

        }
    }

    private fun switchUsers() {
        if (appsStatus) { //Если you в нижней плитке то...
            if (cameraStatus) { //Если камера включена то...
                binding.userBackground2.setImageResource(R.drawable.user_background2)
                binding.avatar2.visibility = View.VISIBLE
                binding.avatar2.setImageResource(currentAvatar)
                binding.progressBar2.visibility = View.GONE
                binding.name2.text = binding.name1.text

                if (binding.mutedMicro.isVisible) {
                    if (binding.mutedMicro2.isVisible) {
                        binding.mutedMicro.visibility = View.VISIBLE
                    }
                    else binding.mutedMicro.visibility = View.GONE

                    binding.mutedMicro2.visibility = View.VISIBLE
                }
                else binding.mutedMicro2.visibility = View.GONE

                binding.userBackground1.setImageResource(R.drawable.user_background_loading)
                binding.avatar1.visibility = View.GONE
                binding.progressBar1.visibility = View.VISIBLE
                binding.name1.setText(R.string.you)
            }
            else { //Если камера не включена то...
                binding.userBackground2.setImageResource(R.drawable.user_background2)
                binding.avatar2.setImageResource(currentAvatar)
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
            }
            appsStatus = false
        }
        else { //Если you в верхней плитке то...
            if (cameraStatus) { //Если камера включена то...
                binding.userBackground1.setImageResource(R.drawable.user_background2)
                binding.avatar1.visibility = View.VISIBLE
                binding.progressBar1.visibility = View.GONE
                binding.avatar1.setImageResource(currentAvatar)
                binding.name1.text = binding.name2.text

                if (binding.mutedMicro2.isVisible) {
                    if (binding.mutedMicro.isVisible) {
                        binding.mutedMicro2.visibility = View.VISIBLE
                    }
                    else binding.mutedMicro2.visibility = View.GONE

                    binding.mutedMicro.visibility = View.VISIBLE
                }
                else binding.mutedMicro.visibility = View.GONE

                binding.userBackground2.setImageResource(R.drawable.user_background_loading)
                binding.avatar2.visibility = View.GONE
                binding.progressBar2.visibility = View.VISIBLE
                binding.name2.setText(R.string.you)
            }
            else { //Если камера не включена то...
                binding.userBackground1.setImageResource(R.drawable.user_background2)
                binding.avatar1.setImageResource(currentAvatar)
                binding.name1.text = binding.name2.text

                if (binding.progressBar1.isVisible) {
                    binding.progressBar1.visibility = View.GONE
                    binding.progressBar2.visibility = View.VISIBLE
                }

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
            }
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
            this.state = BottomSheetBehavior.STATE_EXPANDED
            //this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}