package com.gilbertohdz.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gilbertohdz.android.politicalpreparedness.databinding.ElectionItemBinding
import com.gilbertohdz.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(
        private val clickListener: ElectionListener
): ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }
}

// DONE: Create ElectionViewHolder
class ElectionViewHolder(val binding: ElectionItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(electionListener: ElectionListener, election: Election) {
        binding.election = election
        binding.electionListener = electionListener

        // performance boost
        binding.executePendingBindings()
    }

    companion object {
        // DONE: Add companion object to inflate ViewHolder (from)
        fun from(parent: ViewGroup): ElectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ElectionItemBinding.inflate(layoutInflater, parent, false)
            return ElectionViewHolder(binding)
        }
    }
}

// DONE: Create ElectionDiffCallback
class ElectionDiffCallback() : DiffUtil.ItemCallback<Election>() {

    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }
}

// DONE: Create ElectionListener
class ElectionListener(val block: (Election) -> Unit) {
    fun onClick(election: Election) = block(election)
}