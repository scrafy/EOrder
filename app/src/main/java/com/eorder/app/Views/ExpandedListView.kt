package com.eorder.app.Views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ListView


class ExpandedListView(context: Context, attrs: AttributeSet) : ListView(context, attrs) {

    private var params: android.view.ViewGroup.LayoutParams? = null
    private var old_count = 0

    override fun onDraw(canvas: Canvas) {

        if (getCount() !== old_count) {
            old_count = getCount()
            params = getLayoutParams()
            params!!.height = getCount() * if (old_count > 0) getChildAt(0).getHeight() else 0
            setLayoutParams(params)
        }

        super.onDraw(canvas)
    }

}