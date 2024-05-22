package com.practicing.contactsmanagerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicing.contactsmanagerapp.repository.ContactRepository

/* If your viewModel has a constructor with parameters
you can't use the default constructor that the viewModel
framework provides

ViewModelFactory: pass the required parameters to the ViewModel
 */
class ViewModelFactory(private val contactRepository: ContactRepository): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ContactViewModel::class.java))
        {
            return ContactViewModel(contactRepository) as T
        }
        else
        {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}