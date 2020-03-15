package com.eorder.app.activities


import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.eorder.app.R


class LandingActivity : BaseActivity() {

     override fun addActionBar(menu: MutableMap<String, Int>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        setListeners()

    }


    private fun setListeners() {

        findViewById<TextView>(R.id.textView_center).setOnClickListener{view ->

            startActivity(Intent(this, CenterActivity::class.java))

        }
    }

}
