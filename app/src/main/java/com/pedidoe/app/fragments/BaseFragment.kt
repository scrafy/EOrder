package com.pedidoe.app.fragments

import androidx.fragment.app.Fragment
import com.pedidoe.app.activities.BaseFloatingButtonActivity
import com.pedidoe.application.interfaces.ICheckValidSession

abstract class BaseFragment : Fragment() {

    override fun onStart() {
        (context as ICheckValidSession).checkValidSession()
        (context as BaseFloatingButtonActivity).showFloatingButton()
        super.onStart()
    }
}