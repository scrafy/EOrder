package com.eorder.app.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.adapters.fragments.CatalogsAdapter
import com.eorder.app.com.eorder.app.activities.BaseActivity
import com.eorder.app.com.eorder.app.activities.BaseFloatingButtonActivity
import com.eorder.app.com.eorder.app.interfaces.ISelectCatalog
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.interfaces.IShowSnackBarMessage
import com.eorder.app.viewmodels.fragments.CatalogsViewModel
import com.eorder.application.extensions.toBitmap
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel
import pl.droidsonroids.gif.GifDrawable
import java.lang.Exception


class CatalogsFragment : Fragment(), IShowSnackBarMessage, IRepaintModel,
    ISetAdapterListener {


    private lateinit var model: CatalogsViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: CatalogsAdapter
    private lateinit var catalogs: List<Catalog>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.catalogs_fragment, container, false)

    }

    override fun setAdapterListeners(view: View, obj: Any?) {
        val catalog = (obj as Catalog)

        view.findViewById<CardView>(R.id.cardView_catalog_list_item).setOnClickListener { v ->

            (context as ISelectCatalog).selectCatalog(catalog.id)
        }
    }

    override fun onStart(){
        (context as BaseFloatingButtonActivity).showFloatingButton()
        super.onStart()
    }

    override fun repaintModel(view: View, model: Any?) {

        val catalog = (model as Catalog)

        view.findViewById<TextView>(R.id.textView_catalogs_list_catalog_name).text = catalog.name
        view.findViewById<TextView>(R.id.textView_catalogs_list_total_products).text = resources.getString(R.string.catalog_fragment_number_of_products).format(catalog.totalProducts)

        view.findViewById<ImageView>(R.id.imgView_catalog_list_img_product)
            .setImageDrawable(GifDrawable( context?.resources!!, R.drawable.loading ))

        if (catalog.imageBase64 == null){

            try {
                view.findViewById<ImageView>(R.id.imgView_catalog_list_img_product)
                    .setImageDrawable(GifDrawable( context?.resources!!, R.drawable.loading ))
            }
            catch(ex: Exception){

            }


        }else{
            view.findViewById<ImageView>(R.id.imgView_catalog_list_img_product)
                .setImageBitmap(catalog.imageBase64?.toBitmap())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
        setObservers()
        var sellerId = arguments?.getInt("sellerId")
        if (sellerId != null)

            model.getCatalogBySeller(sellerId ?: 0)
        else {
            //TODO show snackbar showing message error
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun setObservers() {

        model.getCatalogBySellersObservable()
            .observe((context as LifecycleOwner), Observer<ServerResponse<List<Catalog>>> { it ->

                catalogs = it.serverData?.data ?: mutableListOf()
                adapter.catalogs = catalogs
                adapter.notifyDataSetChanged()
                var items = catalogs.filter{p -> p.imageUrl != null}.map { p ->

                    UrlLoadedImage(p.id, p.imageBase64, p.imageUrl!!)
                }

                model.loadImages(items).observe((context as LifecycleOwner), Observer<List<UrlLoadedImage>> { items ->

                    items.forEach { item ->

                        this.catalogs.find { c -> c.id == item.id }?.imageBase64 = item.imageBase64
                    }
                    adapter.notifyDataSetChanged()
                })
            })

        model.getErrorObservable().observe((context as LifecycleOwner), Observer<Throwable> { ex ->

            model.manageExceptionService.manageException(this.context!!, ex)

        })

        model.getLoadImageErrorObservable().observe((context as LifecycleOwner), Observer { ex ->

            model.manageExceptionService.manageException(this.context!!, ex)
        })
    }

    private fun init() {

        val layout = GridLayoutManager(context, 2)
        adapter = CatalogsAdapter(
            this,
            listOf()
        )

        recyclerView = this.view?.findViewById(R.id.recView_catalogs_fragment)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()
    }

}
