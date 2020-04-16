package com.eorder.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eorder.app.R
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.helpers.paintEditTextUnderLines
import com.eorder.app.viewmodels.fragments.CenterInfoFragmentViewModel
import com.eorder.domain.models.Center
import kotlinx.android.synthetic.main.fragment_center_info.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class CenterInfoFragment : BaseFragment() {

    private lateinit var model: CenterInfoFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_center_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
    }

    private fun init() {

        val center = arguments?.get("center") as Center

        editText_fragment_center_info_center_name.setText(center.center_name)
        editText_fragment_center_info_address.setText(center.address)
        editText_fragment_center_info_city.setText(center.city)
        editText_fragment_center_info_post_code.setText(center.pc.toString())
        editText_fragment_center_info_province.setText(center.province)
        editText_fragment_center_info_email.setText(center.email)
        editText_fragment_center_info_sector.setText(center.sector)
        editText_fragment_center_info_country.setText(center.country)

        LoadImageHelper().loadImage(
            center.imageUrl,
            imgView_fragment_center_center_image,
            true
        )

        paintEditTextUnderLines(
            this.context!!,
            linearLayout_fragment_center_info_fields_container
        )
    }

}
