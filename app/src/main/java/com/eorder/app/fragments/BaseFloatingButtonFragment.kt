package com.eorder.app.fragments

import com.eorder.app.activities.BaseFloatingButtonActivity

abstract class BaseFloatingButtonFragment : BaseFragment() {

    override fun onStart() {
        (context as BaseFloatingButtonActivity).showFloatingButton()
        super.onStart()
    }
}