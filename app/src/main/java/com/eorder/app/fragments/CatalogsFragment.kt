package com.eorder.app.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.adapters.fragments.CatalogsAdapter
import com.eorder.app.com.eorder.app.interfaces.ISelectCatalog
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.interfaces.IShowSnackBarMessage
import com.eorder.app.viewmodels.fragments.CatalogsViewModel
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel


class CatalogsFragment : Fragment(), IShowSnackBarMessage, IRepaintModel,
    ISetAdapterListener {


    private lateinit var model: CatalogsViewModel
    private var recyclerView : RecyclerView? = null
    private lateinit var adapter:CatalogsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.catalogs_fragment, container, false)

    }

    override fun setAdapterListeners(view: View, obj: Any?) {
        val catalog = (obj as Catalog)

        view.findViewById<CardView>(R.id.cardView_catalog_fragment_item).setOnClickListener{v ->

            (context as ISelectCatalog).selectCatalog(catalog.id)
        }
    }


    override fun repaintModel(view:View, model:Any?) {

        val catalog = (model as Catalog)
        val catalogName = view.findViewById<TextView>(R.id.textView_catalog_center_catalog_name)
        val enabled = view.findViewById<TextView>(R.id.textView_catalog_center_enabled)

        catalogName.setText(catalog.name)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
        setObservers()
        var sellerId = arguments?.getInt("sellerId")
        if ( sellerId != null)

            model.getCatalogBySeller(sellerId ?: 0)
        else {
            //TODO show snackbar showing message error
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun setObservers(){

        model.getCatalogBySellersObservable().observe((context as LifecycleOwner), Observer<ServerResponse<List<Catalog>>> { it ->

            adapter.catalogs = it.serverData?.data ?: mutableListOf()
            adapter.notifyDataSetChanged()
        })

       model.getErrorObservable().observe((context as LifecycleOwner), Observer<Throwable>{ex ->

           model.manageExceptionService.manageException(this, ex)

        })
    }

    private fun init(){

        val layout = LinearLayoutManager(this.context)
        adapter =  CatalogsAdapter(
            this,
            listOf()
        )

        recyclerView = this.view?.findViewById<RecyclerView>(R.id.recView_center_catalogs)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()
    }

}
