package com.eorder.app.activities

import android.os.Bundle
import android.view.View
import com.eorder.app.R
import com.eorder.app.com.eorder.app.viewmodels.ProductViewModel
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ProductActivity : BaseMenuActivity(), IShowSnackBarMessage,
    IRepaintModel, ISetAdapterListener {

    private lateinit var model: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        model = getViewModel()
        setMenuToolbar()
    }

    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

    override fun checkValidSession() {
        model.checkValidSession(this)
    }

    override fun setMenuToolbar() {
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

    override fun signOutApp() {
        model.signOutApp(this)
    }

    override fun showMessage(message: String) {
        //SnackBar(this, null,resources.getString(R.string.close), message).show()
    }

    override fun setAdapterListeners(view: View, obj: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun repaintModel(view: View, model: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
