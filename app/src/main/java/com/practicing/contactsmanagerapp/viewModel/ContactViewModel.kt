package com.practicing.contactsmanagerapp.viewModel


import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicing.contactsmanagerapp.repository.ContactRepository
import com.practicing.contactsmanagerapp.roomDatabase.Contact
import kotlinx.coroutines.launch

/*View model: stores and manage UI-related Data.
* It separates the UI-related logic from UI controller (Activity/Fragment)  */
class ContactViewModel(private val contactRepository: ContactRepository): ViewModel(), Observable
{
    val contacts = contactRepository.contacts
    private var isUpdateOrDelete = false
    private lateinit var contactToUpdateOrDelete: Contact

    // Data Binding with Live Data
    @Bindable
    val inputName = MutableLiveData<String?>()
    @Bindable
    val inputEmail = MutableLiveData<String?>()
    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun insert(contact: Contact) = viewModelScope.launch {
        contactRepository.insert(contact)
    }

    fun delete(contact: Contact) = viewModelScope.launch {
        contactRepository.delete(contact)

        // Resetting the buttons and fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun update(contact: Contact) = viewModelScope.launch {
        contactRepository.update(contact)

        // Resetting the buttons and fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun clearAll() = viewModelScope.launch {
        contactRepository.deleteAll()
    }

    fun saveOrUpdate()
    {
        if(isUpdateOrDelete)
        {
            //make an update
            contactToUpdateOrDelete.name = inputName.value!!
            contactToUpdateOrDelete.email = inputEmail.value!!
            update(contactToUpdateOrDelete)
        }
        else
        {
            //make an insert
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Contact(0, name, email))

            //Rest the name and email
            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearAllOrDelete()
    {
        if(isUpdateOrDelete)
        {
            delete(contactToUpdateOrDelete)
        }
        else
        {
            clearAll()
        }
    }

    fun initUpdateAndDelete(contact: Contact)
    {
        inputName.value = contact.name
        inputEmail.value = contact.email
        isUpdateOrDelete = true
        contactToUpdateOrDelete = contact
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}