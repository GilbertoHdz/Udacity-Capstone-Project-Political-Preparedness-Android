package com.gilbertohdz.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbertohdz.android.politicalpreparedness.network.models.Election
import com.gilbertohdz.android.politicalpreparedness.utils.SingleEvent
import com.gilbertohdz.android.politicalpreparedness.utils.SingleLiveEvent
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(
        private val dataSource: ElectionDataSource
): ViewModel() {

    // DONE: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    // DONE: Create live data val for upcoming elections
    val upcomingElections = dataSource.upcomingElections
    // DONE: Create live data val for saved elections
    val savedElections: LiveData<List<Election>>
     get() = dataSource.savedElections

    init{
        refreshElections()
    }

    private fun refreshElections(){
        viewModelScope.launch {
            dataSource.apply {
                refreshElections()
            }
        }
    }

    // DONE: Create functions to navigate to saved or upcoming election voter info
    private val _navigateToElectionDetail = MutableLiveData<Election>()
    val navigateToElectionDetail
        get() = _navigateToElectionDetail

    fun onElectionItemClicked(election: Election) {
        _navigateToElectionDetail.value = election
    }

    fun onElectionItemNavigated() {
        _navigateToElectionDetail.value = null
    }
}