package com.example.garbagecollector.ORMDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GCUsers")
class GCUsers (
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "email") val email:String,
    @ColumnInfo(name = "password") val password:String,
    @ColumnInfo(name = "date") val date:String,
    @ColumnInfo(name = "time") val time:String
        )
{
    @PrimaryKey(autoGenerate = true) var id:Long = 0
}