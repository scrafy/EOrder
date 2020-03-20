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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.adapters.fragments.CentersAdapter
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISelectCenter
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.interfaces.IShowSnackBarMessage
import com.eorder.app.viewmodels.fragments.CatalogsViewModel
import com.eorder.app.viewmodels.fragments.CentersViewModel
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel


class CentersFragment : Fragment(), IShowSnackBarMessage, IRepaintModel, ISetAdapterListener {

    private lateinit var model: CentersViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: CentersAdapter


    private lateinit var viewModel: CatalogsViewModel

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

        view.findViewById<ImageView>(R.id.imgView_center_card_view_info)
            .setOnClickListener { view ->


            }
    }

    override fun repaintModel(view: View, model: Any?) {

        val center = (model as Center)

        view.findViewById<TextView>(R.id.textView_center_name).setText(center.center_name)
        view.findViewById<TextView>(R.id.textView_email).setText(center.email)
        view.findViewById<TextView>(R.id.textView_address).setText(center.address)


    }

    fun setObservers() {

        model.getCentersResultObservable().observe(
            (context as LifecycleOwner),
            Observer<ServerResponse<List<Center>>> { it ->

                adapter.centers = it.serverData?.data ?: mutableListOf()
                adapter.notifyDataSetChanged()
            })

        model.getErrorObservable()
            ?.observe((context as LifecycleOwner), Observer<Throwable> { ex ->

                model.manageExceptionService.manageException(this, ex)
            })
    }

    private fun init() {

        val layout = LinearLayoutManager(this.context)

        adapter = CentersAdapter(this, mutableListOf())
        recyclerView = this.view?.findViewById<RecyclerView>(R.id.recView_centers_list_fragment)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()
    }

}
