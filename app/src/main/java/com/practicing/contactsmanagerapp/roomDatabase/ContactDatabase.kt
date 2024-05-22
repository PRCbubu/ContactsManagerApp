package com.practicing.contactsmanagerapp.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase()
{
    abstract val contactDao: ContactDAO

    companion object{
        // @Volatile prevents any other thread from immediately seeing changes to the instance and avoid race conditions in multithreaded environments
        @Volatile
        private var INSTANCE: ContactDatabase?=null
        fun getInstance(context: Context): ContactDatabase
        {
            synchronized(this)
            {
                var instance = INSTANCE
                if(instance == null)
                {
                    instance = Room.databaseBuilder(context.applicationContext, ContactDatabase::class.java, "contact_database").build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}