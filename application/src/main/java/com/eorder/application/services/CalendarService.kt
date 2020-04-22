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

    override val currentDate: Date = Date.from(Instant.now())

    override fun getCurrentMonthNumDays(): Int {

        return LocalDate.now().lengthOfMonth()
    }

    override fun getMonthNumDays(month: Int): Int {

        return LocalDate.of(LocalDate.now().get(ChronoField.YEAR), month, 1).lengthOfMonth()
    }

    override fun getMonthCurrentDay(): Int {

        return LocalDate.now().get(ChronoField.DAY_OF_MONTH)
    }

    override fun getCurrentMonth(): Int {

        return LocalDate.now().get(ChronoField.DAY_OF_MONTH)
    }

    override fun getOrderWeek(): List<LocalDate> {

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
}