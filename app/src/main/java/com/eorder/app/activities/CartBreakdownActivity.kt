package com.eorder.app.activities

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import com.eorder.app.R
import com.eorder.app.com.eorder.app.activities.BaseActivity
import com.eorder.app.viewmodels.CartBreakdownModelView
import org.koin.androidx.viewmodel.ext.android.getViewModel
import android.content.res.Resources
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.*
import com.eorder.domain.models.Order
import kotlinx.android.synthetic.main.activity_cart_breakdown.*
import kotlin.math.roundToInt


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
            row.addView(getTableTexView(true, "${p.amount}"))
            row.addView(getTableTexView(true, "${p.totalBase}€"))
            row.addView(getTableTexView(true, "${p.tax}"))
            row.addView(getTableTexView(true, "${p.rate}%"))
            row.addView(getTableTexView(true, "${p.totalTaxes}€"))
            row.addView(getTableTexView(true, "${p.total}€"))
            table.addView(row)
        }
        lastRow(table, order)

    }

    private fun getTable(): TableLayout {

        val table = TableLayout(this)
        val layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(0, Utils.convertDpToPixel(20F), 0, 0)
        table.layoutParams = layoutParams
        return table

    }


    private fun getTableRow(): TableRow {

        val row = TableRow(this)
        val layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.bottomMargin = Utils.convertDpToPixel(20F)
        row.layoutParams = layoutParams
        return row

    }

    private fun getTableTexView(singleLine: Boolean, _text: String, black:Boolean = false): TextView {

        val text = TextView(this)
        val layoutParams = TableRow.LayoutParams(
            if (!singleLine) Utils.convertDpToPixel(100F) else TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        text.isSingleLine = singleLine
        if (black) {
            text.setTextColor(resources.getColor(R.color.primaryText))
            text.setTypeface(textView.typeface, Typeface.BOLD)
        }
        text.text = _text
        layoutParams.setMargins(Utils.convertDpToPixel(25F), 0, 0, 0)
        text.layoutParams = layoutParams
        return text

    }

    private fun lastRow(table: TableLayout, order: Order) {

        val totalRow = getTableRow()
        val totalText = getTableTexView(false,"Total")

        totalText.setTextColor(resources.getColor(R.color.primaryText))
        totalText.setTypeface(textView.typeface, Typeface.BOLD)
        totalText.textSize = 20F
        totalRow.addView(totalText)
        totalRow.addView(getTableTexView(true, ""))
        totalRow.addView(getTableTexView(true, "${order.totalProducts}",true))
        totalRow.addView(getTableTexView(true, "${order.totalBase}€", true))
        totalRow.addView(getTableTexView(true, ""))
        totalRow.addView(getTableTexView(true, ""))
        totalRow.addView(getTableTexView(true, "${order.totalTaxes}€", true))
        totalRow.addView(getTableTexView(true, "${order.total}€", true))
        table.addView(totalRow)
    }

}

class Utils {

    companion object {

        fun convertDpToPixel(dp: Float): Int {
            val metrics = Resources.getSystem().displayMetrics
            val px = dp * (metrics.densityDpi / 160f)
            return px.roundToInt()
        }
    }

}