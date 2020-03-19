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
import com.eorder.app.adapters.fragments.SellerAdapter
import com.eorder.app.com.eorder.app.interfaces.ISelectSeller
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.interfaces.IShowSnackBarMessage
import com.eorder.app.viewmodels.fragments.CatalogsViewModel
import com.eorder.app.viewmodels.fragments.SellersViewModel
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SellersFragment : Fragment(), IShowSnackBarMessage, IRepaintModel, ISetAdapterListener {

    private lateinit var model: SellersViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: SellerAdapter


    private lateinit var viewModel: CatalogsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.sellers_fragment, container, false)

    }

    override fun showMessage(message: String) {
        Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
        setObservers()
        var centerId = arguments?.getInt("centerId")
        if ( centerId != null)

            model.getSellersByCenter(centerId)
        else {
            //TODO show snackbar showing message error
        }

    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        val seller = (obj as Seller)

        view.findViewById<CardView>(R.id.cardView_sellers_list_item)
            .setOnClickListener { view ->

                (this.activity as ISelectSeller).selectSeller(seller.id)
            }


    }

    override fun repaintModel(view: View, model: Any?) {

        val seller = (model as Seller)
        view.findViewById<TextView>(R.id.textView_sellers_list_name).setText(seller.companyName)
    }

    fun setObservers() {

        model.getSellersByCenterResultObservable().observe(
            (this.activity as LifecycleOwner),
            Observer<ServerResponse<List<Seller>>> { it ->

                adapter.sellers = it.serverData?.data ?: mutableListOf()
                adapter.notifyDataSetChanged()
            })

        model.getErrorObservable()
            ?.observe((this.activity as LifecycleOwner), Observer<Throwable> { ex ->

                model.manageExceptionService.manageException(this, ex)
            })
    }

    private fun init() {

        val layout = LinearLayoutManager(this.context)

        adapter = SellerAdapter(this, mutableListOf())
        recyclerView = this.view?.findViewById<RecyclerView>(R.id.recView_sellers_list_fragment)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()
    }

}
