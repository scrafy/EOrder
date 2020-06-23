package com.eorder.app.fragments

import androidx.fragment.app.Fragment
import com.eorder.app.activities.BaseActivity
import com.eorder.app.activities.BaseFloatingButtonActivity
import com.eorder.application.interfaces.ICheckValidSession

abstract class BaseFragment : Fragment() {

    override fun onStart() {
        (context as ICheckValidSession).checkValidSession()
        (context as BaseFloatingButtonActivity).showFloatingButton()
        super.onStart()
    }
}