package com.practicing.contactsmanagerapp.repository

import com.practicing.contactsmanagerapp.roomDatabase.Contact
import com.practicing.contactsmanagerapp.roomDatabase.ContactDAO

// Repository: Acts as a bridge between the ViewModel and Data Source
class ContactRepository(private val contactDAO: ContactDAO)
{
    val contacts = contactDAO.getAllContacts()

    suspend fun insert(contact: Contact): Long
    {
        return contactDAO.insertContact(contact)
    }

    suspend fun delete(contact: Contact)
    {
        return contactDAO.deleteContact(contact)
    }

    suspend fun update(contact: Contact)
    {
        return contactDAO.updateContact(contact)
    }

    suspend fun deleteAll()
    {
        return contactDAO.deleteAllContacts()
    }

}