package com.gilbertohdz.android.politicalpreparedness.launch

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilbertohdz.android.politicalpreparedness.databinding.FragmentLaunchBinding
import com.gilbertohdz.android.politicalpreparedness.election.adapter.ElectionListener

class LaunchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentLaunchBinding.inflate(inflater)
        binding.lifecycleOwner = this

        // TODO: Setup Launch fragment
        // binding.representativeButton.setOnClickListener { navToRepresentatives() }
        // binding.upcomingButton.setOnClickListener { navToElections() }

        return binding.root
    }

    private fun navToElections() {
        // TODO: Launch fragment navigation
        // this.findNavController().navigate(LaunchFragmentDirections.actionLaunchFragmentToElectionsFragment())
    }

    private fun navToRepresentatives() {
        // TODO: Launch fragment navigation
        // this.findNavController().navigate(LaunchFragmentDirections.actionLaunchFragmentToRepresentativeFragment())
    }

}
