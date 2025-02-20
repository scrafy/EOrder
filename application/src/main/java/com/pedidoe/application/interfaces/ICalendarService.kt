package com.pedidoe.application.interfaces

import android.content.Context
import org.threeten.bp.LocalDate

interface ICalendarService {

    val currentDate: LocalDate

    fun getCurrentMonthNumDays(): Int
    fun getMonthNumDays(month: Int): Int
    fun getMonthCurrentDay(): Int
    fun getCurrentMonth(): Int
    fun getOrderWeek(month:Int): List<LocalDate>
    fun getMonthDays(month: Int): MutableList<LocalDate>
    fun getDayName(context: Context, day:Int): String
    fun getMotnhs(context:Context): Array<String>
    fun isDateLessOrEqualCurrentDate(date:LocalDate): Boolean

}
