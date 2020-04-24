package com.eorder.application.interfaces

import java.time.LocalDate
import java.util.*

interface ICalendarService {

    val currentDate: LocalDate

    fun getCurrentMonthNumDays(): Int
    fun getMonthNumDays(month: Int): Int
    fun getMonthCurrentDay(): Int
    fun getCurrentMonth(): Int
    fun getOrderWeek(month:Int): List<LocalDate>
    fun getMonthDays(month: Int): List<LocalDate>
    fun getDayName(day:Int): String
    fun getMotnhs(): Array<String>
    fun isDateLessOrEqualCurrentDate(date:LocalDate): Boolean

}
