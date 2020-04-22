package com.eorder.app.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import com.eorder.app.R
import com.eorder.app.activities.BaseFloatingButtonActivity
import com.eorder.app.com.eorder.app.viewmodels.fragments.ProductCalendarFragmentViewModel
import com.eorder.app.helpers.SpinnerExtension
import com.eorder.domain.models.Product
import kotlinx.android.synthetic.main.fragment_product_calendar.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class ProductCalendarFragment : BaseFragment() {

    private lateinit var model: ProductCalendarFragmentViewModel
    private lateinit var months: Array<String>
    private lateinit var spinnerMonth: SpinnerExtension
    private lateinit var viewDays: MutableMap<Int, CardView>
    private lateinit var product: Product


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_product_calendar, container, false)
    }

    override fun onStart() {
        super.onStart()
        (context as BaseFloatingButtonActivity).hideFloatingButton()
        model = getViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        init()
        setSpinnerMonths()
        setMonthDaysGridLayout(null)

    }


    private fun init() {
        product = arguments?.getSerializable("product") as Product
        gridLayout_fragment_product_calendar_container.columnCount = 3
        textView_fragment_product_calendar_product_name.text = product.name
        textView_fragment_product_calendar_total_units.text = product.amount.toString()
    }


    private fun setMonthDaysGridLayout(month: Int?) {

        viewDays = mutableMapOf()
        val calendarService = model.getServiceCalendar()

        for (i in 1 until getMonthNumDays(month)) {

            val viewDay = LayoutInflater.from(context).inflate(
                R.layout.calendar_product_day,
                null
            ) as CardView
            viewDay.findViewById<TextView>(R.id.textView_fragment_product_calendar_day).text =
                i.toString()
            //viewDay.findViewById<TextView>(R.id.textView_fragment_product_calendar_units).text = "500"
            gridLayout_fragment_product_calendar_container.addView(viewDay)
            setViewDayOnClickListener(viewDay)
            viewDays[i] = viewDay

            if (i == calendarService.getMonthCurrentDay())
                setCurrentDay(viewDay)

        }

    }

    private fun setOrderDays() {

        val days = model.getServiceCalendar().getOrderWeek()

        viewDays.filter { i ->

            i.value.findViewById<TextView>(R.id.textView_fragment_product_calendar_day).text as Int in days.map { i -> i.dayOfMonth }
        }.forEach { i ->

            i.value.background = resources.getDrawable(R.drawable.shape_drawerlayout_background)
        }

    }

    private fun setCurrentDay(view: CardView) {

        view.setCardBackgroundColor(resources.getColor(R.color.colorAccent, null))
    }

    private fun getMonthNumDays(month: Int?): Int {

        val calendarService = model.getServiceCalendar()

        return if (month != null)
            calendarService.getMonthNumDays(month)
        else
            calendarService.getCurrentMonthNumDays()
    }

    private fun setViewDayOnClickListener(view: CardView) {

        view.setOnClickListener {

        }
    }

    private fun setSpinnerMonths() {

        val monthsAdapter = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_item,
            setMotnhs()
        )
        spinnerMonth.adapter = monthsAdapter

        spinnerMonth.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    //TODO
                }

            }
    }

    private fun setMotnhs(): Array<String> {

        return Array(12) { i ->

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
}
