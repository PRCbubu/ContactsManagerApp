package com.practicing.contactsmanagerapp.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practicing.contactsmanagerapp.R
import com.practicing.contactsmanagerapp.databinding.CardItemBinding
import com.practicing.contactsmanagerapp.roomDatabase.Contact

class MyRecyclerViewAdapter(private val contactList: List<Contact>, private val clickListener: (Contact) -> Unit): RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>()
{
    class MyViewHolder(val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(contact: Contact, clickListener: (Contact) -> Unit)
        {
            binding.nameTextView.text = contact.name
            binding.emailTextView.text = contact.email

            binding.listItemLayout.setOnClickListener {
                clickListener(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.card_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactList[position], clickListener)
    }

}
