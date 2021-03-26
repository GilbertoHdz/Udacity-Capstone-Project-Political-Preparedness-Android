package com.gilbertohdz.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gilbertohdz.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    // DONE: Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(elections: List<Election>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElection(election: Election)

    // DONE: Add select all election query
    @Query("SELECT * FROM election_table")
    fun getElections(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table where isSaved = 1")
    fun getSavedElections(): LiveData<List<Election>>

    // DONE: Add select single election query
    @Query("SELECT * from election_table where id = :id")
    suspend fun getElectionById(id: Int): Election?

    // DONE: Add delete query
    @Query("DELETE FROM election_table where id=:id")
    fun deleteById(id: Int)

    // DONE: Add clear query
    @Query("DELETE FROM election_table")
    fun clear()
}