package com.gilbertohdz.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import com.gilbertohdz.android.politicalpreparedness.network.models.Election

interface ElectionDataSource {
    fun getElections(): LiveData<List<Election>>
    suspend fun getElectionById(id: Int): Election?
    fun getSavedElections(): LiveData<List<Election>>
    suspend fun saveElection(election: Election)
    suspend fun deleteById(id: Int)
    suspend fun refreshElections()
}