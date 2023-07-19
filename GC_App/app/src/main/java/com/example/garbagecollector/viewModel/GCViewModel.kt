package com.example.garbagecollector.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.garbagecollector.ORMDatabase.GCDatabase
import com.example.garbagecollector.ORMDatabase.GCUsers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GCViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = GCDatabase.getDatabase(application).getDao()
    private val repository : GCRepository = GCRepository(dao)
    val allUsers: LiveData<List<GCUsers>> = repository.allUsers

    fun addUser(user:GCUsers) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(user)
    }

    fun updateDateTime(name:String,password: String, date:String, time:String) = viewModelScope.launch(Dispatchers.IO){
        repository.updateDateTime(name,password,date,time)
    }

    fun getIDforSignup(name:String, email:String, password:String) : LiveData<Int?> {
        return repository.getIDforSignup(name, email, password)
    }

    fun getIDforLogin(name:String, password:String) : LiveData<Int?> {
        return repository.getIDforLogin(name, password)
    }
}