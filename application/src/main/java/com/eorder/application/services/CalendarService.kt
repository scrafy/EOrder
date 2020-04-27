package com.eorder.application.services

import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.ICalendarService
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoField
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class CalendarService : ICalendarService {

    override val currentDate: LocalDate = LocalDate.now()

    override fun getCurrentMonthNumDays(): Int {

        return LocalDate.now().lengthOfMonth()
    }

    override fun getMonthNumDays(month: Int): Int {

        return LocalDate.of(LocalDate.now().get(ChronoField.YEAR), month, 1).lengthOfMonth()
    }

    override fun getMonthDays(month: Int): List<LocalDate> {

        val days: MutableList<LocalDate> = mutableListOf()
        for (i in 1..getMonthNumDays(month)) {
            days.add(LocalDate.of(LocalDate.now().get(ChronoField.YEAR), month, i))
        }
        return days//.filter { it >= currentDate }
    }

    override fun getMonthCurrentDay(): Int {

        return LocalDate.now().get(ChronoField.DAY_OF_MONTH)
    }

    override fun getCurrentMonth(): Int {

        return LocalDate.now().get(ChronoField.MONTH_OF_YEAR)
    }

    override fun getOrderWeek(month:Int): List<LocalDate> {

        var dates: MutableList<LocalDate> = mutableListOf()
        var date = LocalDate.of(2020,4,1).plusWeeks(1)

        while (date.get(ChronoField.DAY_OF_WEEK) != DayOfWeek.MONDAY.value) {

            date = date.minusDays(1)
        }
        for (i in 1..7) {
            dates.add(date)
            date = date.plusDays(1)
        }
        return dates//.filter { it.get(ChronoField.MONTH_OF_YEAR) == month }
    }

    override fun getDayName(day:Int): String {

        return when(day){

            1 -> "Monday"
            2 -> "Tuesday"
            3 -> "Wenesday"
            4 -> "Thursday"
            5 -> "Friday"
            6 -> "Saturday"
            7 -> "Sunday"
            else -> "Monday"
        }
    }

    override fun getMotnhs(): Array<String> {



        return  Array(12) { i ->

            when (i) {

                0 -> "January"
                1 -> "February"
                2 -> "March"
                3 -> "April"
                4 -> "May"
                5 -> "June"
                6 -> "July"
                7 -> "August"
                8 -> "September"
                9 -> "October"
                10 -> "November"
                11 -> "December"
                else -> "January"
            }
        }

    }

    override fun isDateLessOrEqualCurrentDate(date:LocalDate): Boolean{

        return date <= LocalDate.now()
    }
}