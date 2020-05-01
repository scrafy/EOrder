package com.eorder.app.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView
import android.view.View


class ExpandedListView(context: Context, attrs: AttributeSet) : ListView(context, attrs) {


    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val expandSpec =
            View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
        val params = this.layoutParams
        params.height = this.measuredHeight
    }

}