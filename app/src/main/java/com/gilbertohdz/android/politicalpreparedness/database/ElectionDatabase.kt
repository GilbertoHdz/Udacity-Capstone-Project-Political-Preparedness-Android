package com.gilbertohdz.android.politicalpreparedness.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gilbertohdz.android.politicalpreparedness.network.models.Election

@Database(entities = [Election::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ElectionDatabase: RoomDatabase() {

    abstract fun electionDao(): ElectionDao
}