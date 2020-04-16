package com.eorder.app.fragments


import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.eorder.app.adapters.fragments.CentersAdapter
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISelectCenter
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.app.viewmodels.fragments.CentersViewModel
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel
import com.eorder.app.R
import com.eorder.app.helpers.GridLayoutItemDecoration
import com.eorder.app.helpers.LoadImageHelper


@RequiresApi(Build.VERSION_CODES.O)
class CentersFragment : BaseFragment(),
    IShowSnackBarMessage, IRepaintModel,
    ISetAdapterListener {

    private lateinit var model: CentersViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: CentersAdapter
    private lateinit var centers: List<Center>
    private lateinit var refreshLayout:SwipeRefreshLayout


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

        if ( center.image != null)
            LoadImageHelper().setImageAsCircle(view.findViewById<ImageView>(R.id.imgView_center_list_img_center), center.image as Bitmap)

        else
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_center_list_img_center))
    }

    fun setObservers() {

        model.getCentersResultObservable().observe(
            this.activity as LifecycleOwner,
            Observer<ServerResponse<List<Center>>> { it ->

                centers = it.serverData?.data ?: mutableListOf()
                adapter.centers = centers
                adapter.notifyDataSetChanged()
                refreshLayout.isRefreshing = false
                loadImages()
            })

        model.getErrorObservable()
            ?.observe(this.activity as LifecycleOwner, Observer<Throwable> { ex ->

                refreshLayout.isRefreshing = false
                model.getManagerExceptionService().manageException(this.context!!, ex)
            })


    }

    private fun loadImages() {

        centers.forEach { p->

            LoadImageHelper().loadImage(p).observe(this.activity as LifecycleOwner, Observer<Any> {

                adapter.notifyDataSetChanged()
            })
        }
    }

    private fun init() {

        val layout = GridLayoutManager(context, 2)

        adapter = CentersAdapter(this, mutableListOf())
        recyclerView = this.view?.findViewById(R.id.recView_centers_fragment)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()

        refreshLayout = this.view?.findViewById(R.id.swipeRefresh_centers_fragment)!!
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
        refreshLayout.setOnRefreshListener {

            model.getCenters()
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
