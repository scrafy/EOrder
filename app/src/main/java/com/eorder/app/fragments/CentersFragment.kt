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
import com.eorder.app.com.eorder.app.adapters.fragments.CentersAdapter
import com.eorder.app.com.eorder.app.interfaces.IShowSnackBarMessage
import com.eorder.app.com.eorder.app.viewmodels.fragments.CatalogsByCenterViewModel
import com.eorder.app.com.eorder.app.viewmodels.fragments.CentersViewModel
import com.eorder.infrastructure.models.Center
import com.eorder.infrastructure.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel


class CentersFragment : Fragment(), IShowSnackBarMessage {

    var model: CentersViewModel? = null
    var recyclerView : RecyclerView? = null
    var adapter = CentersAdapter(mutableListOf())

    companion object {
        fun newInstance() = CentersFragment()
    }

    private lateinit var viewModel: CatalogsByCenterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.centers_fragment, container, false)

    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
        setObservers()
        model?.getCenters()

    }

    fun setObservers(){

        model?.getCentersResultObservable()?.observe((this.activity as CenterActivity), Observer<ServerResponse<List<Center>>> { it ->

            adapter.centers = it.serverData?.data ?: mutableListOf()
            adapter.notifyDataSetChanged()
        })

        model?.getErrorObservable()?.observe((this.activity as CenterActivity), Observer<Throwable>{ ex ->

            model?.manageExceptionService?.manageException(this, ex)
        })
    }

    private fun init(){

        var layout = LinearLayoutManager(this.context)

        recyclerView = this.view?.findViewById<RecyclerView>(R.id.recView_centers_list_fragment)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()
    }

}
