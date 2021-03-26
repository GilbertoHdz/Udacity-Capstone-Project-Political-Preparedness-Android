package com.gilbertohdz.android.politicalpreparedness.representative.model

import com.gilbertohdz.android.politicalpreparedness.network.models.Office
import com.gilbertohdz.android.politicalpreparedness.network.models.Official

data class Representative (
        val official: Official,
        val office: Office
)