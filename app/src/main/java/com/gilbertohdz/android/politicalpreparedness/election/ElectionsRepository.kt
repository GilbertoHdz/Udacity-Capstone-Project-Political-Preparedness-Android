package com.gilbertohdz.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gilbertohdz.android.politicalpreparedness.database.ElectionDao
import com.gilbertohdz.android.politicalpreparedness.database.ElectionDatabase
import com.gilbertohdz.android.politicalpreparedness.network.CivicsApiService
import com.gilbertohdz.android.politicalpreparedness.network.models.Election
import com.gilbertohdz.android.politicalpreparedness.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository(
        private val electionDao: ElectionDao,
        private val service: CivicsApiService,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
        private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ElectionDataSource {

    override val savedElections: LiveData<List<Election>>
        get() = electionDao.getSavedElections()

    override val upcomingElections: LiveData<List<Election>>
        get() = electionDao.getElections()

    override suspend fun saveElection(election: Election) = withContext(ioDispatcher) {
        election.isSaved = !election.isSaved
        electionDao.insertElection(election)
    }

    override suspend fun getElectionById(id: Int): Election? = withContext(ioDispatcher) {
        return@withContext try {
            electionDao.getElectionById(id)
        } catch (ex: Exception) {
            null
        }
    }

    override suspend fun deleteById(id: Int) = with(ioDispatcher) {
        electionDao.deleteById(id)
    }

    override suspend fun refreshElections() = withContext(mainDispatcher) {
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