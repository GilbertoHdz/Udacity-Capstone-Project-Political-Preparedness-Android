package com.gilbertohdz.android.politicalpreparedness.election.adapter

import java.util.*

object DateStrConverters {
    @JvmStatic
    fun dateToStr(value: Date?): String {
        return value?.toString() ?: ""
    }
}