package com.eorder.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.adapters.fragments.CentersAdapter
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISelectCenter
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.domain.interfaces.IShowSnackBarMessage
import com.eorder.app.viewmodels.fragments.CentersViewModel
import com.eorder.application.extensions.toBitmap
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel
import pl.droidsonroids.gif.GifDrawable
import com.eorder.app.R
import android.graphics.Bitmap
import com.eorder.app.com.eorder.app.activities.BaseFloatingButtonActivity
import com.eorder.app.com.eorder.app.fragments.BaseFloatingButtonFragment
import com.eorder.app.com.eorder.app.fragments.BaseFragment
import java.io.*
import java.lang.Exception


class CentersFragment : BaseFloatingButtonFragment(), IShowSnackBarMessage, IRepaintModel, ISetAdapterListener {

    private lateinit var model: CentersViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: CentersAdapter
    private lateinit var centers: List<Center>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.centers_fragment, container, false)

    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
        setObservers()
        model.getCenters()

    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        val center = (obj as Center)

        view.findViewById<CardView>(R.id.cardView_center_list_item)
            .setOnClickListener { view ->

                (context as ISelectCenter).selectCenter(center)
            }

    }

    override fun repaintModel(view: View, model: Any?) {

        val center = (model as Center)

        view.findViewById<TextView>(R.id.textView_center_name).text = center.center_name


        if (center.imageBase64 == null) {
            try {
                view.findViewById<ImageView>(R.id.imgView_center_list_img_center)
                    .setImageDrawable(GifDrawable(context?.resources!!, R.drawable.loading))
            } catch (ex: Exception) {

            }

        } else {
            view.findViewById<ImageView>(com.eorder.app.R.id.imgView_center_list_img_center)
                .setImageBitmap(center.imageBase64?.toBitmap())
        }

    }

    private fun bitMapToInputStream(bm: Bitmap): InputStream {

        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val bais = ByteArrayInputStream(baos.toByteArray())
        bais.reset()
        return bais
    }

    fun setObservers() {

        model.getCentersResultObservable().observe(
            (context as LifecycleOwner),
            Observer<ServerResponse<List<Center>>> { it ->

                centers = it.serverData?.data ?: mutableListOf()
                adapter.centers = centers
                adapter.notifyDataSetChanged()
                var items = centers.filter { p -> p.imageUrl != null && p.id != null }.map { p ->

                    UrlLoadedImage(p.id!!, p.imageBase64, p.imageUrl!!)
                }

                model.loadImages(items)
                    .observe((context as LifecycleOwner), Observer<List<UrlLoadedImage>> { items ->

                        items.forEach { item ->

                            this.centers.find { c -> c.id == item.id }?.imageBase64 =
                                item.imageBase64
                        }
                        adapter.notifyDataSetChanged()
                    })
            })

        model.getErrorObservable()
            ?.observe((context as LifecycleOwner), Observer<Throwable> { ex ->

                model.manageExceptionService.manageException(this.context!!, ex)
            })

        model.getLoadImageErrorObservable()
            .observe((context as LifecycleOwner), Observer<Throwable> { ex ->

                model.manageExceptionService.manageException(this.context!!, ex)
            })
    }

    private fun init() {

        val layout = LinearLayoutManager(this.context)

        adapter = CentersAdapter(this, mutableListOf())
        recyclerView = this.view?.findViewById(R.id.recView_centers_fragment)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()
    }

}
