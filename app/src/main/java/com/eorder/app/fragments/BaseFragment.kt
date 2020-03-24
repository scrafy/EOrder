package com.eorder.app.com.eorder.app.fragments

import androidx.fragment.app.Fragment
import com.eorder.app.com.eorder.app.activities.BaseActivity
import com.eorder.app.com.eorder.app.activities.BaseFloatingButtonActivity

abstract class BaseFragment : Fragment() {

    override fun onStart() {
        (context as BaseActivity).checkToken()
        (context as BaseFloatingButtonActivity).showFloatingButton()
        super.onStart()
    }
}