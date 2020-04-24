package com.eorder.app.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.activities.BaseFloatingButtonActivity
import com.eorder.app.adapters.fragments.ProductCalendarAdapter
import com.eorder.app.com.eorder.app.viewmodels.fragments.ProductCalendarFragmentViewModel
import com.eorder.app.helpers.GridLayoutItemDecoration
import com.eorder.app.helpers.SpinnerExtension
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetActionBar
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.widgets.AlertDialogInput
import com.eorder.domain.models.Product
import com.eorder.domain.models.ProductAmountByDay
import kotlinx.android.synthetic.main.fragment_product_calendar.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.time.LocalDate
import java.time.temporal.ChronoField


@RequiresApi(Build.VERSION_CODES.O)
class ProductCalendarFragment : BaseFragment(), IRepaintModel, ISetAdapterListener {

    private lateinit var model: ProductCalendarFragmentViewModel
    private lateinit var spinnerMonths: SpinnerExtension
    private lateinit var product: Product
    private lateinit var recycle: RecyclerView
    private lateinit var productCalendarAdapter: ProductCalendarAdapter
    private var productCalendarDays: MutableList<ProductAmountByDay> = mutableListOf()
    private lateinit var orderDays: List<LocalDate>
    private var daysWithAmount: MutableList<ProductAmountByDay> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_product_calendar, container, false)
    }

    override fun onStart() {
        super.onStart()
        (context as BaseFloatingButtonActivity).hideFloatingButton()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        setSpinnerMonths()
        init()
        setListeners()


    }

    override fun repaintModel(view: View, model: Any?) {

        var day = model as ProductAmountByDay


        view.findViewById<TextView>(R.id.textView_fragment_product_calendar_day).text =
            day.day.dayOfMonth.toString()


        view.findViewById<TextView>(R.id.textView_fragment_product_calendar_day_name).text =
            this.model.getServiceCalendar().getDayName(day.day.get(ChronoField.DAY_OF_WEEK))

        if (day.day in orderDays) {

            view.findViewById<LinearLayout>(R.id.linearLayout_calendar_product_day_container)
                .background = resources.getDrawable(R.drawable.shape_drawerlayout_background)

            view.findViewById<TextView>(R.id.textView_fragment_product_calendar_day).setTextColor(
                Color.WHITE
            )

            view.findViewById<TextView>(R.id.textView_fragment_product_calendar_units).setTextColor(
                Color.WHITE
            )

            view.findViewById<TextView>(R.id.textView_fragment_product_calendar_day_name)
                .setTextColor(Color.WHITE)

        }

        if (day.day == LocalDate.now()) {

            view.findViewById<LinearLayout>(R.id.linearLayout_calendar_product_day_container)
                .setBackgroundColor(
                    resources.getColor(R.color.colorAccent)

                )
            view.findViewById<TextView>(R.id.textView_fragment_product_calendar_day).setTextColor(
                Color.WHITE
            )
            view.findViewById<TextView>(R.id.textView_fragment_product_calendar_day_name)
                .setTextColor(Color.WHITE)
        }

        if (day.amount > 0)
            view.findViewById<TextView>(R.id.textView_fragment_product_calendar_units).text =
                day.amount.toString()

        /*if (day.day in daysWithAmount.map { it.day }) {

            view.findViewById<TextView>(R.id.textView_fragment_product_calendar_units).text =
                daysWithAmount.first { d -> d.day == day.day }.amount.toString()
        }*/

    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        val day = obj as ProductAmountByDay
        var dialogInput: AlertDialogInput? = null

        if (!model.getServiceCalendar().isDateLessOrEqualCurrentDate(day.day)) {

            view.setOnClickListener {

                dialogInput = AlertDialogInput(context!!, "", "", "ADD", "CLOSE", { d, i ->

                    if (dialogInput?.input?.text.isNullOrEmpty()) {
                        day.amount = 0
                    } else {
                        day.amount = Integer(dialogInput?.input?.text.toString()).toInt()
                    }
                    setDayAmount(day)
                    if (day.amount != 0)
                        view.findViewById<TextView>(R.id.textView_fragment_product_calendar_units)
                            .text = day.amount.toString()
                    else
                        view.findViewById<TextView>(R.id.textView_fragment_product_calendar_units)
                            .text = ""

                }, { d, i ->

                    d.dismiss()
                })

                (dialogInput as AlertDialogInput).show()

            }

        }
    }

    private fun init() {

        var menu = mutableMapOf<String, Int>()
        recycle = recycleView_fragment_product_calendar_container
        menu["empty_menu"] = R.menu.empty_menu
        (context as ISetActionBar)?.setActionBar(menu, true, false)
        val layout = GridLayoutManager(context, 4)
        layout.orientation = LinearLayoutManager.VERTICAL
        product = arguments?.getSerializable("product") as Product
        textView_fragment_product_calendar_product_name.text = product.name
        if (product.amountsByDay.isNullOrEmpty())
            textView_fragment_product_calendar_total_units.text = (product.amount * 7).toString()
        else
            textView_fragment_product_calendar_total_units.text = product.amount.toString()
        productCalendarAdapter =
            ProductCalendarAdapter(
                productCalendarDays,
                this
            )
        recycle.adapter = productCalendarAdapter
        recycle.layoutManager = layout
        recycle.itemAnimator = DefaultItemAnimator()
        recycle.addItemDecoration(
            GridLayoutItemDecoration(
                4,
                20,
                true
            )
        )

        spinnerMonths.setSelection(LocalDate.now().get(ChronoField.MONTH_OF_YEAR) - 1)

    }

    private fun setDayAmount(day: ProductAmountByDay) {

        /* if (daysWithAmount.isEmpty()) {

             productCalendarDays.forEach {

                 if (it.amount > 0)
                     daysWithAmount.add(it)
             }
             //orderDays.forEach { daysWithAmount.add(ProductAmountByDay(it, product.amount)) }
         }*/
        if (daysWithAmount.firstOrNull { it.day == day.day } == null) {

            daysWithAmount.add(day)
        } else {
            (daysWithAmount.find { it.day == day.day } as ProductAmountByDay).amount = day.amount
        }
    }

    private fun confirmAmountsByDays() {

        //is the first time we enter in the calendar view for this product?
        if (product.amountsByDay.isNullOrEmpty()) {

            if (daysWithAmount.isEmpty()) {

                orderDays.forEach { daysWithAmount.add(ProductAmountByDay(it, product.amount)) }
            }
            model.setAmountOfProductByDay(product.id, daysWithAmount)

        } else {
            if (daysWithAmount.isNotEmpty()) {

                daysWithAmount.forEach {

                    if ((product.amountsByDay as MutableList<ProductAmountByDay>).firstOrNull { p -> p.day == it.day } != null) {

                        (product.amountsByDay as MutableList<ProductAmountByDay>).find { p -> p.day == it.day }
                            ?.amount = it.amount
                    } else {
                        (product.amountsByDay as MutableList<ProductAmountByDay>).add(it)
                    }
                }
            }
        }

        product.amount = product.amountsByDay?.sumBy { it.amount }!!


        textView_fragment_product_calendar_total_units.text = product.amountsByDay?.sumBy { it.amount }!!.toString()

    }


    private fun setMonthDays(month: Int) {

        var productAmountByDay: ProductAmountByDay? = null
        val days = model.getServiceCalendar().getMonthDays(month)
        orderDays = this.model.getServiceCalendar().getOrderWeek(month)
        productCalendarDays.clear()
        if (!this.product.amountsByDay.isNullOrEmpty()) {


            days.forEach { d ->
                productAmountByDay = this.product.amountsByDay?.firstOrNull { it.day == d }

                if (productAmountByDay != null)
                    productCalendarDays.add(
                        ProductAmountByDay(
                            d,
                            (productAmountByDay as ProductAmountByDay).amount
                        )
                    )
                else
                    productCalendarDays.add(ProductAmountByDay(d, 0))

            }

        } else {
            days.forEach {

                if (it in orderDays)
                    productCalendarDays.add(ProductAmountByDay(it, product.amount))
                else
                    productCalendarDays.add(ProductAmountByDay(it, 0))
            }
        }
        productCalendarAdapter = ProductCalendarAdapter(productCalendarDays, this)
        recycle.adapter = productCalendarAdapter
        productCalendarAdapter.notifyDataSetChanged()

    }

    private fun setSpinnerMonths() {

        spinnerMonths = spinner_fragment_product_calendar_months
        val monthsAdapter = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_item,
            model.getServiceCalendar().getMotnhs()
        )

        spinnerMonths.adapter = monthsAdapter

    }


    private fun setListeners() {

        spinnerMonths.onItemSelectedListener =
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
                    setMonthDays(position + 1)
                }

            }

        button_fragment_product_calendar_next_month.setOnClickListener {

            confirmAmountsByDays()
        }
    }
}
