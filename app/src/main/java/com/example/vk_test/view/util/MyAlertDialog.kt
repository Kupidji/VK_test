package com.example.vk_test.view.util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.vk_test.R

class MyAlertDialog : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выбирите действие")
                .setMessage("Помахать собеседнику?")
                .setIcon(R.drawable.ic_launcher_background)
                .setCancelable(true)
                .setPositiveButton("Да") { _, _ ->
                    Toast.makeText(
                        activity,
                        "Вы помахали собеседнику",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton(
                    "Нет"
                ) { _, _ ->
                    Toast.makeText(
                        activity, "Ну и ладно :)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}