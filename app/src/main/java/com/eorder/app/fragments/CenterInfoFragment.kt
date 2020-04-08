package com.eorder.app.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.helpers.paintEditTextUnderLines
import com.eorder.app.viewmodels.fragments.CenterInfoFragmentViewModel
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Center
import kotlinx.android.synthetic.main.fragment_center_info.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import pl.droidsonroids.gif.GifDrawable


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

        if (center.imageBase64 == null) {

            imgView_fragment_center_center_image.setImageDrawable(
                GifDrawable(
                    context?.resources!!,
                    R.drawable.loading
                )
            )
            loadImage()

        } else {
            setImage(center.imageBase64 as String)
        }

        editText_fragment_center_info_center_name.setText(center.center_name)
        editText_fragment_center_info_address.setText(center.address)
        editText_fragment_center_info_city.setText(center.city)
        editText_fragment_center_info_post_code.setText(center.pc.toString())
        editText_fragment_center_info_province.setText(center.province)
        editText_fragment_center_info_email.setText(center.email)
        editText_fragment_center_info_sector.setText(center.sector)
        editText_fragment_center_info_country.setText(center.country)

        paintEditTextUnderLines(
            this.context!!,
            linearLayout_fragment_center_info_fields_container
        )
    }

    private fun loadImage() {

        val center = arguments?.get("center") as Center
        if (!center.imageUrl.isNullOrEmpty()) {

            val list: MutableList<UrlLoadedImage> = mutableListOf()
            list.add(UrlLoadedImage(center.id, null, center.imageUrl!!))
            model.loadImages(list)
                .observe(this.activity as LifecycleOwner, Observer<List<UrlLoadedImage>> { items ->

                    if (items[0].imageBase64 != null) {

                        setImage(items[0].imageBase64!!)
                    }


                })
        }

    }

    private fun setImage(imageBase64: String) {

        val bitmap = BitmapFactory.decodeByteArray(
            Base64.decode(imageBase64, 0),
            0,
            Base64.decode(imageBase64, 0).size
        )
        val roundedBitmapDrawable =
            RoundedBitmapDrawableFactory.create(
                context?.resources!!,
                bitmap
            )
        roundedBitmapDrawable.isCircular = true
        imgView_fragment_center_center_image.setImageDrawable(roundedBitmapDrawable)

    }


}
