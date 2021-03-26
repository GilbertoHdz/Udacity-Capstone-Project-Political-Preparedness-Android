package com.gilbertohdz.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gilbertohdz.android.politicalpreparedness.network.CivicsApiService
import java.lang.IllegalArgumentException

// DONE: Create Factory to generate VoterInfoViewModel with provided election datasource
class VoterInfoViewModelFactory(
        private val dataSource: ElectionDataSource,
        private val service: CivicsApiService
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
            return VoterInfoViewModel(dataSource, service) as T
        }
        throw IllegalArgumentException("")
    }
}