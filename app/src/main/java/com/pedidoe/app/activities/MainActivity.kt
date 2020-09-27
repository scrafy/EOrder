package com.pedidoe.app.activities


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pedidoe.app.R
import com.pedidoe.app.viewmodels.MainViewModel
import com.pedidoe.app.widgets.AlertDialogOk
import com.pedidoe.app.widgets.SnackBar
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.models.ApkVersion
import com.pedidoe.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.system.exitProcess


@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), IShowSnackBarMessage {


    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = getViewModel()
        setObservers()
        setListeners()
        showSessionExpiredMessage()
        init()
    }


    override fun showMessage(message: String) {

        SnackBar(
            this,
            findViewById<LinearLayout>(R.id.linearLayout_activity_main_conatiner),
            resources.getString(R.string.close),
            message
        ).show()
    }

    fun setObservers() {

        model.apkVersionResult.observe(this, Observer<ServerResponse<ApkVersion>> {

            val apkVersionDeployed = it.ServerData?.Data
            val version = this.packageManager.getPackageInfo(packageName, 0).versionName

            if (apkVersionDeployed?.version == null) {
                AlertDialogOk(
                    this,
                    resources.getString(R.string.main_activity_apk_new_version_title),
                    resources.getString(R.string.main_activity_apk_new_version__problem_message),
                    resources.getString(R.string.ok)
                ) { d, i -> finish(); exitProcess(0) }.show()

            } else if (apkVersionDeployed.version != version) {

                if (apkVersionDeployed.isMandatory) {

                    AlertDialogOk(
                        this,
                        resources.getString(R.string.main_activity_apk_new_version_title),
                        String.format(resources.getString(R.string.main_activity_apk_new_version_message), apkVersionDeployed.version),
                        resources.getString(R.string.ok)
                    ) { d, i ->

                        val marketUri = "https://play.google.com/store/apps/details?id=com.pedidoe"
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(marketUri)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                        startActivity(intent)
                        finish()
                        exitProcess(0)
                    }.show()

                } else {

                    AlertDialogOk(
                        this,
                        resources.getString(R.string.main_activity_apk_new_version_title),
                        String.format(resources.getString(R.string.main_activity_apk_new_version_message), apkVersionDeployed.version),
                        resources.getString(R.string.ok)
                    ) { d, i ->

                        val marketUri = "https://play.google.com/store/apps/details?id=com.pedidoe"
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(marketUri)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                        startActivity(intent)

                    }.show()

                }

            } else {

                val pInfo = this.packageManager.getPackageInfo(packageName, 0)
                val version = pInfo.versionName
                linearLayout_login_activity_textView_version.text = "V $version"
                model.loadSessionToken(this)
                if ( model.isLogged() ) {
                    model.LoadOrderFromSharedPreferences(this)
                    startActivity(Intent(this, LandingActivity::class.java))
                }
            }


        })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })
    }

    fun setListeners() {

        button_activity_main_login.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))
        }

        button_activity_main_create_account.setOnClickListener {

            startActivity(Intent(this, ActivateCenterActivity::class.java))
        }

    }

    fun showSessionExpiredMessage() {

        if (intent.getStringExtra("session_expired") != null)
            AlertDialogOk(
                this,
                resources.getString(R.string.main_activity_session_expired),
                intent.getStringExtra("session_expired"),
                "OK"
            ) { d, i -> }.show()

    }


    private fun init() {

        val pInfo = this.packageManager.getPackageInfo(packageName, 0)
        val version = pInfo.versionName
        linearLayout_login_activity_textView_version.text = "V $version"
        model.getCurrentApkVersion()

    }


}
