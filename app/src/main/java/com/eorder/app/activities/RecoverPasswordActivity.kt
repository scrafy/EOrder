package com.eorder.app.activities


import android.os.Bundle
import com.eorder.app.R
import com.eorder.domain.models.ValidationError

class RecoverPasswordActivity : BaseViewModelActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)
    }

    override fun setObservers() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setListeners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setValidationErrors(errors: List<ValidationError>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearEditTextAndFocus() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
