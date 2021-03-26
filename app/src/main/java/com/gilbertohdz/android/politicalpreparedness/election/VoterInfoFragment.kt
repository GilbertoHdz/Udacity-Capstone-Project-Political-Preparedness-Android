package com.gilbertohdz.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.gilbertohdz.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class VoterInfoFragment : Fragment() {

    private val viewModel: VoterInfoViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // DONE: Add ViewModel values and create ViewModel
        // DONE: Add binding values
        val binding = FragmentVoterInfoBinding.inflate(inflater)
        val electionSelected = VoterInfoFragmentArgs.fromBundle(requireArguments()).argElection
        viewModel.election = electionSelected
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.election = electionSelected

        // DONE: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */

        // DONE: Handle loading of URLs
        viewModel.url.observe(viewLifecycleOwner) { url ->
            url?.let {
                viewModel.setUrl(null)
                loadUrl(url)
            }
        }

        // DONE: Handle save button UI state
        // DONE: cont'd Handle save button clicks
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initValues()
    }

    // DONE: Create method to load URL intents
    private fun loadUrl(url: String) {
        val implicit = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(implicit)
    }
}