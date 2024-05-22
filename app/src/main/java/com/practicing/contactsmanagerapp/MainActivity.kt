package com.practicing.contactsmanagerapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicing.contactsmanagerapp.databinding.ActivityMainBinding
import com.practicing.contactsmanagerapp.repository.ContactRepository
import com.practicing.contactsmanagerapp.roomDatabase.Contact
import com.practicing.contactsmanagerapp.roomDatabase.ContactDatabase
import com.practicing.contactsmanagerapp.viewModel.ContactViewModel
import com.practicing.contactsmanagerapp.viewModel.ViewModelFactory
import com.practicing.contactsmanagerapp.views.MyRecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactViewModel: ContactViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ROOM Database
        val dao = ContactDatabase.getInstance(applicationContext).contactDao
        val repository = ContactRepository(dao)
        val factory = ViewModelFactory(repository)

        //View Model
        contactViewModel = ViewModelProvider(this, factory).get(ContactViewModel::class.java)
        binding.contactViewModel = contactViewModel
        // use this: LiveData and Data Binding integration
        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        DisplayUsersList()
    }

    private fun DisplayUsersList() {
        contactViewModel.contacts.observe(this, Observer { binding.recyclerView.adapter = MyRecyclerViewAdapter(it, { selectedItem: Contact -> contactItemClicked(selectedItem) }) })
    }

    private fun contactItemClicked(selectedItem: Contact) {
        Toast.makeText(this, "User: ${selectedItem.name}", Toast.LENGTH_SHORT).show()
        contactViewModel.initUpdateAndDelete(selectedItem)
    }
}