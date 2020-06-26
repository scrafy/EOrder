package com.eorder.app.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.eorder.app.R
import com.eorder.app.activities.SellerProductActivity
import com.eorder.app.adapters.fragments.SellerAdapter
import com.eorder.app.helpers.GridLayoutItemDecoration
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISelectSeller
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.viewmodels.fragments.CatalogsViewModel
import com.eorder.app.viewmodels.fragments.SellersViewModel
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.sellers_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class SellersFragment : BaseFragment(),
    IShowSnackBarMessage, IRepaintModel,
    ISetAdapterListener {

    private lateinit var model: SellersViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: SellerAdapter
    private lateinit var sellers: List<Seller>
    private lateinit var refreshLayout: SwipeRefreshLayout


    private lateinit var viewModel: CatalogsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.sellers_fragment, container, false)

    }

    override fun showMessage(message: String) {

        SnackBar(
            context!!,
            swipeRefresh_sellers_fragment,
            resources.getString(R.string.close),
            message
        ).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
        setObservers()
        model.getSellers()

    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        val seller = (obj as Seller)

        view.findViewById<CardView>(R.id.cardView_sellers_list_item)
            .setOnClickListener { view ->

                (context as ISelectSeller).selectSeller(seller)
            }

    }

    override fun repaintModel(view: View, model: Any?) {

        val seller = (model as Seller)
        view.findViewById<TextView>(R.id.textView_sellers_list_seller_name).text =
            seller.companyName

        try {
            Glide.with(context!!).load(seller.imageUrl)
                .into(view.findViewById<ImageView>(R.id.imgView_seller_list_img))
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_seller_list_img))
        }


    }


    fun setObservers() {

        model.getSellersResultObservable().observe(
            this.activity as LifecycleOwner,
            Observer<ServerResponse<List<Seller>>> {

                sellers = it.ServerData?.Data ?: mutableListOf()
                adapter.sellers = sellers
                adapter.notifyDataSetChanged()


            })

        model.getErrorObservable()
            ?.observe(this.activity as LifecycleOwner, Observer<Throwable> { ex ->

                refreshLayout.isRefreshing = false
                model.getManagerExceptionService().manageException(this.context!!, ex)
            })

    }


    private fun init() {

        val layout = GridLayoutManager(this.context, 2)

        adapter = SellerAdapter(this, mutableListOf())
        recyclerView = this.view?.findViewById<RecyclerView>(R.id.recView_sellers_fragment)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()

        refreshLayout = this.view?.findViewById(R.id.swipeRefresh_sellers_fragment)!!
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
        refreshLayout.setOnRefreshListener {

            model.getSellers()
        }

        recyclerView?.addItemDecoration(
            GridLayoutItemDecoration(
                2,
                50,
                true
            )
        )
    }

}
