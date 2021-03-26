package com.gilbertohdz.android.politicalpreparedness.network.models

data class RepresentativeResponse(
        val offices: List<Office>,
        val officials: List<Official>
)

val RepresentativeResponse.representatives
    get() = kotlin.run {
        offices.flatMap {
            it.getRepresentatives(officials)
        }
    }