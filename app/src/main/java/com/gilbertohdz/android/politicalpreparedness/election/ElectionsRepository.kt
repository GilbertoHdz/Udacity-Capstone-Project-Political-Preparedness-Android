package com.gilbertohdz.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import com.gilbertohdz.android.politicalpreparedness.database.ElectionDao
import com.gilbertohdz.android.politicalpreparedness.database.ElectionDatabase
import com.gilbertohdz.android.politicalpreparedness.network.CivicsApiService
import com.gilbertohdz.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository(
        private val electionDao: ElectionDao,
        private val service: CivicsApiService,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
        private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ElectionDataSource {

    override fun getElections(): LiveData<List<Election>> = with(ioDispatcher) {
        return electionDao.getElections()
    }

    override fun getSavedElections(): LiveData<List<Election>> {
        return electionDao.getSavedElections()
    }

    override suspend fun saveElection(election: Election) = with(ioDispatcher){
        election.isSaved = true
        electionDao.insertElection(election)
    }

    override suspend fun getElectionById(id: Int): Election? = with(ioDispatcher){
        return electionDao.getElectionById(id)
    }

    override suspend fun deleteById(id: Int) = with(ioDispatcher) {
        electionDao.deleteById(id)
    }

    override suspend fun refreshElections() = with(mainDispatcher) {
        try {
            val electionResponse = service.getElections()
            if (electionResponse.isSuccessful && !electionResponse.body()?.elections.isNullOrEmpty()) {
                withContext(Dispatchers.IO) {
                    electionDao.insertAll(electionResponse.body()!!.elections)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}