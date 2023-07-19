package com.example.garbagecollector.ORMDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GCDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user:GCUsers)

    @Query("UPDATE GCUsers SET date=:date AND time=:time WHERE name=:name AND password =:password")
    suspend fun updateDateTime(name:String,password: String,date:String,time:String)

    @Query("SELECT id FROM GCUsers WHERE name=:name AND email=:email AND password=:password")
    fun getIDforSignup(name:String, email:String, password:String) : LiveData<Int?>

    @Query("SELECT id FROM GCusers WHERE name=:name AND password=:password")
    fun getIDforLogin(name: String,password: String) : LiveData<Int?>

    @Query("SELECT * FROM GCUsers ORDER BY date DESC, time DESC")
    fun getAllItemsOrderedByDateAndTime(): LiveData<List<GCUsers>>



}