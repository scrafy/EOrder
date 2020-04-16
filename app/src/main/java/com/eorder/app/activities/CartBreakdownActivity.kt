package com.eorder.app.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.eorder.app.R
import com.eorder.app.viewmodels.CartBreakdownModelView
import org.koin.androidx.viewmodel.ext.android.getViewModel
import android.graphics.Typeface
import android.os.Build
import android.view.Gravity
import android.widget.*
import androidx.annotation.RequiresApi
import com.eorder.application.extensions.convertDpToPixel
import com.eorder.domain.models.Order
import kotlinx.android.synthetic.main.activity_cart_breakdown.*

@RequiresApi(Build.VERSION_CODES.O)
class CartBreakdownActivity : BaseActivity() {

    private lateinit var model: CartBreakdownModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_breakdown)
        model = getViewModel()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        init()

    }


    override fun checkValidSession() {
        model.checkValidSession(this)
    }


    private fun init() {

        val order = model.getOrder()

        val table = findViewById<TableLayout>(R.id.tableLayout_activity_cart_breakdown_container)

        order.products.forEach { p ->

            val row = getTableRow()
            row.addView(getTableTexView(false, p.name))
            row.addView(getTableTexView(true, "${p.price}€"))
            row.addView(getTableTexView(true, "${p.amount}",false,true))
            row.addView(getTableTexView(true, "${p.totalBase}€"))
            row.addView(getTableTexView(true, "${p.tax}"))
            row.addView(getTableTexView(true, "${p.rate}%"))
            row.addView(getTableTexView(true, "${p.totalTaxes}€"))
            row.addView(getTableTexView(true, "${p.total}€"))
            table.addView(row)
        }
        lastRow(table, order)

    }

    private fun getTableRow(): TableRow {

        val row = TableRow(this)
        val layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.bottomMargin = 20F.convertDpToPixel()
        row.layoutParams = layoutParams
        return row

    }

    private fun getTableTexView(
        singleLine: Boolean,
        _text: String,
        black: Boolean = false,
        center: Boolean = false
    ): TextView {

        val text = TextView(this)
        val layoutParams = TableRow.LayoutParams(
            if (!singleLine) 100F.convertDpToPixel() else TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        text.isSingleLine = singleLine
        if (black) {
            text.setTextColor(resources.getColor(R.color.primaryText))
            text.setTypeface(textView.typeface, Typeface.BOLD)
        }
        text.text = _text
        if (center)
            text.gravity = Gravity.CENTER
        layoutParams.setMargins(18F.convertDpToPixel(), 0, 0, 0)
        text.layoutParams = layoutParams
        return text

    }

    private fun lastRow(table: TableLayout, order: Order) {

        val totalRow = getTableRow()
        val totalText =
            getTableTexView(false, resources.getString(R.string.shop_breakdown_activity_total))

        totalText.setTextColor(resources.getColor(R.color.primaryText))
        totalText.setTypeface(textView.typeface, Typeface.BOLD)
        totalText.textSize = 20F
        totalRow.addView(totalText)
        totalRow.addView(getTableTexView(true, ""))
        totalRow.addView(getTableTexView(true, "${order.totalProducts}", true,true))
        totalRow.addView(getTableTexView(true, "${order.totalBase}€", true))
        totalRow.addView(getTableTexView(true, ""))
        totalRow.addView(getTableTexView(true, ""))
        totalRow.addView(getTableTexView(true, "${order.totalTaxes}€", true))
        totalRow.addView(getTableTexView(true, "${order.total}€", true))
        table.addView(totalRow)
    }

}