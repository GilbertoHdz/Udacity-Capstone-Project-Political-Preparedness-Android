package com.gilbertohdz.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import com.gilbertohdz.android.politicalpreparedness.network.models.Election

interface ElectionDataSource {
    val savedElections: LiveData<List<Election>>
    val upcomingElections: LiveData<List<Election>>
    suspend fun getElectionById(id: Int): Election?
    suspend fun saveElection(election: Election)
    suspend fun deleteById(id: Int)
    suspend fun refreshElections()
}