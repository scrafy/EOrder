package com.eorder.app.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.activities.CenterActivity
import com.eorder.app.adapters.fragments.CatalogsByCenterAdapter
import com.eorder.app.com.eorder.app.interfaces.IShowSnackBarMessage
import com.eorder.app.com.eorder.app.viewmodels.fragments.CatalogsByCenterViewModel
import com.eorder.infrastructure.models.Catalog
import com.eorder.infrastructure.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel


class CatalogsByCenterFragment : Fragment(), IShowSnackBarMessage {


    var model: CatalogsByCenterViewModel? = null
    var recyclerView : RecyclerView ? = null
    var adapter =
        CatalogsByCenterAdapter(
            mutableListOf()
        )


    private lateinit var viewModel: CatalogsByCenterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.catalogs_by_center_fragment, container, false)

    }

    override fun showMessage(message: String) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
        setObservers()
        var centerId = arguments?.getInt("centerId")
        if ( centerId != null)

            model?.getCatalogByCentre(centerId ?: 0)
        else {
            //TODO show snackbar showing message error
        }
    }

    fun setObservers(){

        model?.getCatalogByCentreObservable()?.observe((this.activity as CenterActivity), Observer<ServerResponse<List<Catalog>>> { it ->

            adapter.catalogs = it.serverData?.data ?: mutableListOf()
            adapter.notifyDataSetChanged()
        })

       model?.getErrorObservable()?.observe((this.activity as CenterActivity), Observer<Throwable>{ex ->

           model?.manageExceptionService?.manageException(this, ex)

        })
    }

    private fun init(){

        var layout = LinearLayoutManager(this.context)

        recyclerView = this.view?.findViewById<RecyclerView>(R.id.recView_center_catalogs)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()
    }

}
