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
import com.eorder.app.activities.ProductActivity
import com.eorder.app.adapters.fragments.CentersAdapter
import com.eorder.app.helpers.GridLayoutItemDecoration
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISelectCenter
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.viewmodels.fragments.CentersViewModel
import com.eorder.app.widgets.SnackBar
import com.eorder.domain.factories.Gson
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Center
import com.eorder.domain.models.ServerResponse
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.centers_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class CentersFragment : BaseFragment(),
    IShowSnackBarMessage, IRepaintModel,
    ISetAdapterListener {

    private lateinit var model: CentersViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: CentersAdapter
    private lateinit var centers: List<Center>
    private lateinit var refreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.centers_fragment, container, false)
    }

    override fun showMessage(message: String) {
        editText_activity_main_code_input.text.clear()
        SnackBar(
            context!!,
            swipeRefresh_centers_fragment,
            resources.getString(R.string.close),
            message
        ).show()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
        setObservers()


    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        val center = (obj as Center)

        view.findViewById<CardView>(R.id.cardView_center_list_item)
            .setOnClickListener { view ->

                (context as ISelectCenter).selectCenter(center)
            }

        if (view.findViewById<TextView>(R.id.textView_centers_list_view_products) != null)

            view.findViewById<TextView>(R.id.textView_centers_list_view_products).setOnClickListener {

                val intent = Intent(context, ProductActivity::class.java)
                intent.putExtra("centerId", center.id)
                startActivity(intent)
            }
    }

    override fun repaintModel(view: View, model: Any?) {

        val center = (model as Center)

        view.findViewById<TextView>(R.id.textView_center_name).text = center.center_name

        try {
            Glide.with(context!!).load(center.imageUrl).circleCrop()
                .into(view.findViewById<ImageView>(R.id.imgView_center_list_img_center))
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_center_list_img_center))
        }

        if (this.arguments != null && (this.arguments as Bundle).getBoolean("showViewProductsLink")) {

            view.findViewById<TextView>(
                R.id.textView_centers_list_view_products
            ).visibility = View.VISIBLE
        }else{
            view.findViewById<TextView>(
                R.id.textView_centers_list_view_products
            ).visibility = View.INVISIBLE
        }



    }

    fun setObservers() {

        model.getCentersResultObservable().observe(
            this.activity as LifecycleOwner,
            Observer<ServerResponse<List<Center>>> {

                centers = it.serverData?.data ?: mutableListOf()

                adapter.centers = centers
                adapter.notifyDataSetChanged()
                refreshLayout.isRefreshing = false

            })

        model.getErrorObservable()
            ?.observe(this.activity as LifecycleOwner, Observer<Throwable> { ex ->

                refreshLayout.isRefreshing = false
                model.getManagerExceptionService().manageException(this.context!!, ex)
            })


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
        centers = Gson.Create().fromJson(arguments?.getString("centers"), object: TypeToken<List<Center>>(){}.type)
        adapter.centers = centers
        adapter.notifyDataSetChanged()
    }

}
