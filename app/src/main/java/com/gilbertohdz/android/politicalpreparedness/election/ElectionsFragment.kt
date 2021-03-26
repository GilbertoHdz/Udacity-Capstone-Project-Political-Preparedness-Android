package com.gilbertohdz.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gilbertohdz.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.gilbertohdz.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.gilbertohdz.android.politicalpreparedness.election.adapter.ElectionListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ElectionsFragment: Fragment() {

    // DONE: Declare ViewModel
    private val viewModel: ElectionsViewModel by viewModel()

    // DONE: Initiate recycler adapters
    private lateinit var upcomingElectionListAdapter: ElectionListAdapter
    private lateinit var savedElectionListAdapter: ElectionListAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // DONE: Add ViewModel values and create ViewModel

        // DONE: Add binding values
        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // DONE: Link elections to voter info
        viewModel.navigateToElectionDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                // clear the event
                viewModel.onElectionItemNavigated()
               this.findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it.id, it.division))
            }
        })

        // DONE: Populate recycler adapters
        upcomingElectionListAdapter = ElectionListAdapter(ElectionListener {
            viewModel.navigateToElectionDetail
            viewModel.onElectionItemClicked(it)
        })

        binding.upcomingElectionsRecyclerView.adapter = upcomingElectionListAdapter

        savedElectionListAdapter = ElectionListAdapter(ElectionListener {
            viewModel.navigateToElectionDetail
            viewModel.onElectionItemClicked(it)
        })

        binding.savedElectionsRecyclerView.adapter = savedElectionListAdapter

        return binding.root
    }

    // DONE: Refresh adapters when fragment loads

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        electionsObserver()
    }

    private fun electionsObserver() {
        viewModel.upcomingElections.observe(viewLifecycleOwner, Observer { elections ->
            elections?.apply {
                upcomingElectionListAdapter.submitList(elections)
            }
        })

        viewModel.savedElections.observe(viewLifecycleOwner, Observer { elections ->
            elections?.apply {
                savedElectionListAdapter.submitList(elections)
            }
        })
    }

}