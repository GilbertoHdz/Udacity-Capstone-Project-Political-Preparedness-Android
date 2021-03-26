package com.gilbertohdz.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gilbertohdz.android.politicalpreparedness.databinding.FragmentElectionBinding

class ElectionsFragment: Fragment() {

    // TODO: Declare ViewModel
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel

        // DONE: Add binding values
        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters

        return binding.root
    }

    //TODO: Refresh adapters when fragment loads

}