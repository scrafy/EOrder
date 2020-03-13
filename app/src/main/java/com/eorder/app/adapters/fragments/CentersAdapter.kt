package com.eorder.app.adapters.fragments

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.com.eorder.app.interfaces.IShowCatalogsByCenter
import com.eorder.app.com.eorder.app.interfaces.IShowCenterInfo
import com.eorder.infrastructure.models.Center

class CentersAdapter(var centers: List<Center>) : RecyclerView.Adapter<CentersAdapter.CenterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.centers_list, parent, false)

        return CenterViewHolder(
            view,
            parent.context
        )
    }

    override fun getItemCount(): Int {
       return centers.size
    }

    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {

        holder.setData(centers.get(position))
    }


    class CenterViewHolder(private val view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

        private var centerName: TextView ? = null
        private var email: TextView ? = null
        private var address: TextView ? = null
        private var enabled: TextView ? = null
        private var imgCenter: ImageView? = null
        private var idCenter: Int? = null


        init{

            this.centerName = view.findViewById<TextView>(R.id.textView_center_name)
            this.email = view.findViewById<TextView>(R.id.textView_email)
            this.address = view.findViewById<TextView>(R.id.textView_address)
            this.enabled = view.findViewById<TextView>(R.id.textView_enabled)
            this.imgCenter = view.findViewById<ImageView>(R.id.imageView_thumbnail)
            setListener()
        }

        private fun setListener() {

            view.findViewById<ImageView>(R.id.imgView_center_card_view_catalog).setOnClickListener{view ->

                (context as IShowCatalogsByCenter).showCatalogsByCenter(this.idCenter)
            }

            view.findViewById<ImageView>(R.id.imgView_center_card_view_info).setOnClickListener{view ->

                (context as IShowCenterInfo).showCenterInfo(this.idCenter)
            }
        }


        fun setData(center: Center){

            this.idCenter = center.id
            this.centerName?.setText(center.center_name)
            this.email?.setText(center.email)
            this.address?.setText(center.address)
            if (center.enabled) {
                this.enabled?.setText(R.string.enabled)
                this.enabled?.setTextColor(Color.GREEN)
            }else{
                this.enabled?.setText(R.string.disabled)
                this.enabled?.setTextColor(Color.RED)
            }
       }
    }
}