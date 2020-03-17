package com.eorder.app.fragments


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.activities.CenterActivity
import com.eorder.app.adapters.fragments.CatalogsByCenterAdapter
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.interfaces.IShowSnackBarMessage
import com.eorder.app.viewmodels.fragments.CatalogsByCenterViewModel
import com.eorder.infrastructure.models.Catalog
import com.eorder.infrastructure.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel


class CatalogsByCenterFragment : Fragment(), IShowSnackBarMessage, IRepaintModel,
    ISetAdapterListener {


    private lateinit var model: CatalogsByCenterViewModel
    private var recyclerView : RecyclerView? = null
    private lateinit var adapter:CatalogsByCenterAdapter
    private lateinit var viewModel: CatalogsByCenterViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.catalogs_by_center_fragment, container, false)

    }

    override fun setAdapterListeners(view: View, obj: Any?) {
        val catalog = (obj as Catalog)

        view.findViewById<ImageView>(R.id.imgView_catalog_center_products).setOnClickListener{v ->

            if (catalog != null) {

                var args = Bundle()
                args.putInt("catalogId", catalog.id)
                var fragment = ProductsFragment()
                fragment.arguments = args
                this.activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.linear_layout_center_fragment_container, fragment)
                    ?.addToBackStack(null)?.commit()

            } else {
                //TODO show snack bar informando de que el catalogo id es invalido
            }
        }
    }


    override fun repaintModel(view:View, model:Any?) {

        val catalog = (model as Catalog)
        val catalogName = view.findViewById<TextView>(R.id.textView_catalog_center_catalog_name)
        val enabled = view.findViewById<TextView>(R.id.textView_catalog_center_enabled)

        catalogName.setText(catalog.name)

        if (catalog.enabled) {
            enabled?.setText(R.string.enabled)
            enabled?.setTextColor(Color.GREEN)
        }else{
            enabled?.setText(R.string.disabled)
            enabled?.setTextColor(Color.RED)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
        setObservers()
        var centerId = arguments?.getInt("centerId")
        if ( centerId != null)

            model.getCatalogByCentre(centerId ?: 0)
        else {
            //TODO show snackbar showing message error
        }
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setObservers(){

        model.getCatalogByCentreObservable().observe((this.activity as CenterActivity), Observer<ServerResponse<List<Catalog>>> { it ->

            adapter.catalogs = it.serverData?.data ?: mutableListOf()
            adapter.notifyDataSetChanged()
        })

       model.getErrorObservable().observe((this.activity as CenterActivity), Observer<Throwable>{ex ->

           model.manageExceptionService.manageException(this, ex)

        })
    }

    private fun init(){

        val layout = LinearLayoutManager(this.context)
        adapter =  CatalogsByCenterAdapter(
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
