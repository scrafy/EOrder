package com.eorder.app.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.adapters.fragments.ProductCalendarAdapter
import com.eorder.app.helpers.GridLayoutItemDecoration
import com.eorder.app.helpers.SpinnerExtension
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.viewmodels.ProductCalendarActivityViewModel
import com.eorder.app.widgets.AlertDialogInput
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.AlertDialogQuestion
import com.eorder.application.interfaces.ICalendarService
import com.eorder.application.interfaces.ICheckValidSession
import com.eorder.domain.models.Product
import com.eorder.domain.models.ProductAmountByDay
import kotlinx.android.synthetic.main.activity_product_calendar.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.time.LocalDate
import java.time.temporal.ChronoField


@RequiresApi(Build.VERSION_CODES.O)
class ProductCalendarActivity : BaseActivity(), IRepaintModel, ISetAdapterListener,
    ICheckValidSession {

    private lateinit var model: ProductCalendarActivityViewModel
    private lateinit var spinnerMonths: SpinnerExtension
    private lateinit var product: Product
    private lateinit var recycle: RecyclerView
    private lateinit var productCalendarAdapter: ProductCalendarAdapter
    private lateinit var calendarService: ICalendarService
    private var productCalendarDays: MutableList<ProductAmountByDay> = mutableListOf()
    private lateinit var orderDays: List<LocalDate>
    private var daysWithAmount: MutableList<ProductAmountByDay> = mutableListOf()


    companion object {

        var product: Product? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_calendar)
        model = getViewModel()
        calendarService = this.model.getServiceCalendar()
        setSpinnerMonths()
        setListeners()
        init()


    }

    override fun onStart() {
        super.onStart()
        this.setSupportActionBar(toolbar as Toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowHomeEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        this.onBackPressed()
        return true
    }

    override fun checkValidSession() {
        model.checkValidSession(this)
    }

    override fun repaintModel(view: View, model: Any?) {

        var day = model as ProductAmountByDay


        view.findViewById<TextView>(R.id.textView_fragment_product_calendar_day).text =
            day.day.dayOfMonth.toString()


        view.findViewById<TextView>(R.id.textView_fragment_product_calendar_day_name).text =
            calendarService.getDayName(this, day.day.get(ChronoField.DAY_OF_WEEK))

        if (day.day in orderDays) {

            view.findViewById<LinearLayout>(R.id.linearLayout_calendar_product_day_container)
                .background = resources.getDrawable(R.drawable.shape_blue_gradient_background)

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

    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        val day = obj as ProductAmountByDay
        var dialogInput: AlertDialogInput? = null

        if (!calendarService.isDateLessOrEqualCurrentDate(day.day)) {

            view.setOnClickListener {

                dialogInput = AlertDialogInput(
                    this,
                    "",
                    "",
                    resources.getString(R.string.add),
                    resources.getString(R.string.close),
                    { d, i ->

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

                        Toast.makeText(
                            this,
                            String.format(
                                resources.getString(R.string.product_calendar_activity_number_units),
                                day.amount
                            ),
                            Toast.LENGTH_LONG
                        ).show()

                    },
                    { d, i ->

                        d.dismiss()
                    })

                (dialogInput as AlertDialogInput).show()

            }

        }
    }

    private fun init() {

        orderDays = calendarService.getOrderWeek(LocalDate.now().get(ChronoField.MONTH_OF_YEAR))
        product = ProductCalendarActivity.product!!
        initializeAmountWithDays()
        recycle = recycleView_activity_product_calendar_container
        val layout = GridLayoutManager(this, 4)
        layout.orientation = LinearLayoutManager.VERTICAL
        textView_activity_product_calendar_product_name.text = product.name
        if (product.amountsByDay.isNullOrEmpty())
            textView_activity_product_calendar_total_units.text = String.format(
                resources.getString(R.string.product_calendar_activity_units),
                (product.amount)
            )
        else
            textView_activity_product_calendar_total_units.text = String.format(
                resources.getString(R.string.product_calendar_activity_units),
                product.amount
            )

        productCalendarAdapter =
            ProductCalendarAdapter(
                productCalendarDays

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

        if (orderDays.any { x -> x.monthValue == LocalDate.now().get(ChronoField.MONTH_OF_YEAR) + 1 }) {

            spinnerMonths.setSelection(LocalDate.now().get(ChronoField.MONTH_OF_YEAR))

        } else {

            spinnerMonths.setSelection(LocalDate.now().get(ChronoField.MONTH_OF_YEAR) - 1)
        }
    }

    private fun setDayAmount(day: ProductAmountByDay) {

        if (daysWithAmount.isNullOrEmpty() && day.amount == 0 && product.amountsByDay.isNullOrEmpty()) return

        if (daysWithAmount.firstOrNull { it.day == day.day } == null) {

            daysWithAmount.add(day)
        } else {
            (daysWithAmount.find { it.day == day.day } as ProductAmountByDay).amount = day.amount
        }

    }

    private fun initializeAmountWithDays() {

        if (product.amountsByDay.isNullOrEmpty()) {
            if (product.amount > 0)
                orderDays.forEach { daysWithAmount.add(ProductAmountByDay(it, product.amount)) }

            product.amountsByDay = mutableListOf()

        }
    }

    private fun confirmAmountsByDays() {


        if (daysWithAmount.isNullOrEmpty()) {
            AlertDialogOk(
                this,
                resources.getString(R.string.product_calendar_activity_dialog_confirm_calendar_title),
                resources.getString(R.string.product_calendar_activity_dialog_confirm_calendar_message_no_days),
                resources.getString(R.string.ok)
            ) { d, i -> }.show()
            return
        }

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

        product.amount = product.amountsByDay?.sumBy { it.amount }!!

        if (product.amount == 0) {

            daysWithAmount = mutableListOf()
            model.removeProductFromShop(product)
            textView_activity_product_calendar_total_units.text =
                String.format(resources.getString(R.string.product_calendar_activity_units), 0)

        } else {

            textView_activity_product_calendar_total_units.text =

                String.format(
                    resources.getString(R.string.product_calendar_activity_units),
                    product.amountsByDay?.sumBy { it.amount }!!
                )


            model.addProductToShop(product)
        }

        this.onBackPressed()

    }


    private fun setMonthDays(month: Int) {

        var productAmountByDay: ProductAmountByDay? = null
        val days = mutableListOf<LocalDate>()

        if (orderDays.any { x ->
                x.monthValue == LocalDate.now().get(ChronoField.MONTH_OF_YEAR) + 1 && month == LocalDate.now().get(
                    ChronoField.MONTH_OF_YEAR
                ) + 1
            })

            days.addAll(orderDays.filter { x -> x.monthValue == LocalDate.now().get(ChronoField.MONTH_OF_YEAR) })

        days.addAll(calendarService.getMonthDays(month))
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
            days.forEach { d ->

                productAmountByDay = daysWithAmount?.firstOrNull { it.day == d }

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
        }
        productCalendarAdapter = ProductCalendarAdapter(productCalendarDays)
        recycle.adapter = productCalendarAdapter
        productCalendarAdapter.notifyDataSetChanged()

    }

    private fun setSpinnerMonths() {

        spinnerMonths = spinner_activity_product_calendar_months
        val monthsAdapter = ArrayAdapter<String>(
            this,
            R.layout.simple_spinner_item_blue,
            calendarService.getMotnhs(this)
        )
        monthsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_text_color)
        spinnerMonths.adapter = monthsAdapter

    }

    private fun cleanCalendar() {

        if (product.amountsByDay.isNullOrEmpty()) {

            AlertDialogOk(
                this,
                resources.getString(R.string.product_calendar_activity_dialog_clean_title),
                resources.getString(R.string.product_calendar_activity_dialog_clean_message),
                resources.getString(R.string.ok)
            ) { d, i -> }.show()

        } else {

            AlertDialogQuestion(
                this,
                resources.getString(R.string.product_calendar_activity_dialog_clean_title),
                resources.getString(R.string.product_calendar_activity_dialog_clean_calendar_message),
                resources.getString(R.string.yes),
                resources.getString(R.string.no),
                { d, i ->

                    product.amount = 0
                    product.amountsByDay = mutableListOf()
                    daysWithAmount = mutableListOf()
                    textView_activity_product_calendar_total_units.text = String.format(
                        resources.getString(R.string.product_calendar_activity_units),
                        0
                    )
                    spinnerMonths.setSelection(LocalDate.now().get(ChronoField.MONTH_OF_YEAR) - 1)
                    this.model.removeProductFromShop(product)
                },
                { d, i ->

                }).show()


        }

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

        toolbar.setOnClickListener {

            this.onBackPressed()
        }

        button_activity_product_calendar_confirm.setOnClickListener {

            confirmAmountsByDays()
        }

        button_activity_product_calendar_clean.setOnClickListener {

            cleanCalendar()
        }
    }
}
