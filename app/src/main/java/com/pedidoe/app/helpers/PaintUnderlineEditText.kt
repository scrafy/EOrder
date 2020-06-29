package com.pedidoe.app.helpers

import android.content.Context
import android.graphics.PorterDuff
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.children
import com.pedidoe.app.R


fun paintEditTextUnderLines(context: Context, view: ViewGroup) {

    view.children.forEach { v ->

        if (v is ViewGroup)
            paintEditTextUnderLines(context, v)

        if (v is EditText)
            v.background.setColorFilter(
                context.resources.getColor(R.color.colorPrimary, null),
                PorterDuff.Mode.SRC_IN
            )

    }
}




