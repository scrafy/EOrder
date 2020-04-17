package com.eorder.app.Views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ListView
import android.view.ViewGroup
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View


class ExpandedListView(context: Context, attrs: AttributeSet) : ListView(context, attrs) {

    private var params: android.view.ViewGroup.LayoutParams? = null
    private var old_count = 0

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val expandSpec =
            View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
        val params = this.layoutParams
        params.height = this.measuredHeight
    }

}