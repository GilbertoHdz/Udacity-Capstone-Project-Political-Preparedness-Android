package com.gilbertohdz.android.politicalpreparedness.representative

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbertohdz.android.politicalpreparedness.network.CivicsApiService
import com.gilbertohdz.android.politicalpreparedness.network.models.Address
import com.gilbertohdz.android.politicalpreparedness.network.models.RepresentativeResponse
import com.gilbertohdz.android.politicalpreparedness.network.models.representatives
import com.gilbertohdz.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepresentativeViewModel(
        app: Application,
        private val civicsApiService: CivicsApiService
): ViewModel() {

    // DONE: Establish live data for representatives and address
    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() =_address

    // DONE: Create function to fetch representatives from API from a provided address

    fun getRepresentatives(address: Address) = viewModelScope.launch(Dispatchers.IO) {
        _address.postValue(address)

        val response = civicsApiService.getRepresentatives(address = address.toFormattedString())
        _representatives.postValue(response.body()?.representatives)
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    // DONE: Create function get address from geo location

    // DONE: Create function to get address from individual fields
}
