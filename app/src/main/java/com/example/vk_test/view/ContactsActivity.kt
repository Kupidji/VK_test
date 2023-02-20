package com.example.vk_test.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vk_test.data.Contact
import com.example.vk_test.databinding.ActivityContactsBinding
import com.example.vk_test.util.Constants
import com.example.vk_test.util.ContactAdapter

class ContactsActivity : AppCompatActivity(), ContactAdapter.ContactListener {

    private lateinit var binding : ActivityContactsBinding
    private var adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

    override fun onCallClickListener(contact: Contact) {
        val intent = Intent()
        intent.putExtra(Constants.CONTACT, contact)
        setResult(RESULT_OK, intent)
        finish()
    }
}