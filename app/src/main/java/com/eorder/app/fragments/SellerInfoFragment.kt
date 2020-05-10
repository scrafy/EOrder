package com.eorder.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.eorder.app.R
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.helpers.paintEditTextUnderLines
import com.eorder.app.viewmodels.fragments.SellerInfoFragmentViewModel
import com.eorder.domain.models.Seller
import kotlinx.android.synthetic.main.fragment_seller_info.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class SellerInfoFragment : BaseFragment() {

    private lateinit var model: SellerInfoFragmentViewModel


    companion object {

        lateinit var seller: Seller
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_seller_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
    }

    private fun init() {

        val seller = seller


        editText_fragment_seller_info_seller_name.setText(seller.companyName)
        editText_fragment_seller_info_address.setText(seller.address)
        editText_fragment_seller_info_city.setText(seller.city)
        editText_fragment_seller_info_post_code.setText(seller.pc.toString())
        editText_fragment_seller_info_province.setText(seller.province)
        editText_fragment_seller_info_email.setText(seller.email)
        editText_fragment_seller_info_sector.setText(seller.sector)
        editText_fragment_seller_info_country.setText(seller.country)
        editText_fragment_seller_info_gln.setText(seller.gln.toString())
        editText_fragment_seller_info_erp.setText(seller.erp)
        editText_fragment_seller_info_seller_taxid.setText(seller.taxId)

        try {
            Glide.with(context!!).load(seller.imageUrl)
                .into(imgView_fragment_seller_seller_image)
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(imgView_fragment_seller_seller_image)
        }

        paintEditTextUnderLines(
            this.context!!,
            linearLayout_fragment_seller_info_fields_container
        )
    }
}
