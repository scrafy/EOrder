package com.eorder.app.fragments

import androidx.fragment.app.Fragment
import com.eorder.app.activities.BaseActivity
import com.eorder.app.activities.BaseFloatingButtonActivity

abstract class BaseFragment : Fragment() {

    override fun onStart() {
        (context as BaseActivity).checkValidSession()
        (context as BaseFloatingButtonActivity).showFloatingButton()
        super.onStart()
    }
}