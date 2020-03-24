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
import com.eorder.app.R
import com.eorder.app.adapters.fragments.SellerAdapter
import com.eorder.app.com.eorder.app.activities.BaseFloatingButtonActivity
import com.eorder.app.com.eorder.app.fragments.BaseFloatingButtonFragment
import com.eorder.app.com.eorder.app.fragments.BaseFragment
import com.eorder.app.com.eorder.app.interfaces.ISelectSeller
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.domain.interfaces.IShowSnackBarMessage
import com.eorder.app.viewmodels.fragments.CatalogsViewModel
import com.eorder.app.viewmodels.fragments.SellersViewModel
import com.eorder.application.extensions.toBitmap
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel
import pl.droidsonroids.gif.GifDrawable
import java.lang.Exception

class SellersFragment : BaseFloatingButtonFragment(), IShowSnackBarMessage, IRepaintModel, ISetAdapterListener {

    private lateinit var model: SellersViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: SellerAdapter
    private lateinit var sellers: List<Seller>


    private lateinit var viewModel: CatalogsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.sellers_fragment, container, false)

    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
        setObservers()
        var centerId = arguments?.getInt("centerId")
        if (centerId != null)

            model.getSellersByCenter(centerId)
        else {
            //TODO show snackbar showing message error
        }

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
        view.findViewById<TextView>(R.id.textView_sellers_list_name).text = seller.companyName

        if (seller.imageBase64 == null) {

            try {
                view.findViewById<ImageView>(R.id.imgView_seller_list_img_product)
                    .setImageDrawable(GifDrawable(context?.resources!!, R.drawable.loading))
            } catch (ex: Exception) {

            }

        } else {
            view.findViewById<ImageView>(R.id.imgView_seller_list_img_product)
                .setImageBitmap(seller.imageBase64?.toBitmap())
        }
    }

    fun setObservers() {

        model.getSellersByCenterResultObservable().observe(
            (context as LifecycleOwner),
            Observer<ServerResponse<List<Seller>>> { it ->

                sellers = it.serverData?.data ?: mutableListOf()
                adapter.sellers = sellers
                adapter.notifyDataSetChanged()
                var items = sellers.filter { p -> p.imageUrl != null && p.id != null }.map { p ->

                    UrlLoadedImage(p.id!!, p.imageBase64, p.imageUrl!!)
                }

                model.loadImages(items)
                    .observe((context as LifecycleOwner), Observer<List<UrlLoadedImage>> { items ->

                        items.forEach { item ->

                            this.sellers.find { c -> c.id == item.id }?.imageBase64 =
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

        adapter = SellerAdapter(this, mutableListOf())
        recyclerView = this.view?.findViewById<RecyclerView>(R.id.recView_sellers_fragment)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()
    }

}
