package com.example.garbagecollector.viewModel

import androidx.lifecycle.LiveData
import com.example.garbagecollector.ORMDatabase.GCDao
import com.example.garbagecollector.ORMDatabase.GCUsers

class GCRepository(val dao:GCDao) {

    val allUsers: LiveData<List<GCUsers>> = dao.getAllItemsOrderedByDateAndTime()

    suspend fun insert(user:GCUsers) {
        return dao.insert(user)
    }
    suspend fun updateDateTime(name:String,password: String,date:String,time:String) {
        dao.updateDateTime(name,password,date,time)
    }
    fun getIDforSignup(name:String, email:String, password:String) : LiveData<Int?> {
        return dao.getIDforSignup(name, email, password)
    }
    fun getIDforLogin(name:String,password: String) : LiveData<Int?> {
        return dao.getIDforLogin(name,password)
    }
}