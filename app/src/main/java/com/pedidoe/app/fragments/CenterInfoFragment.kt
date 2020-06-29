package com.pedidoe.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.pedidoe.app.R
import com.pedidoe.app.helpers.LoadImageHelper
import com.pedidoe.app.helpers.paintEditTextUnderLines
import com.pedidoe.app.viewmodels.fragments.CenterInfoFragmentViewModel
import com.pedidoe.domain.enumerations.Sector
import com.pedidoe.domain.models.Center
import kotlinx.android.synthetic.main.fragment_center_info.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class CenterInfoFragment : BaseFragment() {

    private lateinit var model: CenterInfoFragmentViewModel


    companion object{
        lateinit var center:Center
    }

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

        val center = CenterInfoFragment.center

        editText_fragment_center_info_center_name.setText(center.name)
        editText_fragment_center_info_address.setText(center.address)
        editText_fragment_center_info_city.setText(center.city)
        editText_fragment_center_info_post_code.setText(center.postalCode.toString())
        editText_fragment_center_info_province.setText(center.province)
        editText_fragment_center_info_email.setText(center.email)
        if ( center.sector != Sector.NO_DEFINIDO )
            editText_fragment_center_info_sector.setText( center.sector.toString() )
        editText_fragment_center_info_country.setText(center.country)

        try {
            Glide.with(context!!).load(center.imageUrl).circleCrop()
                .into(imgView_fragment_center_center_image)
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(imgView_fragment_center_center_image)
        }

        paintEditTextUnderLines(
            this.context!!,
            linearLayout_fragment_center_info_fields_container
        )
    }

}
