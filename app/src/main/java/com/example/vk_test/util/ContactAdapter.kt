package com.example.vk_test.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vk_test.R
import com.example.vk_test.data.Contact
import com.example.vk_test.databinding.ContactPatternBinding
import com.example.vk_test.view.ContactsActivity

class ContactAdapter(val listener: ContactListener) : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    var contactList = ArrayList<Contact>()

    init {
        val contact1 = Contact(R.drawable.avatar2_48, "Kirill Koksharov")
        val contact2 = Contact(R.drawable.avatar3_48, "Mihail Stepanov")
        val contact3 = Contact(R.drawable.avatar4_48, "Nikita Ipatov")
        val contact4 = Contact(R.drawable.avatar5_48, "Dmitriy Sapsanov")
        val contact5 = Contact(R.drawable.avatar6_48, "Black Diamond")
        contactList.add(contact1)
        contactList.add(contact2)
        contactList.add(contact3)
        contactList.add(contact4)
        contactList.add(contact5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ContactPatternBinding.inflate(view, parent, false)
        return ContactHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(contactList[position], listener)
    }

    override fun getItemCount(): Int = contactList.size

    class ContactHolder(
        private val binding: ContactPatternBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact, listener: ContactListener) {
            binding.avatar.setImageResource(contact.avatar)
            binding.name.text = contact.name

            binding.call.setOnClickListener {
                listener.onCallClickListener(contact)
            }
        }

    }

    interface ContactListener {
        fun onCallClickListener(contact: Contact)
    }

}