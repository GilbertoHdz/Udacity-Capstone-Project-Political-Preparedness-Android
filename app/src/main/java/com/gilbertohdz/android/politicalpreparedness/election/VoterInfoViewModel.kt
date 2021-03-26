package com.gilbertohdz.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbertohdz.android.politicalpreparedness.database.ElectionDao
import com.gilbertohdz.android.politicalpreparedness.network.CivicsApiService
import com.gilbertohdz.android.politicalpreparedness.network.models.Division
import com.gilbertohdz.android.politicalpreparedness.network.models.Election
import com.gilbertohdz.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VoterInfoViewModel(
        private val dataSource: ElectionDataSource,
        private val service: CivicsApiService
) : ViewModel() {

    // DONE: Add live data to hold voter info
    val voterInfo = MutableLiveData<VoterInfoResponse>()

    private val _url = MutableLiveData<String?>()
    val url: LiveData<String?>
        get() = _url

    fun setUrl(url: String?){
        _url.value = url
    }

    var election: Election? = null

    // DONE: Add var and methods to populate voter info
    fun initValues() {
        viewModelScope.launch {
            fetchVotersInfo()
        }
    }

    private suspend fun fetchVotersInfo() = withContext(Dispatchers.IO) {

        val response = service.getVoterInfo(getDivisionAdr(election!!.division), election!!.id)

        if (response.isSuccessful) {
            voterInfo.postValue(response.body())
        }
    }

    private fun getDivisionAdr(division: Division): String{
        val result = StringBuilder()

        if (division.state   != "") result.append(division.state)
        if (division.country != ""){
            if (result.toString()!="")
                result.append(", ")

            result.append(division.country)
        }

        return result.toString()
    }

    // DONE: Add var and methods to support loading URLs

    // DONE: Add var and methods to save and remove elections to local database
    // DONE: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    fun onFollowButtonClick(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val election = dataSource.getElectionById(election!!.id) ?: election!!
                dataSource.saveElection(election)
            }
        }
    }

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}