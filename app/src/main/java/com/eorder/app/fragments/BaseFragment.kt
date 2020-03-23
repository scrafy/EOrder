package com.eorder.app.com.eorder.app.fragments

import androidx.fragment.app.Fragment
import com.eorder.app.com.eorder.app.activities.BaseActivity

abstract class BaseFragment : Fragment() {


    protected fun checkToken(){
        (context as BaseActivity).checkToken()
    }
}