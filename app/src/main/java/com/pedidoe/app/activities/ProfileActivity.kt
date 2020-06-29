package com.pedidoe.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import com.pedidoe.app.R
import com.pedidoe.app.com.eorder.app.viewmodels.ProfileActivityViewModel
import com.pedidoe.app.widgets.SnackBar
import com.pedidoe.application.interfaces.ICheckValidSession
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.models.Product
import kotlinx.android.synthetic.main.activity_product_calendar.toolbar
import kotlinx.android.synthetic.main.activity_profile.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class ProfileActivity : BaseFloatingButtonActivity(), IShowSnackBarMessage, ICheckValidSession {

    private lateinit var model: ProfileActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        model = getViewModel()
        setListeners()
        init()

    }

    override fun showMessage(message: String) {

        SnackBar(
            this,
            findViewById<LinearLayout>(R.id.linearLayout_change_password_activity_root),
            resources.getString(R.string.close),
            message
        ).show()
    }

    override fun onStart() {
        super.onStart()
        this.setSupportActionBar(toolbar as Toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        this.onBackPressed()
        return true
    }

    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

    override fun checkValidSession() {
        model.checkValidSession(this)
    }

    private fun setListeners() {

        button_activity_profile_change_password.setOnClickListener {

            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
    }


    private fun init() {

        val profile = model.getProfile()

        editText_create_profile_activity_username.setText(profile.username)
        editText_create_profile_activity_phone.setText(profile.phone)
        editText_create_profile_activity_email.setText(profile.email)
    }
}
