package com.pedidoe.application.services

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.R
import com.pedidoe.application.interfaces.ICalendarService
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoField

@RequiresApi(Build.VERSION_CODES.O)
class CalendarService : ICalendarService {

    override val currentDate: LocalDate = LocalDate.now()

    override fun getCurrentMonthNumDays(): Int {

        return LocalDate.now().lengthOfMonth()
    }

    override fun getMonthNumDays(month: Int): Int {

        return LocalDate.of(LocalDate.now().get(ChronoField.YEAR), month, 1).lengthOfMonth()
    }

    override fun getMonthDays(month: Int): MutableList<LocalDate> {

        val days: MutableList<LocalDate> = mutableListOf()
        for (i in 1..getMonthNumDays(month)) {
            days.add(LocalDate.of(LocalDate.now().get(ChronoField.YEAR), month, i))
        }
        return days.filter { it >= currentDate } as MutableList<LocalDate>
    }

    override fun getMonthCurrentDay(): Int {

        return LocalDate.now().get(ChronoField.DAY_OF_MONTH)
    }

    override fun getCurrentMonth(): Int {

        return LocalDate.now().get(ChronoField.MONTH_OF_YEAR)
    }

    override fun getOrderWeek(month:Int): List<LocalDate> {

        var dates: MutableList<LocalDate> = mutableListOf()
        var date = LocalDate.now().plusWeeks(1)

        while (date.get(ChronoField.DAY_OF_WEEK) != DayOfWeek.MONDAY.value) {

            date = date.minusDays(1)
        }
        for (i in 1..7) {
            dates.add(date)
            date = date.plusDays(1)
        }
        return dates
    }

    override fun getDayName(context: Context, day:Int): String {

        return when(day){

            1 -> context.resources.getString(R.string.monday)
            2 -> context.resources.getString(R.string.tuesday)
            3 -> context.resources.getString(R.string.wenesday)
            4 -> context.resources.getString(R.string.thursday)
            5 -> context.resources.getString(R.string.friday)
            6 -> context.resources.getString(R.string.saturday)
            7 -> context.resources.getString(R.string.sunday)
            else -> context.resources.getString(R.string.monday)
        }
    }

    override fun getMotnhs(context:Context): Array<String> {



        return  Array(12) { i ->

            when (i) {

                0 -> context.resources.getString(R.string.enero)
                1 -> context.resources.getString(R.string.febrero)
                2 -> context.resources.getString(R.string.marzo)
                3 -> context.resources.getString(R.string.abril)
                4 -> context.resources.getString(R.string.mayo)
                5 -> context.resources.getString(R.string.junio)
                6 -> context.resources.getString(R.string.julio)
                7 -> context.resources.getString(R.string.agosto)
                8 -> context.resources.getString(R.string.septiembre)
                9 -> context.resources.getString(R.string.octubre)
                10 -> context.resources.getString(R.string.noviembre)
                11 -> context.resources.getString(R.string.diciembre)
                else -> context.resources.getString(R.string.enero)
            }
        }

    }

    override fun isDateLessOrEqualCurrentDate(date:LocalDate): Boolean{

        return date <= LocalDate.now()
    }
}