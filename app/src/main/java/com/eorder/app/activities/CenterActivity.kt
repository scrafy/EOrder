package com.eorder.app.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.com.eorder.app.activities.BaseActivity
import com.eorder.app.com.eorder.app.adapters.CentersAdapter
import com.eorder.app.com.eorder.app.viewmodels.CenterViewModel
import com.eorder.application.models.GetCatalogsByCentreResponse
import com.eorder.application.models.GetCentersResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel



class CenterActivity : BaseActivity() {

    var model: CenterViewModel? = null
    var recyclerView : RecyclerView ? = null
    var adapter = CentersAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_centers)
        init()
        model = getViewModel()
        setObservers()
        model?.getCenters()

    }

    fun setObservers(){

        model?.getCentersResultObservable()?.observe(this, Observer<GetCentersResponse> { it ->

            adapter.centers =  it.data.serverData?.data ?: mutableListOf()
            adapter.notifyDataSetChanged()

        })

        model?.getCatalogByCentreObservable()?.observe(this, Observer<GetCatalogsByCentreResponse> { it ->

           println(it.data.serverData?.data)

        })

        model?.getErrorObservable()?.observe(this, Observer<Throwable>{ex ->

            manageException(ex)

        })
    }

    fun showCatalogs(centerId: Int?){

        if (centerId != null)
            model?.getCatalogByCentre(centerId)
    }

    fun showCenterInfo(centerId: Int?){
        Toast.makeText(this, centerId.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun init() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.dwrLayout_drawerlayout)
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.lateral_nav_menu_open, R.string.lateral_nav_menu_close)
        var layout = LinearLayoutManager(this)

        toolbar.inflateMenu(R.menu.main_menu)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        recyclerView = findViewById<RecyclerView>(R.id.recView_centers)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()


    }
}
