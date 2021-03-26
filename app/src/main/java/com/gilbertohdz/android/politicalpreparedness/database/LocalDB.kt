package com.gilbertohdz.android.politicalpreparedness.database

import android.content.Context
import androidx.room.Room

/**
 * Singleton class that is used to create an election db
 */
object LocalDB {

    fun createElectionDao(context: Context): ElectionDao {
        return Room.databaseBuilder(
                context.applicationContext,
                ElectionDatabase::class.java,
                "election_database.db"
        ).build().electionDao()
    }
}