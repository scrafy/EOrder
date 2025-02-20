package com.pedidoe.app.activities

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pedidoe.app.R
import com.pedidoe.app.fragments.CenterInfoFragment
import com.pedidoe.app.fragments.CentersFragment
import com.pedidoe.app.interfaces.ISelectCenter
import com.pedidoe.app.viewmodels.CenterActivityViewModel
import com.pedidoe.application.interfaces.ICheckValidSession
import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.models.Center
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class CenterActivity : BaseMenuActivity(), ISelectCenter, ICheckValidSession {

    private lateinit var model: CenterActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_center)
        model = getViewModel()
        setMenuToolbar()
        setObservers()
        init()

    }

    override fun setMenuToolbar() {
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

    override fun signOutApp() {
        model.signOutApp(this)
    }

    override fun checkValidSession() {
        model.checkValidSession(this)
    }

    override fun selectCenter(center: Center) {

        CenterInfoFragment.center = center

        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linearLayout_activity_center_container, CenterInfoFragment())
            .addToBackStack(null).commit()
    }



    private fun init() {

        model.getCenters()

    }

    fun setObservers() {

        model.getCentersResult.observe(
            this as LifecycleOwner,
            Observer<ServerResponse<List<Center>>> {

                val centers = it.ServerData?.Data ?: mutableListOf()
                var fragment = CentersFragment()
                var args = Bundle()

                args.putBoolean("showViewProductsLink", true)
                args.putString("centers", Gson.Create().toJson(centers))
                fragment.arguments = args

                supportFragmentManager.beginTransaction()
                    .add(R.id.linearLayout_activity_center_container, fragment).commit()

            })

        model.getErrorObservable()
            ?.observe(this as LifecycleOwner, Observer<Throwable> { ex ->

                model.getManagerExceptionService().manageException(this, ex)
            })


    }

}
