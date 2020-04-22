package com.eorder.application.interfaces

import java.time.LocalDate
import java.util.*

interface ICalendarService {

    val currentDate: Date

    fun getCurrentMonthNumDays(): Int
    fun getMonthNumDays(month: Int): Int
    fun getMonthCurrentDay(): Int
    fun getCurrentMonth(): Int
    fun getOrderWeek(): List<LocalDate>

}
